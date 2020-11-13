package kr.com.inspect.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.com.inspect.service.MongoService;

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class MongoController {
	@Autowired
	private MongoService mongoService;
	
	//엘라스틱서치
	private String index = "audiolist";
	
	//몽고DB
	private String database = "audioDB";
	private String col = index;
	
	@Value("${input.json.directory}")
	private String jsonPath;
	
	/* 몽고DB 컬렉션에 엘라스틱서치에서 받아온 인덱스 데이터를 입력하기 */
	@GetMapping("/insertElasticIndexIntoMongo")
	public String insertElasticIndexIntoMongo() {
		mongoService.insertElasticIndex(database, col, index);
		return "mongoDB/insertElasticIndex";
	}
	
	/* 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기 */
	@GetMapping("/getMongoCollection")
	public String getMongoCollection(Model model) {
		List<Document> list = mongoService.getCollection(database, col);
		model.addAttribute("result", list);
		return "mongoDB/getCollection";
	}
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기 */
	@GetMapping("/insertJSONIntoMongo")
	public String insertJSONData(HttpServletRequest request) {
		boolean flag = mongoService.insertJSONData(database, col, jsonPath);
		if(flag == true) {
			return "mongoDB/insertJSON";
		}else {
			return "mongoDB/insertJSONFail";
		}
		
	}
}
