package kr.com.inspect.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.com.inspect.dto.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.parser.JsonParsing;
import kr.com.inspect.parser.XlsxParsing;
import kr.com.inspect.service.PostgreService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public class PostgreServiceImpl implements PostgreService{
	@Autowired
	private ElasticDao elasticDao;
	
	@Autowired
	private PostgreDao postgreDao;
	
	@Autowired
	private SqlSession sqlSession;
	private final String metadataNS = "MetadataMapper.";
	private final String programNS = "ProgramMapper.";
	private final String speakerNS = "SpeakerMapper.";
	private final String utteranceNS = "UtteranceMapper.";
	private final String eojeolListNS = "EojeolListMapper.";
	private final String jsonLogNS = "JsonLogMapper.";
	
	private JsonParsing jsonParsing = new JsonParsing();
	private XlsxParsing xlsxParsing = new XlsxParsing();
	
	/* 엘라스틱 서치에서 받아온 인덱스를 PostgreSQL에 넣음(테스트) */
	@Override
	public void insertElasticIndex(String index) {
		// 인덱스를 통해 엘라스틱서치에서 데이터를 받아옴
		SearchHit[] searchHits = elasticDao.getIndex(index);

		//테스트용 VO인 Sound를 엘라스틱서치에서 PostgreSQL로 넣음(사용시 수정 필요)
//		for(SearchHit hit: searchHits) {
//			Map<String, Object> map = hit.getSourceAsMap();
//			Sound sound = new Sound();
//			sound.setId(hit.getId());
//			sound.setCategory((String)map.get("category"));
//			sound.setTitle((String)map.get("title"));
//			sound.setCompany((String)map.get("company"));
//			sound.setContent((String)map.get("content"));
//			
//			postgreInsertMapper.insertTestValue(sound); //PostgreSQL INSERT 쿼리문 필요
//		}
	}
	
	/* Metadata 테이블을 모두 가지고 옴 */
	public List<Metadata> getMetadata(){
		return postgreDao.getMetadata();
	}

	/* JsonLog 테이블을 모두 가져옴 */
	public List<JsonLog> getJsonLog(){ return postgreDao.getJsonLog();	}
	
	/* id로 해당되는 Metadata 테이블을 가져옴 */
	public Metadata getMetadataById(Integer id) {
		return postgreDao.getMetadataById(id);
	}
	
	/* metadataId로 해당되는 Utterance 테이블을 가져옴 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId){
		return postgreDao.getUtteranceUsingMetadataId(metadataId);
	}

	/* utterance_id 를 이용하여 eojeollist 데이터 가져오기 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id){
		return postgreDao.getEojeolListUsingUtteranceId(id);
	}
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 PostgreSQL에 넣음 */
	@Override
	public boolean insertJSONObject(String path, List<MultipartFile> jsonFile) throws Exception {
		String filename;
		File f;

		/* 서버 디렉토리에 파일 저장 */
		for(int i=0; i<jsonFile.size(); i++){
			filename = jsonFile.get(i).getOriginalFilename();

			f = new File(path+filename);

			jsonFile.get(i).transferTo(f);
		}

		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;

		for(File file : fileList){
			/* 확장자가 json인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("json")){
		    	String fullPath = path + file.getName();

				JsonLog jsonLog = new JsonLog();

				/* jsonLog 테이블 시작시간 측정 */
		    	jsonLog.setStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				long startTime = System.currentTimeMillis();

		    	/* json 파일을 읽어서 객체로 파싱 */
				JSONObject obj = jsonParsing.getJSONObject(fullPath);

				/* metadata 테이블 입력 */
				Metadata metadata  = jsonParsing.setMetadata(obj);

				/* jsonLog 테이블 파일명 입력 */
				jsonLog.setTitle(metadata.getTitle());

				/* metadata_id를 가져옴(creator, title) */
				Map map = new HashMap();
				map.put("creator", metadata.getCreator());
				map.put("title", metadata.getTitle());
				String isExistId = sqlSession.selectOne(metadataNS+"isExistMetadataId", map);

				if(isExistId == null) { //등록된 데이터가 아닐 경우
					check = true;

					/* metadata 테이블 입력 */
					sqlSession.insert(metadataNS+"insertIntoMetadata", metadata);

					/* auto increment로 등록된 id를 가져옴 */
					int metadata_id = sqlSession.selectOne(metadataNS+"getMetadataId", map);

					/* speaker 테이블 입력 */
					List<Speaker> speakerList = jsonParsing.setSpeaker(obj, metadata_id);
					for(Speaker speaker : speakerList) {
						sqlSession.insert(speakerNS+"insertIntoSpeaker", speaker);
					}

					/* utterance 테이블 입력 */
					List<Utterance> utteranceList = jsonParsing.setUtterance(obj, metadata_id);
					for(Utterance utterance : utteranceList) {
						sqlSession.insert(utteranceNS+"insertIntoUtterance", utterance); //utterance 입력
						List<EojeolList> eojeolListList = utterance.getEojoelList();
						for(EojeolList eojeolList : eojeolListList) {
							sqlSession.insert(eojeolListNS+"insertIntoEojeolList", eojeolList); //eojeolList 입력
						}
					}

					/* jsonLog 테이블 종료시간 측정 */
					jsonLog.setFinish(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					long endTime = System.currentTimeMillis();

					/* jsonLog 테이블 소요시간 입력 */
					int elapsed = (int)((endTime-startTime)/1000.0);
					int min = elapsed/60;
					int sec = elapsed - min*60;
					jsonLog.setElapsed(""+min+":"+sec);

					sqlSession.insert(jsonLogNS+"insertIntoJsonLog", jsonLog);
				}
		    }
		}

		/* 서버 디렉토리에 파일 삭제 */
		try{
			while (dir.listFiles().length != 0){
				File[] folder_list = dir.listFiles();

				for (int j=0; j<folder_list.length; j++){
					folder_list[j].delete();
				}
			}
		}catch (Exception e){
		}

		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return true;
		}else { //모두 중복된 데이터일 경우
			return false;
		}
	}

	/* 특정 경로에 있는 xlsx 파일들을 읽어서 PostgreSQL에 넣음 */
	@Override
	public boolean insertXlsxTable(String path, List<MultipartFile> xlsxFile) throws Exception{
		String filename;
		File f;

		/* 서버 디렉토리에 파일 저장 */
		for(int i=0; i<xlsxFile.size(); i++){
			filename = xlsxFile.get(i).getOriginalFilename();

			f = new File(path+filename);

			xlsxFile.get(i).transferTo(f);
		}

		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;

		for(File file : fileList){
			/* 확장자가 xlsx인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("xlsx")){
		    	String fullPath = path + file.getName();
		    	List<Program> list = xlsxParsing.setProgramList(fullPath);
		    	for(int i=0; i<list.size(); i++) {
		    		if(sqlSession.selectOne(programNS+"getProgramByFileNum", list.get(i).getFile_num()) == null) {
		    			check = true;
		    			sqlSession.insert(programNS+"insertIntoProgram", list.get(i));
		    		}
		    	}
		    }
		}

		/* 서버 디렉토리에 파일 삭제 */
		try{
			while (dir.listFiles().length != 0){
				File[] folder_list = dir.listFiles();

				for (int j=0; j<folder_list.length; j++){
					folder_list[j].delete();
				}
			}
		}catch (Exception e){
		}


		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return true;
		}else { //모두 중복된 데이터일 경우
			return false;
		}
	}
	
	/* Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public List<Metadata> getMetadataAndProgram(){
		return postgreDao.getMetadataAndProgram();
	}

	/* metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId){
		return postgreDao.getMetadataAndProgramUsingId(metaId);
	}
}
