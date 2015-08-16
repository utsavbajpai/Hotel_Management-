
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Utsav
 */
public class BookingEntry {
   Connection conn = null;
   private String magician;
   private String customer;
   private String holiday;
    private final Timestamp time;
   
   
   
   
  
   
   public BookingEntry(String magician, String customer, String holiday,Timestamp time)
   {
       this.magician= magician;
       this.customer = customer;
       this.holiday = holiday;
       this.time = time;
   }
   
   
   public String getMagician()
   {
       return magician;
   }
   
    public String getCustomer()
    {
        return customer;
    }
    
    public String getHoliday()
    {
        return holiday;
    }
    
    
    public Timestamp getTime()
    {
        return time;
    }
   
    
}
