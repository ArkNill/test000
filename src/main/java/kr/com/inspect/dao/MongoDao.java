package kr.com.inspect.dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public interface MongoDao {
	/* 자원 회수 */
	public void close();
	
	/* 해당되는 database의 collection 객체인 MongoCollection<Document> 만들기 */
	public MongoCollection<Document> makeMongoCollection(String database, String col);
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기 */
	public boolean insertJSONData(String database, String col, String path);
	
	/* 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기 */
	public List<Document> getCollection(String database, String col);
}
