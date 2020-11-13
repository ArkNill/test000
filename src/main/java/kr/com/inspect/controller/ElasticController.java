package kr.com.inspect.controller;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.com.inspect.service.ElasticService;

@Controller
public class ElasticController {
	@Autowired
	private ElasticService elasticService;
	
	//엘라스틱서치
	private String index = "audiolist";

	/* 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기 */
	@GetMapping("/getElasticIndex")
	public String getElasticIndex(Model model) {
		SearchHit[] searchHits = elasticService.getIndex(index);
		model.addAttribute("result", searchHits); 
		return "elasticsearch/getIndex";
	}
}
