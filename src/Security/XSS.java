package Security;

public class XSS {
	//  XSS 예방
	public static String prevention(String weak) {
		return weak.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("\'", "&quot;");
	}
}