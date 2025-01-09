package com.viewservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.connection.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewDoctors")
public class ViewDoctorsServlet extends HttpServlet {
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
				ResultSet res=stmt.executeQuery("select * from doctors");
				
				writer.println("<table border=1>\r\n"
						+ "    <th>id</th>\r\n"
						+ "    <th>name</th>\r\n"
						+ "    <th>specialization</th>\r\n"
						+ "    <th>email</th>\r\n"
						+ "    <th>phone</th>\r\n"
						+ "    <th>availability</th>");
				
				while(res.next()==true) {
					int id=res.getInt(1);
					String name = res.getString(2);
					String spe = res.getString(3);
					String email = res.getString(4);
					String phone = res.getString(5);
					String ava = res.getString(6);
					
					writer.println("\r\n"
							+ "    <tr>\r\n"
							+ "        <td>"+id+"</td>\r\n"
							+ "        <td>"+name+"</td>\r\n"
							+ "        <td>"+spe+"</td>\r\n"
							+ "        <td>"+email+"</td>\r\n"
							+ "        <td>"+phone+"</td>\r\n"
							+ "        <td>"+ava+"</td>\r\n"
							+ "    </tr>");
				
				}
				writer.println("</table>");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}


