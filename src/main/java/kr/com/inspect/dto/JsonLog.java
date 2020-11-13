package kr.com.inspect.dto;

/* JsonLog 테이블(json 파일 저장 시간 정보 테이블) */
public class JsonLog {
    private int id; //primary key
    private String title; // 파일명
    private String start; // 파일 저장 시작시간
    private String finish; // 파일 저장 종료시간
    private String elapsed; // 총 걸린 시간
    private Program program;

    public JsonLog() {}
    public JsonLog(int id, String title, String start, String finish, String elapsed, Program program) {
		super();
		this.id = id;
		this.title = title;
		this.start = start;
		this.finish = finish;
		this.elapsed = elapsed;
		this.program = program;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
	
	@Override
	public String toString() {
		return "JsonLog [id=" + id + ", title=" + title + ", start=" + start + ", finish=" + finish + ", elapsed="
				+ elapsed + ", program=" + program + "]";
	}
}
