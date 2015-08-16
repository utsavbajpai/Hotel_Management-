

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
public class Holiday {
    Connection conn;
    private final String Select_query = "select HOLIDAYNAME from HOLIDAY";
    private static List<String> HolidayList = new ArrayList<String>();
    
    public Holiday()
    {
    
   
   try
   {
       conn = DBConnection.getConnection();
      Statement statement = conn.createStatement();
       ResultSet resultset = statement.executeQuery(Select_query);
  
    
    while(resultset.next())
    {
        HolidayList.add(resultset.getString(1));
    }
   }
   catch(SQLException sqlException)
   {
       sqlException.printStackTrace();
   }
}
    
    public static List<String> getHolidayList()
    {
        return HolidayList;
    }
    
    public void addHoliday(String holiday)
    {
        try
        {
            PreparedStatement addtoHoliday = conn.prepareStatement("INSERT INTO HOLIDAY(HOLIDAYNAME)  VALUES(?)");
            addtoHoliday.setString(1, holiday);
            addtoHoliday.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
