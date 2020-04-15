package Security;

public class XSS {
	public static String prevention(String weak) {
		weak = weak.replaceAll("<", "&li;");
		weak = weak.replaceAll(">", "&gt;");
		weak = weak.replaceAll("&", "&amp;");
		weak = weak.replaceAll("\'\"", "&quot;");
		return weak;
	}
}