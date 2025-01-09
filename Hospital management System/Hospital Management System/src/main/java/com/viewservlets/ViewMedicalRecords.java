package com.viewservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.spi.CalendarNameProvider;

import com.database.connection.ConnectionFactory;

@WebServlet("/ViewMedicalRecords")
public class ViewMedicalRecords extends HttpServlet {
	
	Connection con = null;
	@Override
	public void init() throws ServletException {
		con=ConnectionFactory.requestConnection();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter	writer=resp.getWriter();
		
		
		try {
			Statement stmt=con.createStatement();
			ResultSet res=stmt.executeQuery("select * from medical_records");
			
			writer.println("<table border=2>\r\n"
					+ "    <th>record_id</th>\r\n"
					+ "    <th>patient_id</th>\r\n"
					+ "    <th>doctor_id</th>\r\n"
					+ "    <th>diagnosis</th>\r\n"
					+ "    <th>prescription</th>\r\n"
					+ "    <th>date</th>");
			
			while(res.next()==true) {
				int id=res.getInt(1);
				int p_id = res.getInt(2);
				int d_id = res.getInt(3);
				String dai = res.getString(4);
				String pre = res.getString(5);
				String date = res.getString(6);
				
				writer.println("\r\n"
						+ "    <tr>\r\n"
						+ "        <td>"+id+"</td>\r\n"
						+ "        <td>"+p_id+"</td>\r\n"
						+ "        <td>"+d_id+"</td>\r\n"
						+ "        <td>"+dai+"</td>\r\n"
						+ "        <td>"+pre+"</td>\r\n"
						+ "        <td>"+date+"</td>\r\n"
						+ "    </tr>");
			
			}
			writer.println("</table>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
