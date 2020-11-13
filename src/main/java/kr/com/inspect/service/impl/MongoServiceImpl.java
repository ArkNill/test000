package kr.com.inspect.service.impl;

import java.util.List;

import org.bson.Document;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.MongoDao;
import kr.com.inspect.service.MongoService;

@Service
public class MongoServiceImpl implements MongoService {
	@Autowired
	private ElasticDao elasticDao;
	
	@Autowired
	private MongoDao mongoDao;
	
	/* 몽고DB 컬렉션에 엘라스틱서치에서 받아온 인덱스 데이터를 입력하기 */
	@Override
	public void insertElasticIndex(String database, String col, String index) {
		// 인덱스를 통해 엘라스틱서치에서 데이터를 받아옴
		SearchHit[] searchHits = elasticDao.getIndex(index);
		MongoCollection<Document> collection = mongoDao.makeMongoCollection(database, col);
		
		for(SearchHit hit: searchHits) {
			String json = hit.getSourceAsString();
			Document document = Document.parse(json);
			//document.put("_id", hit.getId());
			collection.insertOne(document);
		}
	}
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기 */
	@Override
	public boolean insertJSONData(String database, String col, String fullPath) {
		return mongoDao.insertJSONData(database, col, fullPath);
	}
	
	/* 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기 */
	public List<Document> getCollection(String database, String col){
		return mongoDao.getCollection(database, col);
	}
}
