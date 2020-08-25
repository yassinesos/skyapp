package org.sid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.sid.Skyline.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

public class ManipDuDB {
	
	   
	private HikariDataSource ds;	
	
	
    public ManipDuDB(HikariDataSource ds) {
		 this.ds = ds;
    }
    
	public Connection getCon() {
		Connection cn = null;
		try {
			cn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cn;
	}
	
	public <T> List<T> findALL(){
		 
	      Connection conn = getCon();
	      Statement stmt = null;
	      ArrayList<Data<String,Double>> arr1 = null;
	      ArrayList<Data<Double,Double>> arr2 = null;
	      
	      try{
			 String catalogName = conn.getCatalog();
			 System.out.println("catalogue "+ catalogName);
	         stmt = conn.createStatement();

			 String sql = (new StringBuilder()).append("select * from").append(" ").append("data4d").toString();
			 System.out.println(sql);

	         ResultSet rs = stmt.executeQuery(sql);
			 System.out.println(sql);
			 
			 ResultSetMetaData rsmd= rs.getMetaData();
			 System.out.println("type "+rsmd.getColumnType(2));
		      if (rsmd.getColumnType(2) == Types.VARCHAR) {
		    	 arr1 = new ArrayList<>();
		    	 while(rs.next()){
			            //Retrieve by column name
			            String label = rs.getString(2);
			            int NumOfCol = rsmd.getColumnCount();
			            
			            ArrayList<Double> arr3 = new ArrayList<>();
			            for(int i = 3; i <= NumOfCol; i++) {
			            	arr3.add(rs.getDouble(i));
			            }
			            arr1.add(new Data<>(label,arr3)); 
			            arr3.clear();
			         }
			         rs.close();
			         return (List<T>) arr1;
		      } else {
			     arr2 = new ArrayList<>();
			     while(rs.next()){
			            //Retrieve by column name
			            Double firstcol = rs.getDouble(2);
			            int NumOfCol = rsmd.getColumnCount();
			            
			            ArrayList<Double> arr3 = new ArrayList<>();
			            for(int i = 3; i <= NumOfCol; i++) {
			            	arr3.add(rs.getDouble(i));
			            }
			            arr2.add(new Data<>(firstcol,arr3)); 
			            arr3.clear();
			         }
			         rs.close();
			         return (List<T>) arr2;
		      }
	        
	      }catch(SQLException se){
	         //Handle errors for JDBC
	         se.printStackTrace();
	      }catch(Exception e){
	         //Handle errors for Class.forName
	         e.printStackTrace();
	      }finally{
	         //finally block used to close resources
	         try{
	            if(stmt!=null)
	               conn.close();
	         }catch(SQLException se){
	         }// do nothing
	         try{
	            if(conn!=null)
	               conn.close();
	         }catch(SQLException se){
	            se.printStackTrace();
	         }//end finally try
	      }//end try
	  	return null;
	}
	
	
}
