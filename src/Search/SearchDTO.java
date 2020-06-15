package Search;

public class SearchDTO {
	private String category;    //  카테고리
	private int id;    //  게시글 번호
	private String title;    //  제목
	private String content;    //  내용
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public SearchDTO(String category, int id, String title, String content) {
		super();
		this.category = category;
		this.id = id;
		this.title = title;
		this.content = content;
	}
}
