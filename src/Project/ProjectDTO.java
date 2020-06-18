package Project;

public class ProjectDTO {
	private String title;    //  프로젝트 제목
	private String content;    //  프로젝트 내용
	
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
	
	public ProjectDTO(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
}
