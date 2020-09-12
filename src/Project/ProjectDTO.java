package Project;

public class ProjectDTO {
	private int ID;    //  프로젝트 번호
	private String title;    //  프로젝트 제목
	private String content;    //  프로젝트 내용
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public ProjectDTO(int ID, String title, String content) {
		super();
		this.ID = ID;
		this.title = title;
		this.content = content;
	}
}
