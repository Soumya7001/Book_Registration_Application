package com.soumya.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String q = "delete from BOOKDATA where id=?;";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
				PrintWriter pw = res.getWriter();
				//set content type
				res.setContentType("text/html");
				//get the id of record 
				int id = Integer.parseInt(req.getParameter("id"));
				
				//Load JDBC Driver
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(Exception e) {
					e.printStackTrace();
				}
				//Generate a connection
				try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "Soumya@7001");
					PreparedStatement pstmt = con.prepareStatement(q);) {
					
					pstmt.setInt(1, id);
					
					int count = pstmt.executeUpdate();
					if(count == 1) {
						pw.println("<h2>Record is deleted successfully</h2>");
					}else { 
						pw.println("<h2>Record is not deleted successfully</h2>");
					} 
					
					
				}catch(SQLException se) {
					se.printStackTrace();
					pw.println("<h1>"+se.getMessage()+"</h2>");
				}catch(Exception e) {
					e.printStackTrace();
					pw.println("<h1>"+e.getMessage()+"</h2>");
				}
				pw.println("</br>");
				pw.println("<a href='index.html'>Home</a>");
				pw.println("<br>");
				pw.println("<a href='booklist'>Book List</a>");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req, res);
	}
}
