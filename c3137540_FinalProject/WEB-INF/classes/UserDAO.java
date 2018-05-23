import java.util.List;

public interface UserDAO {
	
	public List<User> getAllUsers();
	public User getUser(int userID);
	public User getUser(String userName);
	public User login(String userName, String pw);
}
