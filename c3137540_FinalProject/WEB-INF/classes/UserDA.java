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
}
