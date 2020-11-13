package kr.com.inspect.controller;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import kr.com.inspect.parser.JsonParsing;
import org.apache.lucene.search.suggest.analyzing.FSTUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.service.PostgreService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class PostgreController {
	@Autowired
	private PostgreService postgreService;
	
	//엘라스틱서치
	private String index = "audiolist";
	
	@Value("${input.json.directory}")
	private String jsonPath;
	
	@Value("${input.xlsx.directory}")
	private String xlsxPath;
	
	/* 엘라스틱서치 특정 인덱스를 PostgreSQL 특정 테이블에 넣기 */
	@GetMapping("/insertElasticIndexIntoPostgre")
	public String insertElasticIndexIntoPostgre() {
		postgreService.insertElasticIndex(index);
		return "postgreSQL/insertElasticIndex";
	}
	
	/* 데이터 입력 페이지로 이동 */
	@GetMapping("/insertIntoPostgre")
	public String insertPostgres() {
		return "postgreSQL/insertPostgres";
	}

	/* json 파일 postgresql 에 업로드 */
	@RequestMapping(value = "/jsonUpload", method = RequestMethod.POST)
	@ResponseBody
	public String jsonUpload (@RequestParam("jsonFile") List<MultipartFile> multipartFile) throws Exception{
		boolean flag = postgreService.insertJSONObject(jsonPath, multipartFile);

		if(flag == true)
			return "true";
		else
			return "false";
	}

	/* xlsx 파일 postgresql 에 업로드 */
	@RequestMapping(value = "/xlsxUpload", method = RequestMethod.POST)
	@ResponseBody
	public String xlsxUpload (@RequestParam("xlsxFile") List<MultipartFile> multipartFile) throws Exception{
		boolean flag = postgreService.insertXlsxTable(xlsxPath, multipartFile);

		if(flag == true)
			return "true";
		else
			return "false";
	}

	/* Utterance 테이블 가져오기 */
	@GetMapping("/getUtteranceTable/{format}")
	public String getUtteranceTable(Model model, @PathVariable Integer format){
		List<Utterance> utterances = postgreService.getUtteranceUsingMetadataId(format);
 		Metadata metadata = postgreService.getMetadataAndProgramUsingId(format);
		model.addAttribute("utterances",utterances);
		model.addAttribute("metadata",metadata);
		return "postgreSQL/getUtterance";
	}

	/* EojeolList 테이블 가져오기 */
	@GetMapping("/getEojeolList/{format}")
	public String getEojeolList(Model model, @PathVariable String format){
		List<EojeolList> eojeolLists = postgreService.getEojeolListUsingUtteranceId(format);
		model.addAttribute("eojeollist",eojeolLists);
		return "postgreSQL/getEojeolList";
	}
	
	/* Metadata & Program 조인해서 가져오기 */
	@GetMapping("/getMetadataAndProgram")
	public String getMetadataAndProgram(Model model) {
		List<Metadata> metadata = postgreService.getMetadataAndProgram();
		model.addAttribute("result", metadata);
		return "postgreSQL/getTable";
	}

	/* JsonLog 테이블 가져오기 */
	@GetMapping("/getJsonLog")
	public String getJsonLog(Model model){
		List<JsonLog> jsonLogs = postgreService.getJsonLog();
		model.addAttribute("jsonLog",jsonLogs);

		return "postgreSQL/getJsonLog";
	}
}
