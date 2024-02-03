package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class opendata
 */
@WebServlet("/opendata")
public class opendata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public opendata() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int Ac=0;
		try{
			final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
			final String DB_URL="jdbc:mysql://localhost:9689/bank";
			final String USER="root";
			final String PASS="Krishna@9689";
			response.setContentType("text/html");
			
			System.out.println("Hi");
			
			Connection conn=null;
			PreparedStatement stmt=null;
			PreparedStatement stmt2=null;
			
			Class.forName(JDBC_DRIVER);
			Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
			 Statement st=con.createStatement();
			   ResultSet rs=st.executeQuery("select max(Ac) from details");
			   if(rs.next())
				{
					  Ac=rs.getInt(1);
					  Ac=Ac+1;
					  System.out.println(Ac);
				}
			   
			   
			   
			
			Class.forName(JDBC_DRIVER);
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			stmt=conn.prepareStatement("select * from details");
			System.out.println("Connected");
			stmt=conn.prepareStatement("insert into details values(?,?,?,?,?)");
			stmt.setInt(1,Ac);
			stmt.setString(2, request.getParameter("uname"));
			stmt.setString(3, request.getParameter("pwd"));
			stmt.setString(4, request.getParameter("add"));
			stmt.setString(5, request.getParameter("phno"));
			stmt.executeUpdate();
			
			stmt2=conn.prepareStatement("select * from daccount");
			stmt2=conn.prepareStatement("insert into daccount values(?,?)");
			stmt2.setInt(1,Ac);
			stmt2.setInt(2, Integer.valueOf(request.getParameter("amt")));
			stmt2.executeUpdate();
		    System.out.println("Record Inserted Successfully...");
		    
		    PrintWriter out= response.getWriter();
		    out.println("<HTML><CENTER><BODY><BR><BR><H2> The A/C No. is : "+Ac+"</H2></BODY></CENTER</HTML>");

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
