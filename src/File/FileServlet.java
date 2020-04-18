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
 * Servlet implementation class FileServlet
 */
@WebServlet("/fileUpload")
public class FileServlet extends HttpServlet {
	private FileDAO fileDAO = new FileDAO();
	private int MAX_SIZE = 1024 * 1024 * 100;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter script = response.getWriter();
        File attachesDir = new File(fileDAO.getPath());
        
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setRepository(attachesDir);
        fileItemFactory.setSizeThreshold(MAX_SIZE);
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        StringBuilder sb = new StringBuilder("");
		try {
            List<FileItem> items = fileUpload.parseRequest(request);
			
            ArrayList<String> fileNames = new ArrayList<String>();
            ArrayList<String> fileSysNames = new ArrayList<String>();
            String category = null;
            int count = 1;
            for (FileItem item : items) {
                if (item.isFormField()) {
                	if(count == 1) {
                		category = item.getString();
                	}
                	sb.append(item.getString("utf-8"));
                    sb.append("-");
                } else {
                	String overlap = UUID.randomUUID().toString();
                    if (item.getSize() > 0) {
                        String separator = File.separator;
                        int index =  item.getName().lastIndexOf(separator);
                        String fileName = item.getName().substring(index  + 1);
                        String fileSysName = overlap + "_" + fileName;
                        File uploadFile = new File(fileDAO.getPath() + category +  separator + fileSysName);
                        item.write(uploadFile);
                        fileNames.add(fileName);
                        fileSysNames.add(fileSysName);
                    }
                }
                count++;
            }
            String[] value = sb.toString().substring(0, sb.toString().lastIndexOf("-")).split("-");
            
            PostDAO postDAO = new PostDAO();
            int result = postDAO.write(value[0], value[1], value[3], value[2]);
            if(result == 1) {
                for(int i = 0; i < fileNames.size(); i++) {
                	fileDAO.upload(value[0], postDAO.getNext(value[0]) - 1, fileNames.get(i), fileSysNames.get(i));
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
			System.err.println("FileServlet FileUploadexception error");
		} catch (Exception e) {
			System.err.println("FileServlet Exception error write");
		}
    }

}
