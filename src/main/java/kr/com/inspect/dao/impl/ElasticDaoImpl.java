package kr.com.inspect.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.parser.JsonParsing;

@Repository
public class ElasticDaoImpl implements ElasticDao {
	@Autowired
	private RestHighLevelClient elasticClient;
	
	private JsonParsing jsonParsing = new JsonParsing();
	
	/* 자원 회수 */
	@Override
	public void close() {
		try {
			elasticClient.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	/* 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기 */
	@Override
	public SearchHit[] getIndex(String index) {
		// 엘라스틱서치 index 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest().indices(index);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        
        try {
            searchResponse = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            System.out.println("search error");
        }
      SearchHit[] searchHits = searchResponse.getHits().getHits();
      return searchHits;
	}

	/* 특정 경로에 있는 JSON 파일들을 읽어서 엘라스틱서치에 넣기 */
	@Override
	public boolean insertJSON(String index, String path) {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;
		
		for(File file : fileList){
			/* 확장자가 json인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("json")){
		    	/* JSON 파일을 파싱해서 문자열로 만듦 */
		    	String fullPath = path + file.getName();
				JSONObject jo = jsonParsing.getJSONObject(fullPath);
				String json = jo.toString();
				
				/* title_creator로 _id를 지정 */
				Map map = (Map) jo.get("metadata");
				String title = map.get("title").toString();
				String creator = map.get("creator").toString();
				
				IndexRequest request = new IndexRequest(index);
				request.id(title+"_"+creator);
				request.source(json, XContentType.JSON); 
			}
		}
		return false;
	}
}
