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
 * Servlet implementation class balancedata
 */
@WebServlet("/balancedata")
public class balancedata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public balancedata() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bal = 0;
		try{
			final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
			final String DB_URL="jdbc:mysql://localhost:9689/bank";
			final String USER="root";
			final String PASS="Krishna@9689";
			response.setContentType("text/html");
			
		
			System.out.println("Hi");
			
			Connection conn=null;
			PreparedStatement stmt=null;
			
			Class.forName(JDBC_DRIVER);
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			stmt=conn.prepareStatement("select *  from details where Ac=? and username=? and password=?");
			int acc=Integer.parseInt(request.getParameter("acno"));
			stmt.setInt(1,acc);
			stmt.setString(2, request.getParameter("username"));
			stmt.setString(3, request.getParameter("password"));
			ResultSet rs=stmt.executeQuery();
			  PrintWriter out= response.getWriter();
			Statement st=conn.createStatement();
			if(rs.next())
			{
				try
				{   
				    System.out.println("select amt from daccount where acno="+acc);
					ResultSet rs1=st.executeQuery("select amt from daccount where Ac="+acc);
					if(rs1.next()) bal=rs1.getInt(1);
				}
				catch(Exception e){System.out.println(e);}

			  
			    out.println("<HTML><CENTER><BODY><BR><BR><H2> Balance In Your Account IS : "+bal+"</H2></BODY></CENTER</HTML>");

				
				System.out.println("Balance is : "+bal);
			}
			else
			{
				 out.println("<HTML><CENTER><BODY><BR><BR><H2> The Account Number DOes not exist...</H2></BODY></CENTER</HTML>");

			}
	

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
