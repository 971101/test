/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import model.TaskData;

public class DBConnectionDemo {

    public void closeConnection(Connection conn) throws SQLException {
        conn.close();

    }

    public static Connection getConnection(String database) {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + database;
        String user = "hca";
        String password = "b9629008";
        try {
            Class.forName(driver);
            Connection conn
                    = DriverManager.getConnection(url,
                            user, password);

            if (conn != null && !conn.isClosed()) {
                System.out.println("connection suuccess!");
                return conn;
                // conn.close();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("cannot find driver class!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    public static List excecutejpaSql(String database, String qry1){
    
      return null;
    }
    public static void printDB(ResultSet rs) throws SQLException {

        ResultSetMetaData rm = rs.getMetaData();

        int cnum = rm.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= cnum; i++) {
                System.out.print(rm.getColumnName(i) + ":" + rs.getObject(i) + " ");
            }
            System.out.println("");
        }

    }

    public static String getRuntimevar(ResultSet rs, String var_name) throws SQLException {

        String var_val = "";

        ResultSetMetaData rm = rs.getMetaData();

        int cnum = rm.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= cnum; i++) {
                System.out.print(rm.getColumnName(i) + ":" + rs.getObject(i) + " ");
                if (rm.getColumnName(i).equals("var_value")) {

                    System.out.println("var::" + rs.getString(i));
                    var_val = rs.getString(i);

                }
            }
            System.out.println("");
        }

        return var_val;

    }

    public static void editRuntimeval(String var_name, String single_value, String action) throws SQLException {

        System.out.println("in editRuntimeval");

        ResultSet rs = excecuteSql("systmpdata", "select * from runtime_tmp_var where var_name='" + var_name + "';");

        String var = getRuntimevar(rs, var_name);

        System.out.println("be var:" + var);

        if (action.equals("add")) {

            var += ":" + single_value;

        } else if (action.equals("del")) {

            System.out.println("contains:" + var.contains(single_value));
            if (var.contains(single_value)) {

                String[] vararr = var.split(":");
                var = "";

                for (String element : vararr) {

                    System.out.println(element);
                    if (element.equals(single_value)) {

                        //do nothing
                    } else {

                        var += ":" + element;

                    }

                }

            } else {
                System.out.println("no contains var:" + var);
            }

        }

        System.out.println("kk:" + var);

        excecuteSql("systmpdata", "update `runtime_tmp_var` set `var_value`='" + var + "' where `var_name`='" + var_name + "';");

    }

    public static void rs2ccmap(ResultSet rs, String map_name, Object map) throws SQLException {
        ConcurrentHashMap<Long, TaskData> map1 = null;

        if (map_name.equals("taskDataList")) {

            map1 = (ConcurrentHashMap<Long, TaskData>) map;
        }

        ResultSetMetaData rm = rs.getMetaData();

        int cnum = rm.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= cnum; i++) {
                System.out.print(i + ":" + rm.getColumnName(i) + ":" + rs.getObject(i) + " ");

            }

            String[] provisionData = rs.getString(9).split("-");

            for (String data : provisionData) {

                System.out.println(data);

            }

            TaskData taskdata = new TaskData(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), provisionData, Boolean.valueOf(rs.getString(11)));
            map1.putIfAbsent(Long.valueOf(rs.getString(2)), taskdata);

            System.out.println("");
        }

        System.out.println("map.size():" + map1.size());
        System.out.println("map.println():" + map1.get(Long.valueOf("123")).print());

    }

    public static void DB2localdata(String db, String qry, String map_name, Object localdata) throws SQLException {

        ResultSet rs = excecuteSql(db, qry);

        ConcurrentHashMap<Long, TaskData> taskDataList = null;
        Set<Long> processList = null;
        ConcurrentHashMap<Long, Set<Long>> subProcessLists = null;

        if (map_name.equals("taskDataList")) {

            taskDataList = (ConcurrentHashMap<Long, TaskData>) localdata;
        } else if (map_name.equals("processlist")) {

            System.out.println("in processlist");
            processList = (Set<Long>) localdata;

        } else if (map_name.equals("subProcessLists")) {

            subProcessLists = (ConcurrentHashMap<Long, Set<Long>>) localdata;
        }

        ResultSetMetaData rm = rs.getMetaData();

        int cnum = rm.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= cnum; i++) {
                System.out.print(i + ":" + rm.getColumnName(i) + ":" + rs.getObject(i) + " ");

            }
            if (map_name.equals("taskDataList")) {
                String[] provisionData = rs.getString(9).split("-");

                for (String data : provisionData) {

                    System.out.println(data);

                }

                TaskData taskdata = new TaskData(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), provisionData, Boolean.valueOf(rs.getString(11)));
                taskDataList.putIfAbsent(Long.valueOf(rs.getString(2)), taskdata);

                System.out.println("");

            } else if (map_name.equals("processlist")) {

                System.out.println("in pl rs:" + rs.getString(3));
                String[] pl = rs.getString(3).split(":");
                for (String p : pl) {
                    if (!p.equals("")) {

                        processList.add(Long.valueOf(p));

                    }

                }

            } else if (map_name.equals("subProcessLists")) {

                System.out.println("in spl rs:" + rs.getString(3));

            }
        }

    }

    public static ResultSet excecuteSql(String database, String qry1) throws SQLException {

        Connection connection = null;
        connection = getConnection(database);
        Statement stmt = connection.createStatement();
        //String qry1 = "select * from Task where processInstanceId = 2";

        int index1 = qry1.indexOf(" ", 0);
        System.out.println("qry1 index:" + index1);

        String opType = qry1.substring(0, index1);

        System.out.println("qry1 substring:" + opType);

        if (opType.equals("select")) {

            ResultSet rs = stmt.executeQuery(qry1);
            //printDB(rs);
            return rs;
        } else {
            int index = stmt.executeUpdate(qry1);
            System.out.println("DBindex2=>" + index);

        }

        //rs.close(); 
        stmt.close();
        connection.close();
        System.out.println("Disconnected from database");

        return null;

    }

    public static void excecuteSql(String qry1) throws SQLException {

        Connection connection = null;
        connection = getConnection("wslog");
        Statement stmt = connection.createStatement();
            //String qry1 = "select * from Task where processInstanceId = 2";

        //ResultSet rs = stmt.executeQuery(qry1);
        int index = stmt.executeUpdate(qry1);
        System.out.println("DBindex1=>" + index);

        //printDB(rs);
        //rs.close(); 
        stmt.close();
        connection.close();
        System.out.println("Disconnected from database");

    }

    public static void addsystmpvar(String tablename, String col, String pid, String param) throws SQLException {

        String chid_pids = "";
        String progress = "";

        if (tablename.equals("subprocesslists")) {

            chid_pids = param;

        } else if (tablename.equals("progressofprocess")) {

            progress = param;
        }

        ResultSet rs = excecuteSql("systmpdata", "select * from `" + tablename + "` where `pid` = '" + pid + "';");

        ResultSetMetaData rm = rs.getMetaData();

        int cnum = rm.getColumnCount();

        String spl = "";

        int count;

        if (rs.last()) {

            count = rs.getRow();
        } else {

            count = 0;

        }

        System.out.println("count:" + count);

        rs.beforeFirst();

        while (rs.next()) {
            for (int i = 1; i <= cnum; i++) {
                System.out.print(rm.getColumnName(i) + ":" + rs.getObject(i) + " ");

            }

            System.out.println("child_pids:" + rs.getString(3));
            spl = rs.getString(3);
        }

        if (count == 0) {

            if (tablename.equals("subprocesslists")) {

                excecuteSql("systmpdata", "insert into `" + tablename + "`(`pid`,`" + col + "`) values ('" + pid + "','" + chid_pids + "');");

            } else if (tablename.equals("progressofprocess")) {

                excecuteSql("systmpdata", "insert into `" + tablename + "`(`pid`,`" + col + "`) values ('" + pid + "','" + progress + "');");
            }

        } else {

            if (tablename.equals("subprocesslists")) {
                String[] splarr = spl.split(":");
                boolean flag = false;
                for (String element : splarr) {

                    if (element.equals(chid_pids)) {

                        flag = true;

                    }
                }
                if (flag == true) {

                } else if (flag == false) {

                    spl += ":" + chid_pids;
                    excecuteSql("systmpdata", "update `" + tablename + "` set `" + col + "`='" + spl + "' where `pid`='" + pid + "';");

                }

            } else if (tablename.equals("progressofprocess")) {

                excecuteSql("systmpdata", "update `" + tablename + "` set `" + col + "`='" + progress + "' where `pid`='" + pid + "';");

            }

        }

    }

    public static void main(String[] args) throws SQLException {

        //excecuteSql("wslog","describe core_task");
        //insert example excecuteSql("systmpdata","insert into `taskdatalist`(`taskname`,`result`) values ('ww','zz') ");
        //update example excecuteSql("systmpdata","update `taskdatalist` set `processinstanceid`='x' where `ID`='2' ");
        //delete example excecuteSql("systmpdata","delete from `taskdatalist` where `ID`='2' ");
        // excecuteSql("systmpdata", "insert into `taskdatalist`(`processinstanceid`,`taskname`,`requesttime`,`responsetime`,`result`,`errorcode`,`type`,`provisiondata`,`index1`,`needcallback`) values ('aa','bb','cc','dd','ee','ff','ww','zz','qq','kk'); ");
        /*
         TaskData taskData = new TaskData();
         String pD2string = "";
         excecuteSql("systmpdata", "update `taskdatalist` set `processinstanceid`='"+taskData.getProcessInstanceId()+"',`taskname`='"+taskData.getTaskName()+"',`requesttime`='"+taskData.getRequestTime()+"',`responsetime`='"+taskData.getResponseTime()+"',`result`='"+taskData.getResult()+"',`errorcode`='"+taskData.getErrorCode()+"',`type`='"+taskData.getType()+"',`provisiondata`='"+pD2string+"',`index1`='"+taskData.getIndex()+"',`needcallback`='"+taskData.isNeedCallback()+"' where `processinstanceid`='"+taskData.getProcessInstanceId()+"' ");
         */
        /*
        
         ResultSet rs = excecuteSql("systmpdata", "select * from `taskdatalist`; ");
         
         ConcurrentHashMap<Long, TaskData> taskDataList = new ConcurrentHashMap<Long, TaskData>(); //record running task of running process.  
   
         db2ccmap(rs, taskDataList);
         */
        /*
         editRuntimeval("processlist","168","del");
         */
    }
}
