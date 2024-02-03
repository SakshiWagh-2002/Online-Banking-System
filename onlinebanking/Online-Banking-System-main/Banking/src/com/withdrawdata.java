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
 * Servlet implementation class withdrawdata
 */
@WebServlet("/withdrawdata")
public class withdrawdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public withdrawdata() {
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
		
int am;
		
		try{
			final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
			final String DB_URL="jdbc:mysql://localhost:9689/bank";
			final String USER="root";
			final String PASS="Krishna@9689";
			response.setContentType("text/html");
			
			 PrintWriter out= response.getWriter();
			
			System.out.println("Hi");
			Class.forName(JDBC_DRIVER);
			Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
			
			int acno=Integer.parseInt(request.getParameter("acno"));
			String username=request.getParameter("uname");
			String password=request.getParameter("password");
			int amt=Integer.parseInt(request.getParameter("amt"));
		
			
			System.out.println("select * from details where Ac="+acno+" and " +"username='"+username+"'");
			Statement st1=con.createStatement();
			ResultSet rs1=st1.executeQuery("select * from details where username='"+username+"' and password='"+password+"' and Ac="+acno);
			System.out.println("select * from details where username='"+username+"' and password='"+password+"' and Ac="+acno);
			if(rs1.next())
			{

				Statement st2=con.createStatement();
				ResultSet rs2=st2.executeQuery("select * from daccount where Ac="+acno);
				System.out.println("select * from daccount where Ac="+acno);
				if(rs2.next())
				{
				  int a=rs2.getInt(2);
				  am=a-amt;
				  
				  if(am>=500)
				  {
				    try{

					     st2.executeUpdate("update daccount set amt="+am+" where Ac="+acno);
					     System.out.println("values updated");
            		   }catch(Exception e){ System.out.println(e);}

			    out.println("<HTML><CENTER><BODY><BR><BR><H2> Balance In Your Account IS : "+am+"</H2></BODY></CENTER</HTML>");
		            
				  }
				  else
					  out.println("<HTML><CENTER><BODY><BR><BR><H2> You can't withdraw because bal<500</H2></BODY></CENTER</HTML>");
		            
				}	
			}
			else
			{
				 out.println("<HTML><CENTER><BODY><BR><BR><H2> The Account Number Doesn't exist...</H2></BODY></CENTER</HTML>");

			}

		}
		catch(Exception e){System.out.println(e);}


	}

}
