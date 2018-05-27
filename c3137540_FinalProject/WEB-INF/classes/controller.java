import java.io.IOException;
import java.sql.SQLException;
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
		//Displaying issue details on ViewIssueDetails.jsp
		try {
			if(request.getParameter("id") != null) {
				UserDA dataAccess = new UserDA();
				HttpSession userSession = request.getSession();
				Issue clickedIssue = dataAccess.getIssue(Integer.parseInt(request.getParameter("id")));

				if(clickedIssue != null) {
					userSession.setAttribute("currentIssue", clickedIssue);
					
					List<Comment> currentCommentList = clickedIssue.getComments();
					userSession.setAttribute( "currentCommentList", currentCommentList); //To be displayed in a display:table on the page
					redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");
				}
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("login") != null) {
			login(request, response);
		}else if(request.getParameter("addComment") != null){
			addComment(request, response);
		}
	}

	private void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			HttpSession userSession = request.getSession();
			String commentTextBox = request.getParameter("commentBox");

			if(commentTextBox.length() <= 255) { //DB field for commentValue is 255 chars
				UserDA dataAccess = new UserDA();
				User userLoggedIn = (User) userSession.getAttribute("userLoggedIn");
				Issue currentIssue = (Issue) userSession.getAttribute("currentIssue");
				
				Comment c = new Comment();
				c.setUserID(userLoggedIn.getUserID());
				c.setIssueID(currentIssue.getIssueID());
				c.setCommentValue(commentTextBox);

				dataAccess.insertComment(c); //Add to comment table in DB
				List<Comment> currentCommentList = currentIssue.getComments(); //Retrieve comments already stored in the current issue and add the new comment
				currentCommentList.add(c);
				userSession.setAttribute("currentIssue", currentIssue); 		//Save the current issue with the new comment back to session so it displays when the page is loaded
			}else {
				userSession.setAttribute("errorMessage", "Sorry your comment is too long. Please ensure it is not greater than 255 characters in length.");
			}
			redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			UserDA dataAccess = new UserDA();
			HttpSession userSession = request.getSession();
			User userLoggedIn = dataAccess.login(request.getParameter("userName"), request.getParameter("password"));

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void redirect(HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);
		rd.forward(request, response);
	}
}
