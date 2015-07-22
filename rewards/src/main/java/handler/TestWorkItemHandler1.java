/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import tool.getDate;
 
import ws_client.Tester;

/**
 *
 * @author root
 */
public class TestWorkItemHandler1 implements WorkItemHandler {

    private WorkItem workItem;

    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

        System.out.println("id:" + workItem.getId() + " executeWorkItem OK");
        this.workItem = workItem;

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            String processInstanceId = (String) workItem.getParameter("processInstanceId");
            System.out.println("input workItem processInstanceId=>" + processInstanceId);
            System.out.println("processinstanceid=>" + workItem.getProcessInstanceId());
            long processinstanceid = workItem.getProcessInstanceId();
            System.out.println("taskid=>" + workItem.getId());
            String Taskname = (String) workItem.getParameter("name");
            System.out.println("input workItem name=>" + workItem.getParameter("name"));
            System.out.println("input workItem stopProcess=>" + workItem.getParameter("stopProcess"));
            String stopProcess = (String) workItem.getParameter("stopProcess");
            String Time = tool.getDate.getDate();
            String restart = (String) workItem.getParameter("restart");
            String type = (String) workItem.getParameter("type");
            System.out.println("in handler type :" + type);
            String timestamp = (String) workItem.getParameter("timestamp");
            System.out.println("in workhandler timestamp:"+timestamp);

            System.out.println("in handler tmp.Tmp.getProcessInstanceId()=>" + tmp.Tmp.getProcessInstanceId());

            while (!tmp.Tmp.getTaskDataList().containsKey(Long.valueOf(processInstanceId))) {
                Thread.sleep(1000);
            }

            model.TaskData taskdata = tmp.Tmp.getTaskDataList().get(Long.valueOf(processInstanceId));
            taskdata.setTaskName(Taskname);
            taskdata.setRequestTime(Time);
            if (taskdata != null) {
                System.out.println("in handler taskdata.getType()" + taskdata.getType());

                //System.out.println("in eventlistener progress size:" + tmp.Tmp.getProgressOfProcess().size());
                // System.out.println("in eventlistener progress content:" + tmp.Tmp.getProgressOfProcess());
                System.out.println("in eventlistener progress name:" + Taskname);

                System.out.println("in eventlistener progress glbpid:" + Long.valueOf(processInstanceId));

              //tmp.Tmp.getProgressOfProcess().putIfAbsent(Long.valueOf((String)pnle.getKieRuntime().getGlobal("glbProcessInstanceId")), (String)pnle.getNodeInstance().getVariable("name"));
              /*
                 if (!tmp.Tmp.getProgressOfProcess().containsKey(Long.valueOf(processInstanceId))) {

                 System.out.println("in eventlistener progress putIfAbsent");
                 tmp.Tmp.getProgressOfProcess().putIfAbsent(Long.valueOf(processInstanceId), Taskname);

                 System.out.println("now progress : " + tmp.Tmp.getProgressOfProcess().get(Long.valueOf(processInstanceId)));
                 System.out.println("now progress name:" + Taskname);
                 } else {
                 System.out.println("in eventlistener progress replace");

                 tmp.Tmp.getProgressOfProcess().replace(Long.valueOf(processInstanceId), Taskname);

                 }
                 */
                tmp.Tmp.addProgressOfProcess(processInstanceId, Taskname,timestamp);
                /*
                try {

                    DB.DBConnectionDemo.excecuteSql("systmpdata", "update `taskdatalist` set `taskname`='" + taskData.getTaskName() + "' where `processinstanceid`='" + taskData.getProcessInstanceId() + "'");

                } catch (SQLException ex) {
                    Logger.getLogger(BpmResource.class.getName()).log(Level.SEVERE, null, ex);
                }*/

                testwsclient1(taskdata, type);
            } else if (taskdata == null) {
                System.out.println("in handler taskdata==null!");
            }

            if (tmp.Tmp.getTaskDataList().containsKey(Long.valueOf(processInstanceId))) {
                tmp.Tmp.getTaskDataList().get(Long.valueOf(processInstanceId)).setResult(null);
            } else {

                System.out.println("tmp.Tmp.getTaskDataList() do not contains this process!");
            }

            while (!tmp.Tmp.getTaskDataList().containsKey((Long.valueOf(processInstanceId)))) {
                Thread.sleep(1000);
            }

            while (tmp.Tmp.getTaskDataList().get(Long.valueOf(processInstanceId)).getResult() == null) {
                Thread.sleep(1000);
            }

            String responseTime = getDate.getDate();

            System.out.println("statc handler result=>" + tmp.Tmp.result);

            System.out.println("Execution is called to start a generic or user task."
                    + "Since this implementation calls manager.completeWorkItem(...), "
                    + "it will consider the step completed after invoking it and will "
                    + "continue with the process execution");

            params.put("restart", "False");
            params.put("name", Taskname);

            params.put("result", tmp.Tmp.getTaskDataList().get(Long.valueOf(processInstanceId)).getResult());
            manager.completeWorkItem(workItem.getId(), params);

        } catch (InterruptedException ex) {
            Logger.getLogger(TestWorkItemHandler1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public WorkItem getWorkItem() {
        //System.out.println("id:"+workItem.getId()+"getWorkItem OK");
        WorkItem result = workItem;
        workItem = null;
        return result;
    }

    public void abortWorkItem(WorkItem wi, WorkItemManager wim) {

        System.out.println("handler abortWorkItem~");

    }

    public void testwsclient1(model.TaskData taskdata, String type) {

        String[] a = {"", ""};
        String[] b = taskdata.getProvisionData();

        System.out.println("taskdata.getTaskName()=>" + taskdata.getTaskName());

        for(String ele:taskdata.getProvisionData()){
        
        System.out.println("testwsclient1 ele:"+ele);
        }
        
        
        int index = Integer.valueOf(taskdata.getTaskName()) - 1;

        String action = b[index].split(",")[0];
        System.out.println("handler url param=>" + action);
        Tester tt = new Tester();

        //String url = "http://192.168.2.157:8080/WSTest/webresources/hello/"+action;
        //String url = "http://192.168.2.203:8080/WSTest/webresources/hello/" + action;
        String url = "http://"+Tester.ccsIP+":"+Tester.ccsPort+"/"+Tester.ccsTag+"/" + action;

        tt.testWSClient1(url, taskdata);
    }
}
