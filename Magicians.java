
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Utsav
 */
public class Magicians {
    Connection conn = null;
    private final String SELECT_QUERY = "select MAGICIANNAMES from MAGICIANS";
    private static List<String> MagicianList = new ArrayList<String>();
    
    
    public Magicians()
    {
    try
   {
       conn = DBConnection.getConnection();
    Statement statement = conn.createStatement();
    ResultSet resultset = statement.executeQuery(SELECT_QUERY);
    
    while(resultset.next())
    {
        MagicianList.add(resultset.getString(1));
    }
   }
   catch(SQLException sqlException)
   {
       sqlException.printStackTrace();
   }
}
    
    public static List<String> getMagicianList()
    {
        return MagicianList;
    }
    
    
    public void addMagician(String magician)
    {
        Waitlist tempWait = new Waitlist();
        Bookings book = new Bookings();
        List <WaitlistEntry> tempWaitList = tempWait.getWaitlistEntry();
        try
        {
        PreparedStatement insertIntoMagician = conn.prepareStatement("INSERT INTO MAGICIANS(MAGICIANNAMES) VALUES(?)");
        insertIntoMagician.setString(1, magician);
        insertIntoMagician.executeUpdate();
        
        if(!tempWaitList.isEmpty())
        {
            for(WaitlistEntry entry:tempWaitList)
            {
                String s = book.getFreeMagician((entry.getHoliday()));
                if(s != null)
            {
                book.addBooking(entry.getCustomer(), s, entry.getHoliday(),entry.getTime());     
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Congratultions, " + entry.getCustomer() + ", you have been booked with " + s + " for " + entry.getHoliday()+ "!");
                tempWait.deleteFromWaitlist(entry.getCustomer(),entry.getHoliday());
            }
               
            }
                
        }
        
                
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    public void dropMagician(String magician)
    {
        Bookings book = new Bookings();
        Waitlist waitlist = new Waitlist();
        List<BookingEntry> BookingEntries = book.showHoliday(magician);
        Component frame = null;
        
        try
        {
            PreparedStatement deletefromMagicians = conn.prepareStatement("DELETE FROM MAGICIANS WHERE MAGICIANNAMES = ? ");
            deletefromMagicians.setString(1, magician);
            deletefromMagicians.executeUpdate();
            
            for(BookingEntry entry:BookingEntries)
            {
               String s = book.getFreeMagician(entry.getHoliday());
                if(s != null)
                {
                    book.addBooking(entry.getCustomer(),s, entry.getHoliday(), entry.getTime());
                    JOptionPane.showMessageDialog(frame, "Congratultions, " + entry.getCustomer() + ", you have been booked with " + s + " for " + entry.getHoliday() + "!");
                }
                else
                {
                    waitlist.addToWaitlist(entry.getCustomer(), entry.getHoliday(), entry.getTime());
                    book.cancelFromBookings(entry.getCustomer(), entry.getHoliday());
                    JOptionPane.showMessageDialog(frame, "Sorry, " + entry.getCustomer() + " you have been put in the waitlist");
                }
            }       
          
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

}