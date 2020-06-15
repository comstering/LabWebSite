package Board;

public class CalendarDTO {
	private int start_month;    //  시작 달
	private int start_day;    //  시작 일
	private int end_month;    //  끝나는 달
	private int end_day;    //  끝나는 일
	private String content;    //  일정 내용
	
	public int getStart_month() {
		return start_month;
	}
	public void setStart_month(int start_month) {
		this.start_month = start_month;
	}
	public int getStart_day() {
		return start_day;
	}
	public void setStart_day(int start_day) {
		this.start_day = start_day;
	}
	public int getEnd_month() {
		return end_month;
	}
	public void setEnd_month(int end_month) {
		this.end_month = end_month;
	}
	public int getEnd_day() {
		return end_day;
	}
	public void setEnd_day(int end_day) {
		this.end_day = end_day;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public CalendarDTO() {
		
	}
	
	public CalendarDTO(int start_month, int start_day, int end_month, int end_day, String content) {
		super();
		this.start_month = start_month;
		this.start_day = start_day;
		this.end_month = end_month;
		this.end_day = end_day;
		this.content = content;
	}
	
}
