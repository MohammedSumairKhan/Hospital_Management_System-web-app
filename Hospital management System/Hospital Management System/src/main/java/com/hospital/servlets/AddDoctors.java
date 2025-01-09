package com.hospital.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.database.connection.ConnectionFactory;

@WebServlet("/AddDoctors")
public class AddDoctors extends HttpServlet {
	Connection con = null;
	@Override
	public void init() throws ServletException {
		 con=ConnectionFactory.requestConnection();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("name");
		String specialization=req.getParameter("specialization");
		String email=req.getParameter("email");
		String phone=req.getParameter("phone");
		String avalability=req.getParameter("avalability");
		String password = req.getParameter("password");
		
		try {
			con.setAutoCommit(false);
			
			String query = "insert into doctors(`name`,`specialization`,`email`,`phone`,`avalability`,`password`) values (?,?,?,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, specialization);
			pstmt.setString(3,email);
			pstmt.setString(4, phone);
			pstmt.setString(5, avalability);
			pstmt.setString(6, password);
			int i=pstmt.executeUpdate();
			System.out.println(i);
			if(i==1) {
				con.commit();
				resp.setContentType("text/html");
				resp.getWriter().println("<h1>"+name+" 's  Details Are Successfully Added.Thank You!</h1>");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			 if (e.getMessage().contains("Duplicate entry")) {
				 try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				 RequestDispatcher rd=req.getRequestDispatcher("errordoctors.html");
					rd.forward(req, resp);
			 }
		}	
		
		
	}
	 @Override
	    public void destroy() {
	        try {
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
   
}
