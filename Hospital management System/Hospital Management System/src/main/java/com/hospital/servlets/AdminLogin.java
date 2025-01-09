package com.hospital.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.database.connection.ConnectionFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	Connection con = null;
	@Override
	public void init() throws ServletException {
		 con=ConnectionFactory.requestConnection();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name=req.getParameter("username");
		String password=req.getParameter("password");
		
		try {
			PreparedStatement pstmt=con.prepareStatement("select * from admins where name = ? and password = ?");
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			ResultSet res=pstmt.executeQuery();
            if (res.next()==true) {
            	System.out.println("data");
                String un = res.getString("name").trim(); // Trim values from the database
                String pwd = res.getString("password").trim();

                if (name.equalsIgnoreCase(un) && password.equals(pwd)) {
                    RequestDispatcher rd = req.getRequestDispatcher("admindashboard.html");
                    rd.forward(req, resp);
                } else {
                    resp.getWriter().println("<h1>Invalid Login! Please try again.</h1>");
                }
            } else {
                resp.getWriter().println("<h1>Invalid Login! Please try again.</h1>");
            }

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 @Override
	    public void destroy() {
	        try {
	            if (con != null) con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}











