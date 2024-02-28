package in.register.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reg")
public class RegisterUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String birth = req.getParameter("bdate");
		String gender = req.getParameter("inlineRadioOptions");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","root");
			PreparedStatement ps = conn.prepareStatement("insert into register1 values(?,?,?,?,?,?,?)");
			ps.setString(1,fname);
			ps.setString(2,lname);
			ps.setString(3,birth);
			ps.setString(4,gender);
			ps.setString(5,email);
			ps.setString(6,password);
			ps.setString(7,phone);
			
			int i = ps.executeUpdate();
			if(i>0) {
				
				out.print("<h1 style='color:green'>Registration is Successfull</h1>");
				RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
				rd.include(req, resp);
			}else {
				out.print("<h1 style='color:red'>Registration is Failed !!!</h1>");
				RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
				rd.include(req, resp);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

}
