package de.rtcustomz.getraenkeautomat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database 
{
	private Connection conn=null;
	
	public Database() {
		super();
		connect();
	}

	public void connect()
	{   
		try {
	    	Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/nfc?user=root&password=root");

	    } catch (SQLException ex) {
	        // handle any errors
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    } catch (ClassNotFoundException e) {
	    	System.out.println("No JDBC Driver Class Found: "+e.getMessage());
		}
	}
	
	public ArrayList<String> testQuery()
	{
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> rows = new ArrayList<String>();

		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM users;");
		    
		    rows.add("id, first_name, last_name, nickname");
		    
		    while(rs.next())
		    {
		    	int id = rs.getInt(1);
		    	String first_name = rs.getString(2);
		    	String last_name = rs.getString(3);
		    	String nickname = rs.getString(4);
		    	
		    	rows.add(id+", "+first_name+", "+last_name+", "+nickname);
		    }
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
		return rows;
	}
}
