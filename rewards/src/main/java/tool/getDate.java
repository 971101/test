/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tool;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author User
 */
public class getDate {
    
        public static String getDate(){
          
        Timestamp stamp = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String dateNow = dfDate.format(stamp);
        
        System.out.println("dateNow=>"+dateNow);
         
        return dateNow;
     
     }
    
}
