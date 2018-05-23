import java.io.IOException;
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDA dataAccess = new UserDA();
		User userLoggedIn = dataAccess.login(request.getParameter("userName"), request.getParameter("password"));
		
		HttpSession userSession = request.getSession();
		
		if(userLoggedIn != null) {
			userSession.setAttribute("userLoggedIn", userLoggedIn);
			redirect(request, response,"/LoggedIn.jsp");
		}else {
			redirect(request, response,"/login.jsp");
		}
	}
	
	
	private void redirect(HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);
		rd.forward(request, response);
	}
}
