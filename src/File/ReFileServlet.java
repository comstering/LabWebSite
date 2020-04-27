package File;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Post.PostDAO;

/**
 * Servlet implementation class ReFileServlet
 */
@WebServlet("/ReWrite")    //  글 수정
public class ReFileServlet extends HttpServlet {
	private FileDAO fileDAO = new FileDAO();
	private int MAX_SIZE = 1024 * 1024 * 100;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter script = response.getWriter();
		String directory = "/volume1" + fileDAO.getPath();
        File attachesDir = new File(directory);
        
        DiskFileItemFactory reFileItemFactory = new DiskFileItemFactory();
        reFileItemFactory.setRepository(attachesDir);
        reFileItemFactory.setSizeThreshold(MAX_SIZE);
        ServletFileUpload reFileUpload = new ServletFileUpload(reFileItemFactory);
        StringBuilder sb = new StringBuilder("");
		try {
            List<FileItem> items = reFileUpload.parseRequest(request);
			
            ArrayList<String> fileNames = new ArrayList<String>();
            ArrayList<String> fileSysNames = new ArrayList<String>();
            String category = null;
            int count = 1;
            for (FileItem item : items) {
                if (item.isFormField()) {    //  파일이 아닌 input value
        			script.println("<script>");
        			script.println("console.log('" + item.toString() + "')");
        			script.println("</script>");
                	if(count == 1) {
                		category = item.getString("utf-8");
                	}
                	sb.append(item.getString("utf-8"));
                    sb.append("-");
                } else {    //  파일  input
        			script.println("<script>");
        			script.println("console.log('" + item.toString() + "')");
        			script.println("console.log('" + item.getSize() + "')");
        			script.println("</script>");
                	String overlap = UUID.randomUUID().toString();
                    if (item.getSize() > 0) {
                        String separator = File.separator;
                        int index =  item.getName().lastIndexOf(separator);
                        String fileName = item.getName().substring(index  + 1);
                        String fileSysName = overlap + "_" + fileName;
                        File uploadFile = new File(directory + category + separator + fileSysName);
                        item.write(uploadFile);
                        fileNames.add(fileName);
                        fileSysNames.add(fileSysName);
                    }
                }
                count++;
            }
            //  게시글 번호, 제목, 내용, 수정자 구분
            String[] value = sb.toString().substring(0, sb.toString().lastIndexOf("-")).split("-");
            if(value.length > 5) {
            	for(int i = 5; i < value.length; i++) {
            		String[] delFile = value[i].split("_");
                	fileDAO.delete(value[0], Integer.parseInt(value[1]), delFile[1]);
            	}
            }
            
            PostDAO postDAO = new PostDAO();
            //  게시글 업데이트
            int result = postDAO.update(value[0], Integer.parseInt(value[1]), value[2], value[4], value[3]);
            
            if(result == 1) {
                for(int i = 0; i < fileNames.size(); i++) {
                	fileDAO.upload(value[0], Integer.parseInt(value[1]), fileNames.get(i), fileSysNames.get(i));
                }
            }

            script.println("<script>");
            if(category.equals("Notice") || category.equals("Library")) {
        		script.println("location.href = '../LabWebSite/Board/"+ category +".jsp'");
            } else {

        		script.println("location.href = '../LabWebSite/Activity/"+ category +".jsp'");
            }
    		script.println("</script>");
    		script.close();
    		return;
            
		} catch (FileUploadException e) {
			System.err.println("ReFileServlet FileUploadException error");
			script.println("<script>");
			script.println("alert('FileUpload error')");
			script.println("</script>");
		} catch (Exception e) {
			System.err.println("ReFileServlet Exception error write");
			script.println("<script>");
			script.println("alert('refile error')");
			script.println("</script>");
		}
	}

}
