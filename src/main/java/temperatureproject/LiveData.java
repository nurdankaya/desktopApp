/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableView;
import jssc.SerialPortException;
import temperatureproject.controllers.FXMLController;
import static temperatureproject.controllers.FXMLController.oblist;
import temperatureproject.serial.SerialCommunication;

/**
 *
 * @author NURDAN
 */
public class LiveData {
    
    
    public LiveData(SerialCommunication serialComm, TableView<ModelTable> table, LineChart<String, Float> temperatureMonitor) throws SerialPortException{
        System.out.println("çıktı");
        TakeData takeData = new TakeData();
        
        takeData.takeData(serialComm);
       // if (table.isVisible()) {
            oblist.add(new ModelTable(takeData.getDate(), takeData.getTime(), Float.parseFloat(takeData.getTemp()), Float.parseFloat(takeData.getHum())));
            table.setItems(oblist);
            new ChartHumidity(takeData.getTime(),Float.parseFloat(takeData.getHum()));
            new ChartTemperature(takeData.getTime(),Float.parseFloat(takeData.getTemp()),temperatureMonitor);
       // }
    }
}
