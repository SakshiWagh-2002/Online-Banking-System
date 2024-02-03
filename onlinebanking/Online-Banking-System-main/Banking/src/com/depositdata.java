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
 * Servlet implementation class depositdata
 */
@WebServlet("/depositdata")
public class depositdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public depositdata() {
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
		int acamt=0;
		
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
						 

				Statement st1=con.createStatement();
				int acno=Integer.valueOf(request.getParameter("acno"));
				int amt=Integer.valueOf(request.getParameter("amt"));
				System.out.println(acno);
				String username=request.getParameter("uname");
				String password=request.getParameter("pwd");
				 //aamt=Integer.valueOf(request.getParameter("amt"));
				//aamt=20000;
				System.out.println(amt);
			   //amt=Integer.parseInt(request.getParameter("amt"));
				ResultSet rs=st1.executeQuery("select * from details where Ac='"+acno+"'and username='"+username+"'and password='"+password+"'");
				if(rs.next())
				{
					Statement st2=con.createStatement();
					ResultSet rs2=st2.executeQuery("select * from daccount where Ac="+acno);
					if(rs2.next())
					{
						try
						{
						
							int b=rs2.getInt(2);
							acamt=b+amt;
							System.out.println("update daccount set amt="+acamt+" where Ac="+acno);
							st2.executeUpdate("update daccount set amt="+acamt+" where Ac="+acno);
							System.out.println("updated");
						
						  
						    out.println("<HTML><CENTER><BODY><BR><BR><H2> Balance In Your Account IS : "+acamt+"</H2></BODY></CENTER</HTML>");
                        }
						catch(Exception e){ System.out.println(e);}
					}
				}
				else
				{
					 out.println("<HTML><CENTER><BODY><BR><BR><H2> The Account Number DOes not exist...</H2></BODY></CENTER</HTML>");

				}
			
		}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}

}
