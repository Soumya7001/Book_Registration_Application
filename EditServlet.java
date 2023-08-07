package com.soumya.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/editScreen")
public class EditServlet extends HttpServlet {
	private static final String q = "SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA where id=?;";
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
					ResultSet s = pstmt.executeQuery();
					while(s.next()) {
						pw.println("<form action='editurl?id="+id+"' method='post'>");
						pw.println("<table align='center'>");
						pw.println("<tr>");
						pw.println("<td>Book Name</td>");
						pw.println("<td><input type='text' name='bookName' value='"+s.getString(1)+"'></td>");
						pw.println("</tr>");
						pw.println("<tr>");
						pw.println("<td>Book Edition</td>");
						pw.println("<td><input type='text' name='bookEdition' value='"+s.getString(2)+"'></td>");
						pw.println("</tr>");
						pw.println("<tr>");
						pw.println("<td>Book Price</td>");
						pw.println("<td><input type='text' name='bookPrice' value='"+s.getFloat(3)+"'></td>");
						pw.println("</tr>");						
						pw.println("<tr>");
						pw.println("<td><input type='submit' value='Edit'></td>");
						pw.println("<td><input type='reset' value='Cancel'></td>");
						pw.println("</tr>");
						pw.println("<table>");
						pw.println("</form>");
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
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req, res);
	}
}
