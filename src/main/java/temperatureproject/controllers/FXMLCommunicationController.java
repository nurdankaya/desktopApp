/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import temperatureproject.DBConnection;
import temperatureproject.SelectPatient;
import temperatureproject.modelTables.ModelCommunication;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLCommunicationController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableView<ModelCommunication> communicationTable;
    @FXML
    private TableColumn<ModelCommunication, String> columnPersonelId;
    @FXML
    private TableColumn<ModelCommunication, String> columnNameSurname;
    @FXML
    private TableColumn<ModelCommunication, String> columnJob;
    @FXML
    private TableColumn<ModelCommunication, String> columnDepartment;
    @FXML
    private TableColumn<?, ?> columnCommunication;
    @FXML
    private TableColumn<ModelCommunication, CheckBox> columnSms;
    @FXML
    private TableColumn<ModelCommunication, CheckBox> columnMail;
    @FXML
    private ComboBox<String> patientsBox;

    ObservableList<ModelCommunication> oblistCommunication = FXCollections.observableArrayList();
    private ObservableList<String> options = FXCollections.observableArrayList();
    @FXML
    private ListView<String> personelsList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        communicationTable();
        try {
            new SelectPatient(patientsBox, options);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLCommunicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void communicationTable() {
        try {
            communicationTable.getItems().clear();  //table in the main page clean when you turn the main page
            columnPersonelId.setCellValueFactory(new PropertyValueFactory<>("idPersonel"));
            columnNameSurname.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
            columnJob.setCellValueFactory(new PropertyValueFactory<>("job"));      //give names for columns
            columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
            columnSms.setCellValueFactory(new PropertyValueFactory<>("sms"));
            columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

            Connection con = DBConnection.getConnection();
            try {
                //periodically search for new additions every selected second

                ResultSet rs = con.createStatement().executeQuery("select * from personel");  //take personel infos from database

                while (rs.next()) {  //export tables of all machines

                    int idPersonel = rs.getInt("idPersonel");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String job = rs.getString("job");
                    String department = rs.getString("department");

                    oblistCommunication.add(new ModelCommunication(idPersonel, (name + " " + surname), job, department, new CheckBox(), new CheckBox()));
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPersonelController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPersonelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        communicationTable.setItems(oblistCommunication);

    }

    /*
     * 
     * take the tc of the personel and 
     */
    @FXML
    private void showSelectedPersonels(ActionEvent event) throws SQLException, ClassNotFoundException {
        patientsBox.getValue();
        System.out.println("*******************************************1");
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("select * from temperature_db.patients");

        while (rs.next()) {
            String item = rs.getInt("idpatients") + " - " + rs.getString("name") + " " + rs.getString("surname");
            System.out.println(item);
            System.out.println(patientsBox.getValue());
            if (item.trim().equals(patientsBox.getValue().trim())) {
                String patientTc = rs.getString("patientTC");
                int[] id = new int[20];
                int i = 0;

                ResultSet rs2 = con.createStatement().executeQuery("select * from communication where tcPatient='" + patientTc.trim() + "'");
                while (rs2.next()) {

                    id[i] = rs2.getInt("idPersonel");

                    String info = personelInfo(id[i]);
                    System.out.println(info + "*info******************************************4");
                    Platform.runLater(() -> {
                        personelsList.getItems().add(info);
                    });
                    i++;
                }

            }
        }
    }

    /*
     *    "personelInfo" returns personel name and surname according to their id
     */
    private String personelInfo(int id) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("select * from temperature_db.personel");
        String info = null;
        int i = 0;
        while (rs.next()) {
            if (id == rs.getInt("idpersonel")) {
                info = rs.getString("name") + " " + rs.getString("surname");
            }

        }
        return info;
    }

 

}
