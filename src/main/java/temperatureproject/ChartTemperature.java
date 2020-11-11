/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

import com.sun.javafx.charts.Legend;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import temperatureproject.DBConnection;
import temperatureproject.controllers.FXMLController;
import static temperatureproject.controllers.FXMLController.idMachine;
import static temperatureproject.controllers.FXMLController.machineShow;
import static temperatureproject.controllers.FXMLController.seriesT;

/**
 *
 * @author NURDAN
 */
/*
 Adds new data at the temperature line chart
 */
public class ChartTemperature {

    String time;
    float temp;
    String patientName;

    final int CHART_SIZE = 15;
    private XYChart.Series seriesUpAlert;  //chart for high temperature
    private LineChart<String, Float> temperatureMonitor;

    public XYChart.Series getSeriesUpAlert() {
        return seriesUpAlert;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public ChartTemperature(String time, float temp, LineChart<String, Float> temperatureMonitor) {
        this.temperatureMonitor = temperatureMonitor;

        Platform.runLater(() -> {


            /*if (temp >= 30.00) {  // if temp>=30 -> add different chart
             try {

             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/javafxtemperatureproject/popup/FXMLPopup.fxml"));
             Parent root1 = (Parent) fxmlLoader.load();

             System.out.println("p n : " + patientName);
             FXMLPopupController popupController = fxmlLoader.getController();
             popupController.setPatientName(patientName);

             Stage stage = new Stage();
             stage.setScene(new Scene(root1));
             stage.show();

             } catch (IOException ex) {
             Logger.getLogger(ChartTemperature.class.getName()).log(Level.SEVERE, null, ex);
             }

             }*/
            // System.out.println(tempValues.get(j) + "---------" + tempTime.get(j));
            seriesT.getData().add(new XYChart.Data<>(time, temp));
            if (temp >= 30) {
                seriesUpAlert = new XYChart.Series<>();
                temperatureMonitor.getData().add(seriesUpAlert);

                seriesUpAlert.getData().add(new XYChart.Data<>(time, temp));
                System.out.println("iç1111111---------" + seriesT.getData().size());

            }

            if (seriesT.getData().size() > CHART_SIZE) {

                for (int i = 1; i < temperatureMonitor.getData().size(); i++) {
                    if (temperatureMonitor.getData().get(i).getData().size() > 0) {
                        if (temperatureMonitor.getData().get(0).getData().get(0).XValueProperty().get() == temperatureMonitor.getData().get(i).getData().get(0).XValueProperty().get()) {

                            // System.out.println("iç-------------------------------------------" + temperatureMonitor.getData().size());
                            //System.out.println("içxxxx-------------------------------------------" + temperatureMonitor.getData().get(i).getData());
                            temperatureMonitor.getData().get(i).getData().remove(0);

                        }
                    }
                    if (temperatureMonitor.getData().get(i).getData().size() == 0) {
                        temperatureMonitor.getData().remove(i);
                    }
                }
                seriesT.getData().remove(0);
            }

            updateStyleSheet();
        });

    }

    private void updateStyleSheet() {
        Legend legend = (Legend) temperatureMonitor.lookup(".chart-legend");
        AtomicInteger count = new AtomicInteger();
        legend.getItems().forEach(item -> {
            if (count.get() == 0) {
                item.setText("sıcaklık<30");
            } else if (count.get() == 1) {
                item.setText("sıcaklık>30");
            } else {
                item.setText("Remove");
            }
            count.getAndIncrement();
        });
        legend.getItems().removeIf(item -> item.getText().equals("Remove"));
    }
}
