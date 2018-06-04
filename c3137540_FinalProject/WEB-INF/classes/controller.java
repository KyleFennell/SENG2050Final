import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

	/*
	 * This method is called when a user clicks the logout link
	 * The session is invalidated 
	 * A success message is saved to a request attribute
	 * Then the user is redirected back to the login page
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		request.setAttribute("errorMessage", "Success. You have been logged out.");
		redirect(request, response,"/Login.jsp");
	}

	/*
	 * Main method in the controller. Relevant methods are called depending on the parameter sent via the POST method e.g. login, issueID etc...
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDA dataAccess = new UserDA();
		HttpSession userSession = request.getSession();

		try {
			if(request.getParameter("login") != null) {		
				login(dataAccess, userSession, request, response);
			}else if(request.getParameter("addComment") != null){
				addComment(dataAccess, userSession, request, response);
			}else if(request.getParameter("assignedStaff") != null || request.getParameter("issueStatus") != null) {
				updateIssue(dataAccess, userSession, request, response);
			}else if(request.getParameter("issueID") != null) {
				displayIssueDetails(request, response, dataAccess, userSession);
			}else if(request.getParameter("startDate")  != null || request.getParameter("endDate")  != null) {
				setMaintenance(request, response, userSession, dataAccess);
			}else {
				User userLoggedIn = (User) userSession.getAttribute("userLoggedIn");
				if(userLoggedIn.isStaff()) {
					redirect(request, response,"/ITViews/ITMainPage.jsp");
				}else {
					redirect(request, response,"/UserViews/UserMainPage.jsp");
				}
			}
		}catch(java.net.ConnectException | NullPointerException e){
			request.setAttribute("errorMessage", "Database connection failed. Please try again later.");
			redirect(request, response,"/Login.jsp");
		}catch(Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			redirect(request, response,"/Login.jsp");
		}
	}

	/*
	 * A plain text string representing a password value is passed in and hashed using MD5, it returns the hashed string. 
	 * This hashed value is used for the login process (Compare hashed user input with hashed DB value).
	 * Returns a String
	 * */
	private String hashPassword(String password) {
		String passwordToHash = password;
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(passwordToHash.getBytes());
			
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	/*
	 * This method allows IT Staff to add scheduled maintenance dates which are displayed to users of the IT services portal
	 * Check that values have been entered into both datepickers
	 * Call method checkValidDates to ensure that these dates are valid dates. If this is true the method to insert them into the DB is called (insertMaintenance).
	 * Then the session attribute maintenanceDates is also updated 
	 * */
	private void setMaintenance(HttpServletRequest request, HttpServletResponse response, HttpSession userSession, UserDA dataAccess) throws ServletException, IOException {
		String message = "";

		if(request.getParameter("startDate") == null || request.getParameter("startDate").equals("")) {
			message = "Please set a start date.";
		}else if(request.getParameter("endDate") == null || request.getParameter("endDate").equals("")) {
			message = "Please set an end date.";
		}else {
			if(checkValidDates(request.getParameter("startDate")) && checkValidDates(request.getParameter("endDate"))) {
				try {         
					dataAccess.insertMaintenance(request.getParameter("startDate"), request.getParameter("endDate")) ;
					HashMap<String, String> maintenanceDates = dataAccess.getMaintenance();
					userSession.setAttribute("maintenanceDates", maintenanceDates); //Update session
					message = "Maintenance successfully added.";

				} catch (SQLException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
			}else {
				message = "Invalid date/s entered.";
			}
		}
		request.setAttribute("errorMessage", message);
		redirect(request, response,"/ITViews/ITMainPage.jsp");
	}

	/*
	 * Ensures that the string parameter passed into the method can be evaluated to a valid date
	 * Returns boolean
	 * */	
	private boolean checkValidDates(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(dateString.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/*
	 * This method is used to retrieve the issue details of the selected issue from the database
	 * Then set the relevant session attributes in order to display this information on the ViewIssueDetails page
	 * */
	private void displayIssueDetails(HttpServletRequest request, HttpServletResponse response, UserDA dataAccess, HttpSession userSession) throws ServletException, IOException {
		try {
			if(request.getParameter("issueID") != null) {

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

	/*
	 * This method sets the session attributes stafflist and statuslist which are used to populate drop down lists on the IssueStatusDropDown and IssueActions pages
	 * */
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

	/*
	 * Updates an issue's status or assigned IT staff member in the database.
	 * Calls data access methods to update the issue's status or currently assigned ITStaffID, in the database
	 * Set errorMessage request object (Displays a success msg) and currentIssue (so the page displays the updated info once redirected)
	 * */
	private void updateIssue(UserDA dataAccess, HttpSession userSession, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int staffID = 0;
			if(request.getParameter("assignedStaff") != null) {
				staffID = Integer.parseInt(request.getParameter("assignedStaff")); //Retrieve UpdateIssueform.jsp values
			}
					
			String status = request.getParameter("issueStatus").toString();		
			Issue currIssue = (Issue) userSession.getAttribute("currentIssue");
			if(userSession.getAttribute("currentIssue") == null) {
				currIssue = dataAccess.getIssue(Integer.parseInt(request.getParameter("issueID")));				
			}
			
			if(!status.equals("noneSelected")) {//Update Issue with a new status
				dataAccess.updateIssueStatus(status, currIssue.getIssueID());
			} 

			if(staffID != 0) { //Update Issue with a new ITStaffID
				dataAccess.updateIssueITStaffID(staffID, currIssue.getIssueID());
			} 

			//Update session variables
			request.setAttribute("errorMessage", "Success! Issue has been updated.");
			currIssue = dataAccess.getIssue(currIssue.getIssueID()); 
			userSession.setAttribute("currentIssue", currIssue); 
			
			setSessionLists((User) userSession.getAttribute("userLoggedIn"), userSession, dataAccess);
			
			redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");	
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is used to insert a comment into the database and update the relevant session variables needed to display info on the page.
	 * Check that the comment is not too long (less than or equal to 255 characters) as this is how much space has been reserved for this field in the database.
	 * Create the new comment object and populate it.
	 * Call the method to insert it into the database - insertComment
	 *  Update session lists.
	 * */
	private void addComment(UserDA dataAccess, HttpSession userSession, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
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

				request.setAttribute("errorMessage", "Success! Your comment has been added.");
			}else {
				request.setAttribute("errorMessage", "Sorry your comment is too long. Please ensure it is not greater than 255 characters in length.");
			}
			redirect(request, response,"/SharedViews/ViewIssueDetails.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * This method is used to check that a valid user name and password has been used to login to the website
	 * Calls the method (dataAccess.login()) to check the database for a matching userName and password. 
	 * If this method returns a valid user object then the user is redirected to the relevant starting page depending on their user type.
	 * Otherwise they are redirected to the login page with an error message.
	 * */
	private void login(UserDA dataAccess, HttpSession userSession, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String hashedPW = hashPassword(request.getParameter("password")); //Hash password
			User userLoggedIn = dataAccess.login(request.getParameter("userName"), hashedPW); //Check entered userName and hashed pw value match those stored in the User table
			
			if(userLoggedIn != null) {
				setSessionLists(userLoggedIn, userSession, dataAccess); //Set session lists to display appropriate page data
				if(userLoggedIn.isStaff()) { //redirect depending on type of user
					redirect(request, response,"/ITViews/ITMainPage.jsp");
				}else {
					redirect(request, response,"/UserViews/UserMainPage.jsp");
				}
			}else {
				request.setAttribute("errorMessage", "Login details incorrect. Please try again.");
				redirect(request, response,"/Login.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Sets session lists: myIssues, pendingIssues, maintenanaceDates (only if null) 
	 * Calls populateDropDown to populate statusList and staffList
	 * */
	private void setSessionLists(User userLoggedIn, HttpSession userSession, UserDA dataAccess) {
		userSession.setAttribute("userLoggedIn", userLoggedIn);
		List<Issue> myIssues;
		try {
			myIssues = dataAccess.getUserMyIssues(userLoggedIn.getUserID(), userLoggedIn.isStaff()); //Populate myIssues list
			userSession.setAttribute("myIssues", myIssues);

			if(!userLoggedIn.isStaff()) { //If just a normal user add pending issues to session list - used in notifications.jsp include
				List<Issue> myPendingIssues = new ArrayList<Issue>();
				for(Issue issue: myIssues) {
					if(issue.getStatus().equals("Waiting on Reporter")) {
						myPendingIssues.add(issue);
					}
				}
				userSession.setAttribute("myPendingIssues", myPendingIssues);
			}

			if(userSession.getAttribute("maintenanceDates") == null) { //populate maintenanceDates list
				HashMap<String, String> maintenanceDates = dataAccess.getMaintenance(); //used in notifications.jsp include
				userSession.setAttribute("maintenanceDates", maintenanceDates);
			}
			
			if(userSession.getAttribute("statusList") == null || userSession.getAttribute("staffList") == null) {//populate statusList and staffList
				populateDropDown(dataAccess, userSession);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Custom method to redirect the user
	 * Uses RequestDispatcher forward method to forward the request object to the JSP page that the user is re-directed to
	 * */
	private void redirect(HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);
		rd.forward(request, response);
	}
}
