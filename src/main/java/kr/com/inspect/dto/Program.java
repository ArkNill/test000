package kr.com.inspect.dto;

/* Program 테이블(전체 강의 프로그램 목록)  */
public class Program {
	private int id; //순번
	private String file_num; //파일이름
	private String title; //제목
	private String subtitle; //부제
	private String running_time; //방송시간
	
	public Program() {}
	public Program(int id, String file_num, String title, String subtitle, String running_time) {
		super();
		this.id = id;
		this.file_num = file_num;
		this.title = title;
		this.subtitle = subtitle;
		this.running_time = running_time;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_num() {
		return file_num;
	}
	public void setFile_num(String file_num) {
		this.file_num = file_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getRunning_time() {
		return running_time;
	}
	public void setRunning_time(String running_time) {
		this.running_time = running_time;
	}
	
	@Override
	public String toString() {
		return "Program [id=" + id + ", file_num=" + file_num + ", title=" + title + ", subtitle=" + subtitle
				+ ", running_time=" + running_time + "]";
	}
}
