
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Utsav
 */
public class Waitlist {
    Connection conn = null;
    
    public Waitlist()
    {
        conn = DBConnection.getConnection();
    }
    
    public void addToWaitlist(String Customer,String Holiday,Timestamp time)
    {
        try
        {        
           PreparedStatement waitlistStatement = conn.prepareStatement("INSERT INTO WAITLIST(CUSTOMER,HOLIDAY,TIME) VALUES(?, ?, ?) ");
            waitlistStatement.setString(1, Customer);
            waitlistStatement.setString(2, Holiday);
            waitlistStatement.setTimestamp(3, time);
            waitlistStatement.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
               
        
    }
    
    
    public List<WaitlistEntry> getWaitlistEntry()
    {
        List<WaitlistEntry> WaitList = new ArrayList<WaitlistEntry>();
        
        try
        {
            PreparedStatement waitliststatus = conn.prepareStatement("SELECT * FROM WAITLIST ORDER BY HOLIDAY, TIME");
            ResultSet result = waitliststatus.executeQuery();
            while(result.next())
            {
                WaitList.add(new WaitlistEntry(result.getString("CUSTOMER"),result.getString("HOLIDAY"),result.getTimestamp("TIME")));
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return WaitList;
    }
    
    
    public void deleteFromWaitlist(String customer, String holiday)
    {
        try
        {
            PreparedStatement deleteWaitlistEntry = conn.prepareStatement("DELETE FROM WAITLIST WHERE CUSTOMER = ? AND HOLIDAY = ? ");
            deleteWaitlistEntry.setString(1, customer);
            deleteWaitlistEntry.setString(2, holiday);
            deleteWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    
   
}
