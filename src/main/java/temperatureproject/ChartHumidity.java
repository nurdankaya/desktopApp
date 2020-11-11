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
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import temperatureproject.DBConnection;
import temperatureproject.controllers.FXMLController;
import static temperatureproject.controllers.FXMLController.idMachine;
import static temperatureproject.controllers.FXMLController.machineShow;
import static temperatureproject.controllers.FXMLController.seriesH;

/**
 *
 * @author NURDAN
 */
/*
 Adds new data at the humidity line chart
 */
public class ChartHumidity {

    String time;
    float hum;
    final int CHART_SIZE = 15;

    public ChartHumidity(String time, float hum) {
        System.out.println("chart hum girdi");
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                seriesH.getData().add(new XYChart.Data<>(time, hum));
            }
        };
        Platform.runLater(runnable);

        if (seriesH.getData().size() > CHART_SIZE) {
            Runnable runnable2 = new Runnable() {

                @Override
                public void run() {
                    seriesH.getData().remove(0);
                }
            };
            Platform.runLater(runnable2);

        }

        System.out.println("chart hum çıktı");
    }

}
