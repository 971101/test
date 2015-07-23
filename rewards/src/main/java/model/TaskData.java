/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

 

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class TaskData {
    
    String processInstanceId;
    String TaskName;
    String requestTime;
    String responseTime;
    String result; //Success or Fail
    String errorCode;
    String type ;
    String[] provisionData;
    int index;
    boolean needCallback;

    public TaskData(String processInstanceId, String TaskName, String requestTime, String responseTime, String result, String errorCode, String type, String[] provisionData, boolean needCallback) {
        this.processInstanceId = processInstanceId;
        this.TaskName = TaskName;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
        this.result = result;
        this.errorCode = errorCode;
        this.type = type;
        this.provisionData = provisionData;
        this.needCallback = needCallback;
    }

    
    
    public TaskData() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    
    
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String TaskName) {
        this.TaskName = TaskName;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getProvisionData() {
        return provisionData;
    }

    public void setProvisionData(String[] provisionData) {
        this.provisionData = provisionData;
    }

    public boolean isNeedCallback() {
        return needCallback;
    }

    public void setNeedCallback(boolean needCallback) {
        this.needCallback = needCallback;
    }
    
    
     public String print(){
       /*
            String processInstanceId;
            String TaskName;
            String requestTime;
            String responseTime;
            String result;
            String errorCode;
            String type ;
            String provisiovData;
            boolean needCallback;
         */
         String pD = "";
         int Index = 0;
         
         while(Index < provisionData.length){
           pD = pD+","+provisionData[Index];
             
           Index++;
         }
         
        return processInstanceId + ":" + TaskName+":" + requestTime+":"+responseTime+":"+result+":"+errorCode+":"+type+":"+pD+":"+needCallback;
    }
 
}
