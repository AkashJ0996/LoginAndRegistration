package in.login.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logForm")
public class LoginUser extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailUser = req.getParameter("email");
		String passwordUser = req.getParameter("pass");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","root");
			PreparedStatement ps = con.prepareStatement("select * from register1 where email=? and password=?");
			ps.setString(1,emailUser);
			ps.setString(2, passwordUser);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				
				HttpSession session = req.getSession();
				session.setAttribute("fname",rs.getString("firstname"));
				session.setAttribute("lname",rs.getString("lastname"));
				session.setAttribute("email",rs.getString("email"));
				session.setAttribute("phone",rs.getString("phone"));
               
				RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
				rd.include(req, resp);
			}else {
			
				out.print("<h1 style='color:red'> Login faild <h1>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/loginForm");
				rd.include(req, resp);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
