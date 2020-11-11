/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import com.sun.javafx.charts.Legend;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.Parent;
import temperatureproject.DBConnection;
import temperatureproject.ModelTable;
import temperatureproject.SelectPatient;
import temperatureproject.LiveData;
import temperatureproject.serial.SerialCommunication;
import jssc.SerialPortException;

/**
 *
 * @author NURDAN
 */
public class FXMLController implements Initializable {

    public static int idMachine = 0;

    private Label label;
    @FXML
    private AnchorPane menuVertical;
    @FXML
    private AnchorPane main;

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> columnDate;
    @FXML
    private TableColumn<ModelTable, String> columnTime;
    @FXML
    private TableColumn<ModelTable, String> columnTemperature;
    @FXML
    private TableColumn<ModelTable, String> columnHumidity;

    public static ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    ObservableList<ModelTable> oblist2 = FXCollections.observableArrayList();
    int counter1 = 0;   //record number
    int counter2 = 0;   //control record number

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @FXML
    private LineChart<String, Float> temperatureMonitor;
    @FXML
    private LineChart<String, Float> humidityMonitor;
    @FXML
    private Button registerPatient;

    String date, time, date2, time2;
    float temp, hum, temp2, hum2;

    public static XYChart.Series seriesT = new XYChart.Series<>();  //chart for temperature
    public static XYChart.Series seriesH = new XYChart.Series<>();  //chart for humidity

    @FXML
    private Button export;

    private ObservableList<String> options = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> selectPatient;
    @FXML
    private Button showTable;

    public static String machineShow = null;

    @FXML
    private AnchorPane mainArea;
    @FXML
    private Button addMachine;

    AnchorPane panePersonel;
    AnchorPane paneAddMachine;
    AnchorPane panePatient;
    AnchorPane paneExport;
    AnchorPane paneCommunication;
    SerialCommunication serialComm;

    Parent root = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            serialComm = new SerialCommunication();

//            serialComm.portSender();
            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainTable.fxml"));

            mainArea.getChildren().setAll(root);

            seriesT.getData().clear();                                       //    önceki seçilmiş hastanın sıcaklık grafiği verilerini temizler
            seriesH.getData().clear();                                       //    önceki seçilmiş hastanın nem grafiği verilerini temizler

            temperatureMonitor.setAnimated(false);
            temperatureMonitor.getData().addAll(seriesT);   //add values in the seriesT to Temperature line chart

            humidityMonitor.setAnimated(false);
            humidityMonitor.getData().add(seriesH);        //add values in the seriesT to Humidity line chart
            seriesH.setName("Nem");

            columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));      //give names for columns
            columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            columnTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            columnHumidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));

            new SelectPatient(selectPatient, options);

            scheduler.scheduleAtFixedRate(() -> {
                try {
                    new LiveData(serialComm, table, temperatureMonitor);
                } catch (SerialPortException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }, 1, 10000L, TimeUnit.MILLISECONDS
            );

            //run every 1 minute and look is there any new data
            // table.setItems(oblist);
            //table.getItems().clear();  //table in the main page clean when you turn the main page
            // chartTemperature.setTemperatureMonitor(temperatureMonitor);
            Timer timerT = new Timer();
            //  timerT.scheduleAtFixedRate(chartTemperature, 0, 11000);

            Timer timerH = new Timer();
            //   timerH.scheduleAtFixedRate(new ChartHumidity(), 0, 12000);

        } catch (SerialPortException | IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleButtonPersonel(ActionEvent event) throws IOException {
        mainArea.getChildren().clear();
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLPersonel.fxml"));
        mainArea.getChildren().setAll(root);
    }

    @FXML
    private void handleButtonAddMachine(ActionEvent event) throws IOException {
        mainArea.getChildren().clear();
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLAddMachine.fxml"));
        mainArea.getChildren().setAll(root);
    }

    @FXML
    private void handleButtonRegister(ActionEvent event) throws IOException {
        mainArea.getChildren().clear();
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLPatient.fxml"));
        mainArea.getChildren().setAll(root);
    }

    @FXML
    private void handleButtonTable(ActionEvent event) throws IOException, SQLException, ClassNotFoundException, NoSuchFieldException {
        mainArea.getChildren().clear();
        mainArea.getChildren().setAll(temperatureMonitor, humidityMonitor, table);
        //seriesT.getData().clear();                                       //    önceki seçilmiş hastanın sıcaklık grafiği verilerini temizler
        seriesH.getData().clear();                                       //    önceki seçilmiş hastanın nem grafiği verilerini temizler
        table.getItems().clear();                                        //    önceki seçilmiş hastanın tablo verilerini temizler

        String patientShow = (String) selectPatient.getValue();
        machineShow = DBConnection.machineForPatient(patientShow);
        //System.out.println("aaaaaaaaabutton:" + machineShow);
        //temperatureMonitor.getData().removeAll();
        for (XYChart.Series series : temperatureMonitor.getData()) {
            series.getData().clear();
        }
        //FirstInsertion firstInsertion = new FirstInsertion(machineShow, oblist, temperatureMonitor, table);
        updateStyleSheet();

        table.setItems(oblist);
        // chartTemperature.setPatientName(selectPatient.getValue());   //pass name for alert popup
        temperatureMonitor.setTitle(selectPatient.getValue());
    }

    @FXML
    private void handleButtonExport(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        mainArea.getChildren().clear();
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLExport.fxml"));
        mainArea.getChildren().setAll(root);

    }

    @FXML
    private void handleButtonHome(MouseEvent event) throws IOException {

        mainArea.getChildren().clear();
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainTable.fxml"));
        mainArea.getChildren().setAll(root);
    }

    @FXML
    private void handleButtonCommunication(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        mainArea.getChildren().clear();
        root = FXMLLoader.load(getClass().getResource("/fxml/FXMLCommunication.fxml"));
        mainArea.getChildren().setAll(root);
    }

    @FXML
    private void handleButtonActionClose(MouseEvent event) throws IOException {

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
