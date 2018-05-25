import java.util.List;

public interface UserDAO {
	
	public List<User> getAllUsers();
	public User getUser(int userID);
	public User getUser(String userName);
	public User login(String userName, String pw);
	public List<Issue> getUserMyIssues(int userID, boolean isStaff);
	public List<String> getKeywords(int issueID);
	public List<Comment> getComments(int issueID);
	public Issue getIssue(int issueID);
	
}
