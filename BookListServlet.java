package com.soumya.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	private static final String q = "SELECT * FROM BOOKDATA";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
				PrintWriter pw = res.getWriter();
				//set content type
				res.setContentType("text/html");
				 
				
				//Load JDBC Driver
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(Exception e) {
					e.printStackTrace();
				}
				//Generate a connection
				try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "Soumya@7001");
					Statement stmt = con.createStatement();) {
					
					ResultSet s = stmt.executeQuery(q);
					pw.println("<table border='1' align='center'>");
					pw.println("<tr>");
					pw.println("<th>Book ID</th>");
					pw.println("<th>Book Name</th>");
					pw.println("<th>Book Edition</th>");
					pw.println("<th>Book Price</th>");
					pw.println("<th>Edit</th>");
					pw.println("<th>Delete</th>");
					pw.println("</tr>");
					
					while(s.next()) {
						int id = s.getInt(1);
						String bookname1 = s.getString(2);
						String bookedition1 = s.getString(3);
						float bookprice1 = s.getFloat(4);
						pw.println("<tr>");
						pw.println("<td>" +id+ "</td>");
						pw.println("<td>" +bookname1+"</td>");
						pw.println("<td>" +bookedition1+"</td>");
						pw.println("<td>" +bookprice1+"</td>");
						pw.println("<td><a href='editScreen?id="+s.getInt(1)+"'>Edit</a></td>");
						pw.println("<td><a href='deleteurl?id="+s.getInt(1)+"'>Delete</a></td>");
						
						pw.println("</tr>");
					}
					pw.println("</table>");
					
					
					
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
