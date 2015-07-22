/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

 

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@XmlRootElement
public class AssignBPMData {

    String type;
    String[] provisiovData;
    boolean needCallBack;
    

    public AssignBPMData() {
    }

    public AssignBPMData(String type, String[] provisiovData, boolean needCallBack) {
        this.type = type;
        this.provisiovData = provisiovData;
        this.needCallBack = needCallBack;
    }
            
    //
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getProvisiovData() {
        return provisiovData;
    }

    public void setProvisiovData(String[] provisiovData) {
        this.provisiovData = provisiovData;
    }

    public boolean isNeedCallBack() {
        return needCallBack;
    }

    public void setNeedCallBack(boolean needCallBack) {
        this.needCallBack = needCallBack;
    }
    public String print(){
        String pD = "";
         int index = 0;
         
         while(index < provisiovData.length){
           pD = pD+","+provisiovData[index];
             
           index++;
         }
     
       return   type+":"+pD+":"+needCallBack;
   
    }

}
