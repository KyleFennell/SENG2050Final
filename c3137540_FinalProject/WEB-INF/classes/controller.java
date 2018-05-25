import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("id") != null) {
			UserDA dataAccess = new UserDA();
			HttpSession userSession = request.getSession();
			
			Issue clickedIssue = dataAccess.getIssue(Integer.parseInt(request.getParameter("id")));
			if(clickedIssue != null) {
				userSession.setAttribute("currentIssue", clickedIssue);
				redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDA dataAccess = new UserDA();
		User userLoggedIn = dataAccess.login(request.getParameter("userName"), request.getParameter("password"));
		
		HttpSession userSession = request.getSession();
		if(userLoggedIn != null) {
			userSession.setAttribute("userLoggedIn", userLoggedIn);
			List<Issue> myIssues = dataAccess.getUserMyIssues(userLoggedIn.getUserID(), userLoggedIn.isStaff());
			userSession.setAttribute("myIssues", myIssues);
			
			if(userLoggedIn.isStaff()) {
				redirect(request, response,"/ITViews/ITMainPage.jsp");
			}else {
				redirect(request, response,"/UserViews/UserMainPage.jsp");
			}
		}else {
			redirect(request, response,"/SharedViews/Login.jsp");
		}
	}
	
	
	private void redirect(HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);
		rd.forward(request, response);
	}
}
