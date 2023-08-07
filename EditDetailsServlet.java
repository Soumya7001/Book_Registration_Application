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
@WebServlet("/editurl")
public class EditDetailsServlet extends HttpServlet {
	private static final String q = "update BOOKDATA set BOOKNAME=?, BOOKEDITION=?, BOOKPRICE=? where id=?;";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
				PrintWriter pw = res.getWriter();
				//set content type
				res.setContentType("text/html");
				//get the id of record 
				int id = Integer.parseInt(req.getParameter("id"));
				//get the edited data
				String bookname = req.getParameter("bookName");
				String bookedition = req.getParameter("bookEdition");
				float bookprice = Float.parseFloat(req.getParameter("bookPrice"));
				//Load JDBC Driver
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(Exception e) {
					e.printStackTrace();
				}
				//Generate a connection
				try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "Soumya@7001");
					PreparedStatement pstmt = con.prepareStatement(q);) {
					
					pstmt.setString(1, bookname);
					pstmt.setString(2, bookedition);
					pstmt.setFloat(3, bookprice);
					pstmt.setInt(4, id);
					int count = pstmt.executeUpdate();
					if(count == 1) {
						pw.println("<h2>Record is updated successfully</h2>");
					}else { 
						pw.println("<h2>Record is not updated successfully</h2>");
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
