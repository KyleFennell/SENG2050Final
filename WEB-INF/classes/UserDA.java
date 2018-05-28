import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDA implements UserDAO{

	@Override
	public List<User> getAllUsers() throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM User");
			rs = ps.executeQuery();
			
			List<User> userList = new ArrayList<User>();
			while(rs.next())
			{
				User user = extractUserFromResultSet(rs);
				userList.add(user);
			}
			
			
			return userList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public List<User> getStaffUsers() throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM User");
			rs = ps.executeQuery();
			
			List<User> userList = new ArrayList<User>();
			while(rs.next())
			{
				User user = extractUserFromResultSet(rs);
				if(user.isStaff()) {
					userList.add(user);
				}
			}
			
			
			return userList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}

	@Override
	public User getUser(int id) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM User WHERE userID = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next())
			{
				return extractUserFromResultSet(rs);
			}
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public User getUser(String userName) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM User WHERE userName = ?");
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if(rs.next())
			{
				return extractUserFromResultSet(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public User login(String userName, String pw) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
	    try {
	    	connection = DBConnection.getConnection();
	        ps = connection.prepareStatement("SELECT * FROM User WHERE userName=? AND password=?");
	        ps.setString(1, userName);
	        ps.setString(2, pw);
	        rs = ps.executeQuery();
	        if(rs.next())
	        {
	        	return extractUserFromResultSet(rs);
	        }
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
	    return null;
	}
	
	@Override
	public List<Issue> getUserMyIssues(int userID, boolean isStaff) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM Issue WHERE UserID=?");
			if(isStaff) {
				ps = connection.prepareStatement("SELECT * FROM Issue WHERE ITStaffID=?"); //If the user is an IT staff member retrieve all Issues assigned to them
			}
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			
			List<Issue> issueList = new ArrayList<Issue>();
			while(rs.next())
			{
				Issue issue = extractIssueFromResultSet(rs);
				issueList.add(issue);
			}
			return issueList;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public Issue getIssue(int issueID) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM Issue WHERE issueID=?");
			ps.setInt(1, issueID);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				return extractIssueFromResultSet(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public void updateIssueStatus(String status, int issueID) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("UPDATE Issue Set status =? WHERE issueID =?");
			ps.setString(1, status);
			ps.setInt(2, issueID);
			
			// execute insert SQL statement
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, null, ps); //Release DB resources
		}
	}
	
	@Override
	public void updateIssueITStaffID(int staffID, int issueID) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("UPDATE Issue Set ITStaffID=? WHERE issueID =?");
			ps.setInt(1, staffID);
			ps.setInt(2, issueID);
			
			// execute insert SQL statement
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, null, ps); //Release DB resources
		}
	}
	
	@Override
	public List<Comment> getComments(int issueID) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT * FROM Comment WHERE issueID=?");
			ps.setInt(1, issueID);
			rs = ps.executeQuery();
			
			List<Comment> commentsList = new ArrayList<Comment>();
			while(rs.next())
			{
				Comment comm = new Comment();
				comm.setCommentID(rs.getInt("commentID"));
				comm.setIssueID(rs.getInt("issueID"));
				comm.setUserID(rs.getInt("userID"));
				comm.setUserName(rs.getString("userName"));
				comm.setCommentValue(rs.getString("commentValue"));

				commentsList.add(comm);
			}
			return commentsList;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public List<String> getKeywords(int issueID) throws SQLException{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("SELECT keyword FROM Keyword WHERE issueID=?");
			ps.setInt(1, issueID);
			rs = ps.executeQuery();
			
			List<String> keywordList = new ArrayList<String>();
			while(rs.next())
			{
				keywordList.add(rs.getString("keyword"));
			}
			return keywordList;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, rs, ps); //Release DB resources
		}
		return null;
	}
	
	@Override
	public void insertComment(Comment comment) throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("INSERT INTO Comment (issueID, UserID, commentValue, userName) VALUES (?,?,?,?)");
			ps.setInt(1, comment.getIssueID());
			ps.setInt(2, comment.getUserID());
			ps.setString(3, comment.getCommentValue());
			ps.setString(4, comment.getUserName());
			
			// execute insert SQL statement
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			closeConnections(connection, null, ps); //Release DB resources
		}
	}
	
	private void closeConnections(Connection connection, ResultSet rs, PreparedStatement ps) throws SQLException{
		if (ps != null) {
			ps.close();
		}

		if(rs != null) {
			ps.close();
		}
		
		if (connection != null) {
			connection.close();
		}
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
		issue.setReportedDateTime(rs.getTimestamp("reportedDateTime"));
		issue.setResolvedDateTime(rs.getTimestamp("resolvedDateTime"));
		//issue.setReportedDateTime(rs.getDate("reportedDateTime"));
		//issue.setResolvedDateTime(rs.getDate("resolvedDateTime"));
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
