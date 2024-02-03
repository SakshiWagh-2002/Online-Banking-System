package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class closedata
 */
@WebServlet("/closedata")
public class closedata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public closedata() {
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
		
		try
		{
			final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
			final String DB_URL="jdbc:mysql://localhost:9689/bank";
			final String USER="root";
			final String PASS="Krishna@9689";
			response.setContentType("text/html");
			
			 PrintWriter out= response.getWriter();
			
			System.out.println("Hi");
			
			Class.forName(JDBC_DRIVER);
			Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
			Statement st=con.createStatement();
			Statement st2=con.createStatement();
			int acno=Integer.parseInt(request.getParameter("acno"));
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			ResultSet rs=st.executeQuery("select * from details where Ac="+acno+" and username='"+username+"' and password='"+password+"'");
			System.out.println("select * from details where Ac="+acno+" and username='"+username+"' and password='"+password+"'");
			
			if(rs.next())
			{
				try
				{
					st2.executeUpdate("delete from details where Ac="+acno+" and username='"+username+"' and password='"+password+"'");
				}
				catch(Exception e){ System.out.println(e);}
				st.executeUpdate("delete from daccount where Ac="+acno);
				System.out.println("record deleted");
				
		        out.println("<HTML><CENTER><BODY><BR><BR><H2> The Account Closed Succesfully...</H2></BODY></CENTER</HTML>");
			}
			else
			{
		        out.println("<HTML><CENTER><BODY><BR><BR><H2> The Account Does not exist...</H2></BODY></CENTER</HTML>");

			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

}
