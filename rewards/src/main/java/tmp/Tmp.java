/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp;

import static DB.DBConnectionDemo.excecuteSql;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TaskData;

/**
 *
 * @author User
 */
public class Tmp {

    public static String result;
    public static String stopProcess;
    public static String processInstanceId;

    public static ConcurrentHashMap<String, String> starttimeList = new ConcurrentHashMap<String, String>(); //record running task of running process.  

    
    public static Set<Long> processList = new ConcurrentSkipListSet<Long>(); //record parent process id. 
    public static ConcurrentHashMap<Long, Set<Long>> subProcessLists = new ConcurrentHashMap<Long, Set<Long>>(); //record running or finished child processes. 

    public static ConcurrentHashMap<Long, String> progressOfProcess = new ConcurrentHashMap<Long, String>(); //record running process of each parent process.

    public static Set<Long> stopProcessList = new ConcurrentSkipListSet<Long>();//record the processes that user want to stop but it is still running.   

    public static ConcurrentHashMap<Long, TaskData> taskDataList = new ConcurrentHashMap<Long, TaskData>(); //record running task of running process.  

    public static Set<String> downPortSet = new ConcurrentSkipListSet<String>();

    public static Set<String> deployVersions = new ConcurrentSkipListSet<String>();

    
    public static void setStartProcesstime(String startProcesstime) {
    
    
    }
    
    
    public static void addusertaskdata(String actorid, String message, String pid, String starttime,String taskid, String requesttime)   {
        //   DB.DBConnectionDemo.excecutejpaSql("bpmdata", "insert into `usertaskdata`(`actorid`,`message`,`pid`,`starttime`,`taskid`,`status`) values ('" + actorid + "','"+message+"','"+pid+"','"+starttime+"','"+taskid+"','Ready');");

     
     }
    
    public static void addstarttimeList(String pid, String timestamp) {

        String t = starttimeList.putIfAbsent(pid, timestamp);
        if (t != null) {
            t = timestamp;

        }

        try {
            DB.DBConnectionDemo.addsystmpvar("starttimelist", "timestamp", pid, timestamp);
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        public static void removestarttimeList(String timestamp,String pid) {//do not need to delete~ 2015/5/25

        System.out.println("in tmp removestarttimeList");
        starttimeList.remove(pid);

        try {
            DB.DBConnectionDemo.excecuteSql("bpmdata", "delete from  `processids` where `starttime`='"+timestamp+"';");
            
            DB.DBConnectionDemo.excecuteSql("systmpdata", "delete from `starttimelist` where `pid`='" + pid + "'");
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    public static Set<String> getDeployVersions() {
        return deployVersions;
    }

    public static void setDeployVersions(Set<String> deployVersions) {
        Tmp.deployVersions = deployVersions;
    }

    public static void addDeployVersions(String dV) {
        System.out.println("tmp add:" + dV);
        deployVersions.add(dV);
        if (getDeployVersions().contains(dV)) {

            System.out.println("deploy versions contain~:" + getDeployVersions().toString());
            setDeployVersions(getDeployVersions());
            

            try {
                DB.DBConnectionDemo.editRuntimeval("deploy_version", dV, "add");
            } catch (SQLException ex) {
                Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (!deployVersions.contains(dV)) {

            System.out.println("deploy versions no contain~");

        }

        System.out.println("in add deploy versions~!");
    }

    public static String getStopProcess() {

        return stopProcess;
    }

    public static void setStopProcess(String stopProcess) {
        Tmp.stopProcess = stopProcess;
    }

    public static String getProcessInstanceId() {
        return processInstanceId;
    }

    public static void setProcessInstanceId(String processInstanceId) {
        Tmp.processInstanceId = processInstanceId;
    }

    public static Set<Long> getProcessList() {
        return processList;
    }

    public static void removeProcessList(Long pid) {
        processList.remove(pid);
    }

    public static void setProcessList(Set<Long> processList) {
        Tmp.processList = processList;
    }

    public static ConcurrentHashMap<Long, Set<Long>> getSubProcessList() {
        return subProcessLists;
    }

    public static void removeSubProcessList(Long pid) {
        subProcessLists.remove(pid);
    }

    public static void addSubProcessList(Long key, Long pid) {

        Set<Long> subProcessList = tmp.Tmp.getSubProcessList().get(key);

        if (subProcessList == null) {

            subProcessList = new HashSet<Long>();
            tmp.Tmp.getSubProcessList().putIfAbsent(key, subProcessList);
        }

        tmp.Tmp.getSubProcessList().get(key).add(pid);

        System.out.println("look subprocesslist:" + tmp.Tmp.getSubProcessList().toString());
        try {
            DB.DBConnectionDemo.addsystmpvar("subprocesslists", "child_pids", String.valueOf(key), String.valueOf(pid));
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void setSubProcessList(ConcurrentHashMap<Long, Set<Long>> subprocessLists) {
        Tmp.subProcessLists = subprocessLists;
    }

    public static Set<Long> getStopProcessList() {
        return stopProcessList;
    }

    public static void addStopProcessList(Long pid) {
        stopProcessList.add(pid);
        try {
            DB.DBConnectionDemo.editRuntimeval("stopprocesslist", String.valueOf(pid), "add");
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void removeStopProcessList(Long pid) {
        stopProcessList.remove(pid);
        try {
            DB.DBConnectionDemo.editRuntimeval("stopprocesslist", String.valueOf(pid), "del");
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setStopProcessList(Set<Long> stopProcessList) {
        Tmp.stopProcessList = stopProcessList;
    }
    
       public static void addProgressOfProcess(String pid, String taskname, String timestamp) {
//key:index class:pid,taskname
     /*   Long max = getmaxpopindex();

        System.out.println("tmp addProgressOfProcess:" + max);

        max = max + 1;

        tmp.Tmp.getProgressOfProcess().putIfAbsent(max, new pop(pid, taskname));
       */ 
        
        
        
         if (!tmp.Tmp.getProgressOfProcess().containsKey(Long.valueOf(pid))) {

         System.out.println("in eventlistener progress putIfAbsent");
         tmp.Tmp.getProgressOfProcess().putIfAbsent(Long.valueOf(pid), taskname);

         System.out.println("now progress : " + tmp.Tmp.getProgressOfProcess().get(Long.valueOf(pid)));
         System.out.println("now progress name:" + taskname);
         } else {
         System.out.println("in eventlistener progress replace");

         tmp.Tmp.getProgressOfProcess().replace(Long.valueOf(pid), taskname);

         }

        try {
            DB.DBConnectionDemo.addsystmpvar("progressofprocess", "progress", pid, taskname + " " + timestamp);

            //  DB.DBConnectionDemo.excecuteSql("systmpdata", "insert into `progressofprocess`(`pid`,`progress`,`startTime`) values('"+tmp.Tmp.getProcessInstanceId()+"','"+startTime+"');");
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }

        //return max;
    }

    public static void addProgressOfProcess(String pid, String taskname) {

        if (!tmp.Tmp.getProgressOfProcess().containsKey(Long.valueOf(pid))) {

            System.out.println("in eventlistener progress putIfAbsent");
            tmp.Tmp.getProgressOfProcess().putIfAbsent(Long.valueOf(pid), taskname);

            System.out.println("now progress : " + tmp.Tmp.getProgressOfProcess().get(Long.valueOf(pid)));
            System.out.println("now progress name:" + taskname);
        } else {
            System.out.println("in eventlistener progress replace");

            tmp.Tmp.getProgressOfProcess().replace(Long.valueOf(pid), taskname);

        }

        try {
            DB.DBConnectionDemo.addsystmpvar("progressofprocess", "progress", pid, taskname);
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ConcurrentHashMap<Long, String> getProgressOfProcess() {
        return progressOfProcess;
    }

    public static void setProgressOfProcess(ConcurrentHashMap<Long, String> progressOfProcess) {
        Tmp.progressOfProcess = progressOfProcess;
    }

    public static ConcurrentHashMap<Long, TaskData> getTaskDataList() {
        return taskDataList;
    }

    public static String getTaskName(Long pid) {

        return taskDataList.get(pid).getTaskName();
    }

    public static String[] getProvisionData(Long pid) {

        return taskDataList.get(pid).getProvisionData();
    }

    public static String getType(Long pid) {

        return taskDataList.get(pid).getType();
    }

    public static String getRequestTime(Long pid) {

        return taskDataList.get(pid).getRequestTime();
    }

    public static String getResponseTime(Long pid) {

        return taskDataList.get(pid).getResponseTime();
    }

    public static String getResult(Long pid) {

        return taskDataList.get(pid).getResult();
    }

    public static String getErrorCode(Long pid) {

        return taskDataList.get(pid).getErrorCode();
    }

    public static boolean isNeedCallback(Long pid) {

        return taskDataList.get(pid).isNeedCallback();
    }

    public static void setResult(Long pid, String result) {

        taskDataList.get(pid).setResult(result);

    }

    public static void setTaskDataList(ConcurrentHashMap<Long, TaskData> taskDataList) {
        Tmp.taskDataList = taskDataList;
    }

    public static Set<String> getDownPortSet() {
        return downPortSet;
    }

    public static void addDownPortSet(String port) {
        downPortSet.add(port);
        try {
            DB.DBConnectionDemo.editRuntimeval("downportset", port, "add");
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delDownPortSet(String port) {
        downPortSet.remove(port);
        try {
            DB.DBConnectionDemo.editRuntimeval("downportset", port, "del");
        } catch (SQLException ex) {
            Logger.getLogger(Tmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setDownPortSet(Set<String> downPortSet) {
        Tmp.downPortSet = downPortSet;
    }

}
