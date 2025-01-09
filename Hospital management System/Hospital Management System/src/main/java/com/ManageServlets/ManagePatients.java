package com.ManageServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.database.connection.ConnectionFactory;

@WebServlet("/ManagePatients")
public class ManagePatients extends HttpServlet {
	Connection con = null;
	@Override
	public void init() throws ServletException {
		con=ConnectionFactory.requestConnection();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("name");
		String password=req.getParameter("password");
		try {
			PreparedStatement pstmt=con.prepareStatement("delete from pateints where name = ? and password = ?");
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			int res=pstmt.executeUpdate();
			if(res==1) {
				resp.getWriter().println("<h1>patient "+name+" deleted!!!<h1>");
			}
			else {
				resp.getWriter().println("<h1>Please Enter Correct Details<h1>");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
