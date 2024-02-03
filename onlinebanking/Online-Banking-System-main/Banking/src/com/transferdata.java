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
 * Servlet implementation class transferdata
 */
@WebServlet("/transferdata")
public class transferdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transferdata() {
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
				String password=request.getParameter("pwd");
				int tacno=Integer.parseInt(request.getParameter("tacno"));
				int amt=Integer.parseInt(request.getParameter("amt"));

			 
			 
			 
				Statement st1=con.createStatement();
				Statement st2=con.createStatement();
				ResultSet rs=st1.executeQuery("select * from details where Ac="+acno+" and username='"+username+"'and password='"+password+"'");
				ResultSet rs3=st2.executeQuery("select * from details where Ac="+tacno);
				
				if(rs.next())
				{
				 if(rs3.next())
				 {
				  System.out.println("transfer is possible");
				  ResultSet rs1=st1.executeQuery("select amt from daccount where Ac="+acno);
				
				  if(rs1.next())
				  {
					  int k=rs1.getInt(1);
					  System.out.println(k);
					  k=k-amt;
//					  System.out.println(k);

					  if(k>=500)
					  {
				        ResultSet rs2=st1.executeQuery("select amt from daccount where Ac="+tacno);
						
						if(rs2.next())
						{
						  int x=rs2.getInt(1)+amt;
						  System.out.println(x); 
						  st1.executeUpdate("update daccount set amt="+k+" where Ac="+acno);
						  st1.executeUpdate("update daccount set amt="+x+" where Ac="+tacno);
//						  System.out.println(y);
//						  System.out.println(z);
						  String str="   "+k;
						  out.println("<HTML><CENTER><BODY><BR><BR><H2> Balance In Your Account IS : "+str+"</H2></BODY></CENTER</HTML>");
				          
	                    }
					  }
					  else
					  {
						  out.println("<HTML><CENTER><BODY><BR><BR><H2> You can't transfer because bal<500</H2></BODY></CENTER</HTML>");
				               
					  }
						  
	              } 
	             }
				  else
				  {
					  out.println("<HTML><CENTER><BODY><BR><BR><H2> Transfer Is Not Possible...</H2></BODY></CENTER</HTML>");
			               
				  }
	           }
				  	
		      }catch(Exception e){System.out.println(e);} 

		
	}

}
