package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.*;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONObject;

public interface PostgreDao {
	/* Metadata 테이블을 모두 가지고 옴 */
	public List<Metadata> getMetadata();
	
	/* id로 해당되는 Metadata 테이블을 가져옴 */
	public Metadata getMetadataById(Integer id);
	
	/* metadataId로 해당되는 Utterance 테이블을 가져옴 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId);
	
	/* Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public List<Metadata> getMetadataAndProgram();

	/* metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId);

	/* utterance_id 를 이용하여 eojeollist 데이터 가져오기 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id);

	/* JsonLog 테이블을 모두 가져옴 */
	public List<JsonLog> getJsonLog();
}
