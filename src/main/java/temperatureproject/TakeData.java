/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import jssc.SerialPortException;
import temperatureproject.DBConnection;
import temperatureproject.controllers.FXMLController;
import static temperatureproject.controllers.FXMLController.idMachine;
import static temperatureproject.controllers.FXMLController.machineShow;
import static temperatureproject.controllers.FXMLController.oblist;
import static temperatureproject.controllers.FXMLController.seriesH;
import temperatureproject.ModelTable;
import temperatureproject.DateAndTime;
import temperatureproject.serial.SerialCommunication;

/**
 *
 * @author NURDAN
 */
public class TakeData {

    DateAndTime dateAndTime;
    String temp, hum, date, time;

    public String getTemp() {
        return temp;
    }

    public String getHum() {
        return hum;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void takeData(SerialCommunication serialComm) throws SerialPortException {
        dateAndTime = new DateAndTime();
        temp = null;
        hum = null;
        date = null;
        time = null;
      
      
        serialComm.portSender();
        temp = serialComm.getTemperature();
        hum = serialComm.getHumidity();

        date = dateAndTime.takeDate();
        time = dateAndTime.taketime();
        System.out.println(temp + "....." + hum);
        System.out.println(date + "....." + time);

       
    }
}
