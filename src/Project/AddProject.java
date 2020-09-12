package Project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddProject
 */
@WebServlet("/AddProject")
public class AddProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		int year = Integer.parseInt(request.getParameter("year"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		ProjectDAO projectDAO = new ProjectDAO();
		int result = projectDAO.addProject(year, title, content);

		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("console.log('" + result + "')");
		script.println("</script>");
		if(result == 1) {
			script.println("<script>");
			script.println("alert('추가에 성공했습니다.')");
			script.println("location.href = '../LabWebSite/Project/Project.jsp'");
			script.println("</script>");
			script.close();
		} else {
			script.println("<script>");
			script.println("alert('추가에 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
			script.close();
		}
	}

}
