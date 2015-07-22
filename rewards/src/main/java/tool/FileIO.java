/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author root
 */
public class FileIO {
    
     public static String readFile(String filename) throws FileNotFoundException, IOException{

                FileReader fr = new FileReader(filename);

	        BufferedReader br = new BufferedReader(fr);

                String output = "";
                
	        while (br.ready()) {

	            //System.out.println(br.readLine());
                    
                    //if(br.readLine()!=null){
                        output+=br.readLine();
                    //}
                    
	        }

	        fr.close();
               
                return output;
    
    }
    
    
        public static void delPID(String filename, String input) throws IOException{
        
        //"/home/syscom/test.txt"
        
         String output = readFile(filename);
        System.out.println("output=>"+output);
        String result="";
        String[] a = output.split(":");
        for(int i=0;i<a.length;i++){
        
            if(a[i].equals(input)){
            
                System.out.println("match:"+a[i]);
            }else{
            
                result+=":"+a[i];
            }
        }
        
        System.out.println("result:"+result);
        FileWriter fw = new FileWriter(filename);
        
        
	        fw.write(result);

	        fw.flush();

	        fw.close();
    
    
    }
    
}
