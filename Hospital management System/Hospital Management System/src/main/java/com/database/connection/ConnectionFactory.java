package com.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	static Connection con = null;
	static String url="jdbc:mysql://localhost:3306/hospital_management_system";
	static String un = "root";
	static String pwd = "Sumair@1";
		public static Connection requestConnection() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection(url,un,pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		
}
