
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
public class Bookings {
    Connection conn = null;
     
    
    public Bookings()
    {
        conn = DBConnection.getConnection();
    }
    public void addBooking(String Customer, String Magician, String Holiday,Timestamp time)
   {
       try
       {
          
          
           PreparedStatement insertIntoBook = conn.prepareStatement("INSERT INTO BOOKINGS(HOLIDAY,MAGICIAN,CUSTOMER,TIME) VALUES(?,?,?,?) ");
           insertIntoBook.setString(1, Holiday);
           insertIntoBook.setString(2, Magician);
           insertIntoBook.setString(3, Customer);
           insertIntoBook.setTimestamp(4, time);
           insertIntoBook.executeUpdate();
           
       }
       catch(SQLException sqlException)
       {
           sqlException.printStackTrace();
       }
   }
    
    
   public List<BookingEntry> showMagician(String holiday)
   {
            List<BookingEntry> BookingList = new ArrayList<BookingEntry>(); 
            
       try
       {
           
            PreparedStatement status = conn.prepareStatement("SELECT * FROM BOOKINGS WHERE HOLIDAY = ? ");
            status.setString(1,holiday);
             ResultSet result = status.executeQuery();
             while(result.next())
             {
                BookingList.add(new BookingEntry(result.getString("MAGICIAN"),result.getString("CUSTOMER"),result.getString("HOLIDAY"),result.getTimestamp("TIME")));
             }            
       }
       catch(SQLException sqlException)
       {
           sqlException.printStackTrace();
       }
       return BookingList;
       
   }
   
   
   public List<BookingEntry> showHoliday(String magician)
   {
       List<BookingEntry> BookingList = new ArrayList<BookingEntry>(); 
       try
       {
            PreparedStatement status = conn.prepareStatement("SELECT * FROM BOOKINGS WHERE MAGICIAN = ? ");
            status.setString(1, magician);
            ResultSet result = status.executeQuery();
            while(result.next())
            {
                BookingList.add(new BookingEntry(result.getString("MAGICIAN"),result.getString("CUSTOMER"),result.getString("HOLIDAY"),result.getTimestamp("TIME")));
            }
           
       }
       catch(SQLException sqlException)
       {
           sqlException.printStackTrace();
       }
       
       return BookingList;
   }
   
   public String getFreeMagician(String Holiday)
   {
           try
         {
                PreparedStatement freeMagician = conn.prepareStatement("SELECT MAGICIANNAMES FROM MAGICIANS WHERE MAGICIANNAMES NOT IN (SELECT MAGICIAN FROM BOOKINGS WHERE HOLIDAY = ? ) ");
                freeMagician.setString(1, Holiday);
                ResultSet magic = freeMagician.executeQuery();
                
                if(magic.next())
                {
                    return magic.getString(1);
                }
                else
                {
                    return null;
                }
         }
           
           
        catch(SQLException sqlException)
    {
       sqlException.printStackTrace();
       return null;
   }
    
}
   
   public String cancelFromBookings(String customer, String holiday)
   {
       Bookings book = new Bookings();
       Waitlist wait = new Waitlist();
       String c = "";
       List<BookingEntry> Entries = book.showMagician(holiday);
       try
       {
           PreparedStatement getMagician = conn.prepareStatement("SELECT MAGICIAN FROM BOOKINGS WHERE CUSTOMER = ? AND HOLIDAY = ?");
           getMagician.setString(1, customer);
           getMagician.setString(2, holiday);
           ResultSet magician = getMagician.executeQuery();
           PreparedStatement cancelBooking = conn.prepareStatement("DELETE FROM BOOKINGS WHERE CUSTOMER = ? AND HOLIDAY = ?");
           cancelBooking.setString(1, customer);
           cancelBooking.setString(2, holiday);
           cancelBooking.executeUpdate();
           
           
           
         
           PreparedStatement checkWaitlist = conn.prepareStatement("SELECT TIME,CUSTOMER FROM WAITLIST WHERE HOLIDAY = ? ORDER BY TIME");
           checkWaitlist.setString(1, holiday);
           
           ResultSet waitlistResult = checkWaitlist.executeQuery();
           ResultSetMetaData waitlistData = waitlistResult.getMetaData();
           
           magician.next();
           while(waitlistResult.next())
           {
               book.addBooking(waitlistResult.getString("CUSTOMER"), magician.getString("MAGICIAN") , holiday , waitlistResult.getTimestamp("TIME"));
               c = " Congratulations," +waitlistResult.getString("CUSTOMER")+ " you have been booked with " + magician.getString("MAGICIAN")+ "for" +holiday+ "!";
               wait.deleteFromWaitlist(waitlistResult.getString("CUSTOMER"), holiday);
           }
       }
       catch(SQLException sqlException)
       {
           sqlException.printStackTrace();
       }
       
       
       return c;
   }
}
