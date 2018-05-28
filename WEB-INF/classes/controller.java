import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDA dataAccess = new UserDA();
		HttpSession userSession = request.getSession();
		
		if(request.getParameter("login") != null) {
			login(dataAccess, userSession, request, response);
		}else if(request.getParameter("addComment") != null){
			addComment(dataAccess, userSession, request, response);
		}else if(request.getParameter("assignedStaff") != null || request.getParameter("issueStatus") != null) {
			updateIssue(dataAccess, userSession, request, response);
		}else if(request.getParameter("issueID") != null) {
			displayIssueDetails(request, response, dataAccess, userSession);
		}
	}
	
	//Displaying issue details on ViewIssueDetails.jsp
	private void displayIssueDetails(HttpServletRequest request, HttpServletResponse response, UserDA dataAccess, HttpSession userSession) throws ServletException, IOException {
		try {
			if(request.getParameter("issueID") != null) {
				
				userSession.setAttribute("errorMessage", "");
				Issue clickedIssue = dataAccess.getIssue(Integer.parseInt(request.getParameter("issueID")));

				if(clickedIssue != null) {
					populateDropDown(dataAccess, userSession); //Populates drop down list/s on right hand side of page
					userSession.setAttribute("currentIssue", clickedIssue);
					
					List<Comment> currentCommentList = clickedIssue.getComments();
					userSession.setAttribute( "currentCommentList", currentCommentList); //To be displayed in a display:table on the page
					
					User reportedUser = dataAccess.getUser(clickedIssue.getUserID());
					userSession.setAttribute("reportedUserName", reportedUser.getUserName()); //display userName of the user who reported the issue (as well as the userID)
					redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");
				}
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populateDropDown(UserDA dataAccess, HttpSession userSession) throws SQLException {
		//Populate the IT technician list from the DB
		List<User> staffUsers = dataAccess.getStaffUsers(); 
		userSession.setAttribute("staffList", staffUsers);

		//Populate status list depending on the type of user
		User userLoggedIn = (User) userSession.getAttribute("userLoggedIn");
		List<String> status = new ArrayList<String>();
		if(userLoggedIn.isStaff()) {
			status.add("New");
			status.add("In Progress");
			status.add("Waiting On 3rd Party");
			status.add("Waiting on Reporter");
			status.add("Completed");
		}else {
			status.add("Not Accepted");
			status.add("Resolved");
		}
		userSession.setAttribute("statusList", status);
	}
	
	private void updateIssue(UserDA dataAccess, HttpSession userSession, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int staffID = Integer.parseInt(request.getParameter("assignedStaff")); //Retrieve UpdateIssueform.jsp values
			String status = request.getParameter("issueStatus").toString();
			Issue currIssue = (Issue) userSession.getAttribute("currentIssue");
			
			if(!status.equals("noneSelected")) {//Update Issue with a new status
				dataAccess.updateIssueStatus(status, currIssue.getIssueID());
			} 
			
			if(staffID != 0) { //Update Issue with a new ITStaffID
				dataAccess.updateIssueITStaffID(staffID, currIssue.getIssueID());
			} 
			
			currIssue = dataAccess.getIssue(currIssue.getIssueID()); //Update the issue object from the DB
			userSession.setAttribute("currentIssue", currIssue); //Update in issue session
			
			redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");	
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addComment(UserDA dataAccess, HttpSession userSession, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			userSession.setAttribute("errorMessage", "");
			String commentTextBox = request.getParameter("commentBox");

			if(commentTextBox.length() <= 255) { //DB field for commentValue is 255 chars
				User userLoggedIn = (User) userSession.getAttribute("userLoggedIn");
				Issue currentIssue = (Issue) userSession.getAttribute("currentIssue");
				
				Comment c = new Comment();
				c.setUserID(userLoggedIn.getUserID());
				c.setIssueID(currentIssue.getIssueID());
				c.setCommentValue(commentTextBox);
				c.setUserName(userLoggedIn.getUserName());

				dataAccess.insertComment(c); //Add to comment table in DB
				List<Comment> currentCommentList = dataAccess.getComments(currentIssue.getIssueID());
				userSession.setAttribute( "currentCommentList", currentCommentList); //Save the current issueList with the new comment back to session so it displays when the page is loaded		
				
				userSession.setAttribute("errorMessage", "Success! Your comment has been added.");
			}else {
				userSession.setAttribute("errorMessage", "Sorry your comment is too long. Please ensure it is not greater than 255 characters in length.");
			}
			redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void login(UserDA dataAccess, HttpSession userSession, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
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
