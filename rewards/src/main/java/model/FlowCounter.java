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
public class FlowCounter implements java.io.Serializable{
    
    String nodeId;
    String port;
    String up;
    String down;
    String status;

    public FlowCounter() {
    }

    
    
    public FlowCounter(String nodeId, String port, String up, String down) {
        this.nodeId = nodeId;
        this.port = port;
        this.up = up;
        this.down = down;
    }

    public FlowCounter(String nodeId, String port, String up, String down,String status) {
        this.nodeId = nodeId;
        this.port = port;
        this.up = up;
        this.down = down;
        this.status = status;
    }
    
    
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        return "FlowCounter{" + "nodeId=" + nodeId + ", port=" + port + ", up=" + up + ", down=" + down +", status="+status+ '}';
    }
    
    
    
}
