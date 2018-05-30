import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;

public interface UserDAO {
	
	//User DB methods	
	public List<User> getAllUsers() throws SQLException;
	public List<User> getStaffUsers() throws SQLException;
	public User getUser(int userID) throws SQLException;
	public User getUser(String userName) throws SQLException;
	public User login(String userName, String pw) throws SQLException;
	
	//Issue DB methods
	public Issue getIssue(int issueID) throws SQLException;
	public List<Issue> getUserMyIssues(int userID, boolean isStaff) throws SQLException;
	public void updateIssueStatus(String status, int issueID) throws SQLException;
	public void updateIssueITStaffID(int staffID, int issueID) throws SQLException;
	
	//Keyword DB methods
	public List<String> getKeywords(int issueID) throws SQLException;
	
	//Comment DB methods
	public List<Comment> getComments(int issueID) throws SQLException;
	public void insertComment(Comment commentVal) throws SQLException;
	
	//Other methods
	public void insertMaintenance(String start, String end) throws SQLException;
	public HashMap<String, String> getMaintenance() throws SQLException;
}
