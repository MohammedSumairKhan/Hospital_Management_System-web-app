package com.hospital.servlets;

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

@WebServlet("/AddMedicalRecords")
public class AddMedicalRecords extends HttpServlet {
	Connection con = null;
	@Override
	public void init() throws ServletException {
		 con=ConnectionFactory.requestConnection();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String patient_id1=req.getParameter("pid");
		String doctor_id1 = req.getParameter("did"); 
		String diagnosis = req.getParameter("dai");
		String prescription = req.getParameter("pre");
		String date = req.getParameter("date");
		int patient_id = Integer.parseInt(patient_id1);
		int doctor_id = Integer.parseInt(doctor_id1);
		
		try {
			con.setAutoCommit(false);
			
			String query = "insert into medical_records(`pateint_id`,`doctor_id`,`diagnosis`,`prescription`,`date`) values (?,?,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(query);
			pstmt.setInt(1, patient_id);
			pstmt.setInt(2, doctor_id);
			pstmt.setString(3, diagnosis);
			pstmt.setString(4,prescription);
			pstmt.setString(5, date);
			
			int i=pstmt.executeUpdate();
			System.out.println(i);
			 if (i == 1) {
	                con.commit();
	                resp.setContentType("text/html");
	                resp.getWriter().println("<h1>Medical record added successfully. Thank you!</h1>");
	            } else {
	                resp.setContentType("text/html");
	                resp.getWriter().println("<h1>Failed to book the appointment. Please try again later.</h1>");
	            }
		} catch (SQLException e) {
			e.printStackTrace();
	
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
