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

@WebServlet("/ManageMedicalRecords")
public class ManageMedicalRecords extends HttpServlet {
	Connection con = null;
	@Override
	public void init() throws ServletException {
		con=ConnectionFactory.requestConnection();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("name");
		String password=req.getParameter("password");
		int id = Integer.parseInt(name);
		try {
			PreparedStatement pstmt=con.prepareStatement("delete from medical_records where record_id = ? and date = ?");
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			int res=pstmt.executeUpdate();
			if(res==1) {
				resp.getWriter().println("<h1>Medical Record "+id+" deleted!!!<h1>");
			}
			else {
				resp.getWriter().println("<h1>Please Enter Correct Details<h1>");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
