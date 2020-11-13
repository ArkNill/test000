package kr.com.inspect.service.impl;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.service.ElasticService;

@Service
public class ElasticServiceImpl implements ElasticService {
	@Autowired
	private ElasticDao elasticDao;
	
	/* 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기 */
	@Override
	public SearchHit[] getIndex(String index) {
		return elasticDao.getIndex(index);
	}

	/* 특정 경로에 있는 JSON 파일들을 읽어서 엘라스틱서치에 넣기 */
	@Override
	public boolean insertJSON(String index, String path) {
		return elasticDao.insertJSON(index, path);
	}
}
