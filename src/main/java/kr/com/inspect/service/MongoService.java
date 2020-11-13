package kr.com.inspect.service;

import java.util.List;

import org.bson.Document;

public interface MongoService {
	/* 몽고DB 컬렉션에 엘라스틱서치에서 받아온 인덱스 데이터를 입력하기 */
	public void insertElasticIndex(String database, String col, String index);
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기 */
	public boolean insertJSONData(String database, String col, String fullPath);
	
	/* 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기 */
	public List<Document> getCollection(String database, String col);
}
