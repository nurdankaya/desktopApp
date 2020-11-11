/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.XYChart;
import static temperatureproject.controllers.FXMLController.oblist;
import static temperatureproject.controllers.FXMLController.idMachine;
import static temperatureproject.controllers.FXMLController.seriesH;
import static temperatureproject.controllers.FXMLController.seriesT;

/**
 *
 * @author NURDAN
 */
public class TableShow {

    String machineTable;

    public TableShow(String machineTable) {
        this.machineTable = machineTable;

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select * from " + machineTable);

            while (rs.next()) {
                //System.out.println(rs.getString("date_curr"));
                idMachine = rs.getInt("idMachine");
                String date = rs.getString("date_curr");
                String time = rs.getString("time_curr");
                float temp = rs.getFloat("temperature");
                float hum = rs.getFloat("humidity");

                oblist.add(new ModelTable(date, time, temp, hum));

                seriesT.getData().add(new XYChart.Data<>(time, temp));
                seriesH.getData().add(new XYChart.Data<>(time, hum));
                
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TableShow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
