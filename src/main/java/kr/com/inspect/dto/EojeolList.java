package kr.com.inspect.dto;

/* EojeolList 테이블(한 문장을 띄어쓰기 단위로 쪼개낸 어절 모음) */
public class EojeolList {
	private String id; //primary key, 어절을 구분하기 위한 uuid 형식의 아이디
	private String standard; //표준어 어절
	private String eojeol; //어절(방언일 경우 standard와 다름)
	private boolean isDialect; //방언이면 true, 아니면 false
	private int begin; //문장에서 해당 어절이 시작되는 위치
	private int finish; //문장에서 해당 어절이 끝나는 위치
	private String utterance_id; //foreign key
	private int metadata_id; //foreign key
	
	public EojeolList() {}
	public EojeolList(String id, String standard, String eojeol, boolean isDialect, int begin, int finish,
			String utterance_id, int metadata_id) {
		super();
		this.id = id;
		this.standard = standard;
		this.eojeol = eojeol;
		this.isDialect = isDialect;
		this.begin = begin;
		this.finish = finish;
		this.utterance_id = utterance_id;
		this.metadata_id = metadata_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getEojeol() {
		return eojeol;
	}

	public void setEojeol(String eojeol) {
		this.eojeol = eojeol;
	}

	public boolean isDialect() {
		return isDialect;
	}

	public void setDialect(boolean isDialect) {
		this.isDialect = isDialect;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public String getUtterance_id() {
		return utterance_id;
	}

	public void setUtterance_id(String utterance_id) {
		this.utterance_id = utterance_id;
	}

	public int getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(int metadata_id) {
		this.metadata_id = metadata_id;
	}

	@Override
	public String toString() {
		return "EojeolList [id=" + id + ", standard=" + standard + ", eojeol=" + eojeol + ", isDialect=" + isDialect
				+ ", begin=" + begin + ", finish=" + finish + ", utterance_id=" + utterance_id + ", metadata_id="
				+ metadata_id + "]";
	}
}
