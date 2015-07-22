/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws_client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import model.TaskData;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

/**
 *
 * @author root
 */
public class Tester {
    
    public static String webIP = "192.168.2.203";
    public static String webPort = "8080";
    public static String webTag = "WSTest/webresources/hello";
    
    public static String ccsIP = "192.168.2.203";
    public static String ccsPort = "8080";
    public static String ccsTag = "WSTest/webresources/hello";

    public Tester() {
    }
    
    

    public void testWSClient1(String baseUrl, TaskData taskData) {//post client¥áªk

//POSTªº¥áªk
        
        
        
        try {

            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
            //  clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
            Client client = Client.create(clientConfig);
            //WebResource webResource = client.resource("http://localhost:31360/WSTest/webresources/hello/giveLaya");
            WebResource webResource = client.resource(baseUrl);

            /*
             taskData.setType(staticAssignBPMData.getType());
             taskData.setProvisionData(staticAssignBPMData.getProvisiovData());
             taskData.setNeedCallback(staticAssignBPMData.isNeedCallBack());
             */
            //ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, new model.Laya("ss", 5, "ddd"));
            ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, taskData);

            if (response.getStatus() != 200) {
                System.out.println("POST Fail, Error Code:" + response.getStatus());
                
                /*
                if(baseUrl.equals("http://192.168.2.203:8080/WSTest/webresources/hello/")){
                
                    alert("\"POST Fail, Error Code:\" + response.getStatus()");
                }*/
                
            } else {
            //¬ÝSERVER¦^À³¤°»ò
                //¥i¥Hª½±µ¥Î¦r¦ê±µ
                System.out.println("response=>" + response.getEntity(String.class));
                String request = tool.getDate.getDate();
                System.out.println("request date=>" + request);
                // JOptionPane.showMessageDialog(null, "now date=>"+tool.getDate.getDate(), "¸õ¥Xµøµ¡¤º®e¼ÐÃD", JOptionPane.INFORMATION_MESSAGE );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

        public void defaultTestWSClient1(String baseUrl, String alert) {//post client¥áªk

//POSTªº¥áªk
        try {

            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
            //  clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
            Client client = Client.create(clientConfig);
            //WebResource webResource = client.resource("http://localhost:31360/WSTest/webresources/hello/giveLaya");
            WebResource webResource = client.resource(baseUrl);

            /*
             taskData.setType(staticAssignBPMData.getType());
             taskData.setProvisionData(staticAssignBPMData.getProvisiovData());
             taskData.setNeedCallback(staticAssignBPMData.isNeedCallBack());
             */
            //ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, new model.Laya("ss", 5, "ddd"));
            ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, alert);

            if (response.getStatus() != 200) {
                System.out.println("POST Fail, Error Code:" + response.getStatus());
            } else {
            //¬ÝSERVER¦^À³¤°»ò
                //¥i¥Hª½±µ¥Î¦r¦ê±µ
                System.out.println("response=>" + response.getEntity(String.class));
                String request = tool.getDate.getDate();
                System.out.println("request date=>" + request);
                // JOptionPane.showMessageDialog(null, "now date=>"+tool.getDate.getDate(), "¸õ¥Xµøµ¡¤º®e¼ÐÃD", JOptionPane.INFORMATION_MESSAGE );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    
    
    public void sendAutomationResult(String pid, String result) {//¥Îµ{¦¡µo¥XGET½d¨Ò

        //String baseUrl = "http://192.168.2.157:8080/WSTest/webresources/hello/getAutomationResult/"+pid+"/"+result;
        String baseUrl = "http://192.168.2.203:8080/WSTest/webresources/hello/getAutomationResult/" + pid + "/" + result;

        Client client = Client.create();

        System.out.println(client.resource(baseUrl).get(String.class));

    }
    
    public static void alert(String alert) {
           
         Tester t = new Tester();
         //String url = "http://192.168.2.203:8080/WSTest/webresources/hello/alert";
         String url = "http://"+webIP+":"+webPort+"/"+webTag+"/alert";
         t.defaultTestWSClient1(url, alert);
         
    }

}
