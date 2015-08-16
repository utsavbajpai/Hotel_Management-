
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
public class WaitlistEntry {
    private final String customer;
    private final String holiday;
    private final Timestamp time;
    
    public WaitlistEntry(String customer, String holiday,Timestamp time)
    {
        this.customer = customer;
        this.holiday = holiday;
        this.time = time;
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
