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

import com.database.connection.ConnectionFactory;

@WebServlet("/ViewPatients")
public class ViewPatients extends HttpServlet {
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
			ResultSet res=stmt.executeQuery("select * from pateints");
			
			writer.println("<table border=2>\r\n"
					+ "    <th>id</th>\r\n"
					+ "    <th>name</th>\r\n"
					+ "    <th>age</th>\r\n"
					+ "    <th>gender</th>\r\n"
					+ "    <th>email</th>\r\n"
					+ "    <th>phone</th>");
			
			while(res.next()==true) {
				int id=res.getInt(1);
				String name = res.getString(2);
				int age = res.getInt(3);
				String gender = res.getString(4);
				String email = res.getString(5);
				String phone = res.getString(6);
				
				writer.println("\r\n"
						+ "    <tr>\r\n"
						+ "        <td>"+id+"</td>\r\n"
						+ "        <td>"+name+"</td>\r\n"
						+ "        <td>"+age+"</td>\r\n"
						+ "        <td>"+gender+"</td>\r\n"
						+ "        <td>"+email+"</td>\r\n"
						+ "        <td>"+phone+"</td>\r\n"
						+ "    </tr>");
			
			}
			writer.println("</table>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
