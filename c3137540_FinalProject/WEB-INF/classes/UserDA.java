import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDA implements UserDAO{

	@Override
	public List<User> getAllUsers() {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM User");
			ResultSet rs = ps.executeQuery();
			
			List<User> userList = new ArrayList<User>();
			while(rs.next())
			{
				User user = extractUserFromResultSet(rs);
				userList.add(user);
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
			return userList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUser(int id) {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE userID = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return extractUserFromResultSet(rs);
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User getUser(String userName) {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE userName = ?");
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return extractUserFromResultSet(rs);
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User login(String userName, String pw) {
	    Connection connection = DBConnection.getConnection();
	    try {
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE userName=? AND password=?");
	        ps.setString(1, userName);
	        ps.setString(2, pw);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next())
	        {
	        	return extractUserFromResultSet(rs);
	        }
	        
	        //Release DB resources
	        ps.close();
	        rs.close();
	        connection.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	@Override
	public List<Issue> getUserMyIssues(int userID, boolean isStaff){
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Issue WHERE UserID=?");
			if(isStaff) {
				ps = connection.prepareStatement("SELECT * FROM Issue WHERE ITStaffID=?"); //If the user is an IT staff member retrieve all Issues assigned to them
			}
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			List<Issue> issueList = new ArrayList<Issue>();
			while(rs.next())
			{
				Issue issue = extractIssueFromResultSet(rs);
				issueList.add(issue);
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
			return issueList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Issue getIssue(int issueID){
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Issue WHERE issueID=?");
			ps.setInt(1, issueID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				return extractIssueFromResultSet(rs);
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Comment> getComments(int issueID){
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Comment WHERE issueID=?");
			ps.setInt(1, issueID);
			ResultSet rs = ps.executeQuery();
			
			List<Comment> commentsList = new ArrayList<Comment>();
			while(rs.next())
			{
				Comment comm = new Comment();
				comm.setCommentID(rs.getInt("commentID"));
				comm.setIssueID(rs.getInt("issueID"));
				comm.setUserID(rs.getInt("userID"));
				comm.setCommentValue(rs.getString("commentValue"));
				
				commentsList.add(comm);
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
			return commentsList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> getKeywords(int issueID){
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT keyword FROM Keyword WHERE issueID=?");
			ps.setInt(1, issueID);
			ResultSet rs = ps.executeQuery();
			
			List<String> keywordList = new ArrayList<String>();
			while(rs.next())
			{
				keywordList.add(rs.getString("keyword"));
			}
			
			//Release DB resources
			ps.close();
	        rs.close();
	        connection.close();
			return keywordList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private User extractUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("userID"));
		user.setUserName(rs.getString("userName"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("firstName"));
		user.setSurname(rs.getString("surname"));
		user.setEmail(rs.getString("email"));
		user.setphoneNum(rs.getString("phoneNum"));
		user.setisStaff(rs.getBoolean("isStaff"));
		return user;
	}
	
	private Issue extractIssueFromResultSet(ResultSet rs) throws SQLException {
		Issue issue = new Issue();
		issue.setIssueID(rs.getInt("issueID"));
		issue.setTitle(rs.getString("title"));
		issue.setDescription(rs.getString("description"));
		issue.setResolutionDetails(rs.getString("resolutionDetails"));
		issue.setReportedDateTime(rs.getDate("reportedDateTime"));
		issue.setResolvedDateTime(rs.getDate("resolvedDateTime"));
		issue.setStatus(rs.getString("status"));
		issue.setUserID(rs.getInt("userID"));
		issue.setITStaffID(rs.getInt("ITStaffID"));
		issue.setCategory(rs.getString("category"));
		issue.setSubCategory(rs.getString("subCategory"));
		issue.setComments(getComments(issue.getIssueID()));
		issue.setKeywords(getKeywords(issue.getIssueID()));
		
		return issue;
	}
	
}
