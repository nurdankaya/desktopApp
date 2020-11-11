/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.modelTables;

/**
 *
 * @author NURDAN
 */
public class ModelMachine {
    String machineName;
    String state;
    String portName;

    public ModelMachine(String machineName, String state, String portName) {
        this.machineName = machineName;
        this.state = state;
        this.portName = portName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
    

    
    
}
