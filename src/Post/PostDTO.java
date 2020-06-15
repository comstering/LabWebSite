package Post;

public class PostDTO {
	private String category;    //  카테고리
	private int id;    //  게시글 번호
	private String title;    //  제목
	private String writer;    //  작성자
	private String date;    //  작성날짜
	private String reWriter;    //  수정자
	private String reDate;    //  수정날짜
	private String content;    //  내용
	private int count;    //  조회수
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReWriter() {
		return reWriter;
	}
	public void setReWriter(String reWriter) {
		this.reWriter = reWriter;
	}
	public String getReDate() {
		return reDate;
	}
	public void setReDate(String reDate) {
		this.reDate = reDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public PostDTO() {
		
	}
	public PostDTO(String category, int id, String title, String writer, String date, String reWriter, String reDate, String content, int count) {
		super();
		this.category = category;
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.date = date;
		this.reWriter = reWriter;
		this.reDate = reDate;
		this.content = content;
		this.count = count;
	}
}