package File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Security.XSS;

/**
 * Servlet implementation class downloadAction
 */
@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");    //  카테고리
		String fileRealName = request.getParameter("file");    //  시스템에 저장된 파일이름
		String fileName = request.getParameter("fileName");    //  다운로드 시 저장될 파일명
		
		//  파일 경로
		FileDAO fileDAO = new FileDAO();
		String directory = "/volume1" + fileDAO.getPath() + category + "/";
		
		File file = new File(directory + fileRealName);    //  파일 가져오기
		
		String mimeType = getServletContext().getMimeType(file.toString());
		if(mimeType == null) {
			response.setContentType("application/octest-stream");
		}
		
		String downloadName = null;
		if(request.getHeader("user-agent").indexOf("MSIE") == -1) {
			downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");
		} else {
			downloadName = new String(fileName.getBytes("EUC-KR"), "8859_1");
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName + "\";");
		
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream sos = response.getOutputStream();
		
		byte b[] = new byte[1024];
		int data = 0;
		
		while((data = (fis.read(b, 0, b.length))) != -1) {
			sos.write(b, 0, data);
		}
		
		//  자원 해제
		sos.flush();
		sos.close();
		fis.close();
	}
}