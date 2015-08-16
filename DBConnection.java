/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Utsav
 */
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;


public class DBConnection {
       private final static String DATABASE_URL = "jdbc:derby://localhost:1527/Holidays";
       private static Connection conn;
       
        
        public DBConnection()
        {
             
            
        }
        
        
       public static Connection getConnection()
        {
            try{
                     conn =  DriverManager.getConnection(DATABASE_URL,"falling3","falling3");
                     
            }
              
            catch(SQLException sqlException)
                    {
                        sqlException.printStackTrace();
                    }          
            return conn;
        }
        
} 
                
               

