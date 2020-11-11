/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import temperatureproject.DBConnection;
import temperatureproject.ModelTableMainPage;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLMainTableController extends AnchorPane implements Initializable {

    @FXML
    private TableView<ModelTableMainPage> mainTable;
    @FXML
    private TableColumn<ModelTableMainPage, String> columnPatientId;
    @FXML
    private TableColumn<ModelTableMainPage, String> columnNameSurname;
    @FXML
    private TableColumn<ModelTableMainPage, String> columnDate;
    @FXML
    private TableColumn<ModelTableMainPage, String> columnTime;
    @FXML
    private TableColumn<ModelTableMainPage, String> columnTemperature;
    @FXML
    private TableColumn<ModelTableMainPage, String> columnHumidity;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ObservableList<ModelTableMainPage> oblistmain = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane.setBottomAnchor(this, 0.0);
            AnchorPane.setRightAnchor(this, 0.0);
            AnchorPane.setLeftAnchor(this, 0.0);
            AnchorPane.setTopAnchor(this, 0.0);
            columnPatientId.setCellValueFactory(new PropertyValueFactory<>("idPersonel"));
            columnNameSurname.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
            columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));      //give names for columns
            columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            columnTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            columnHumidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));

            Connection con = DBConnection.getConnection();
            int[] idMachine = new int[10];           //to save last id for 10 machines
            Arrays.fill(idMachine, 0);

          /*  scheduler.scheduleAtFixedRate(() -> {    //periodically search for new additions every selected second
                
                try {

                    String machine;
                    ResultSet rs1 = con.createStatement().executeQuery("select * from machines where state=1");  //show just avaliable machines
                    int i = 0;
                    while (rs1.next()) {  //export tables of all machines
                        machine = rs1.getString("machineName");
                        ResultSet rs2 = con.createStatement().executeQuery("select * from " + machine);
                        String[] takePatientInfo = takePatientInfo(machine); //take patient infos
    
                        while (rs2.next()) {
                            int tempId = rs2.getInt("idMachine");
                            System.out.println("aaaaa: " + tempId + "  " + idMachine[i]);
                            if (idMachine[i] < tempId) {
                                idMachine[i] = tempId;

                                String date = rs2.getString("date_curr");
                                String time = rs2.getString("time_curr");
                                float temp = rs2.getFloat("temperature");
                                float hum = rs2.getFloat("humidity");
                                System.out.println(time + " " + hum);
                               
                                oblistmain.add(new ModelTableMainPage(takePatientInfo[0], (takePatientInfo[1] + " " + takePatientInfo[2]), date, time, temp, hum));
                                if ("00:00:00".equals(time)) {
                                    DBConnection.saveDay();
                                }
                            }
                        }
                        i++;
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLMainTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }, 1, 10000L, TimeUnit.MILLISECONDS
            );//run every 1 minute and look is there any new data
            mainTable.setItems(oblistmain);
            mainTable.getItems().clear();  //table in the main page clean when you turn the main page*/

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLMainTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // take id, name, surname according to machine name
    public String[] takePatientInfo(String machine) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getConnection();
        System.out.println("select * from patients where machineName='" + machine + "'");
        ResultSet rs3 = con.createStatement().executeQuery("select * from patients where machineName='" + machine + "'");
        String[] infos = new String[3];
        while (rs3.next()) {
            if (machine.equals(rs3.getString("machineName"))){
                infos[0] = rs3.getString("idPatients");
                infos[1] = rs3.getString("name");
                infos[2] = rs3.getString("surname");
            }
        }
        return infos;
    }
}
