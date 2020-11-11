/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import temperatureproject.DBConnection;
import temperatureproject.modelTables.ModelPatient;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLPatientController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField TcText;
    @FXML
    private TextField PNoText;
    @FXML
    private ChoiceBox<String> machineChoice;
    @FXML
    private TextField timeText;
    @FXML
    private Button registerPatient;
    @FXML
    private TableView<ModelPatient> patientTable;
    @FXML
    private TableColumn<ModelPatient, String> columnNameSurname;
    @FXML
    private TableColumn<ModelPatient, String> columnTC;
    @FXML
    private TableColumn<ModelPatient, String> columnPNo;
    @FXML
    private TableColumn<ModelPatient, String> columnMachine;
    @FXML
    private TableColumn<ModelPatient, String> columnTime;

    List<String> options = new ArrayList<>();
    ObservableList<ModelPatient> oblistPatient = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setChoiceBox();
            patientTable();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonPatient(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        insertPatient();
        patientTable();  //to add new record in the table
        nameText.clear();
        surnameText.clear();
        TcText.clear();
        PNoText.clear();
        timeText.clear();

    }

    @FXML
    private void handleIconDelete(MouseEvent event) {
        ObservableList<ModelPatient> selectedRows, allRows;
        allRows = patientTable.getItems();
        selectedRows = patientTable.getSelectionModel().getSelectedItems();
        for (ModelPatient patient : selectedRows) {
            deletePatient(patient.getPatientTC());
            makeMachineStateZero(patient.getMachineName());
            allRows.remove(patient);
        }

    }

    /*
     *   add avaliable machines into selection box  
     */
    private void setChoiceBox() {

        if (machineChoice != null) {
            machineChoice.getItems().clear();
        }
        try {
            patientTable();
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from machines where state=0");  //show just avaliable machines
            options.clear();
            while (rs.next()) {
                
                options.add(rs.getString("machineName"));

            }
            machineChoice.setItems(FXCollections.observableArrayList(options));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void patientTable() throws SQLException, ClassNotFoundException {
        patientTable.getItems().clear();  //table in the main page clean when you turn the main page

        columnNameSurname.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        columnTC.setCellValueFactory(new PropertyValueFactory<>("patientTC"));      //give names for columns
        columnPNo.setCellValueFactory(new PropertyValueFactory<>("patientNo"));
        columnMachine.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        Connection con = DBConnection.getConnection();

        patientTable.getItems().clear();  //table in the main page clean when you turn the main page
        try {
            //periodically search for new additions every selected second

            ResultSet rs = con.createStatement().executeQuery("select * from patients");  //take personel infos from database

            while (rs.next()) {  //export tables of all machines

                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patientTC = rs.getString("patientTC");
                String patientNo = rs.getString("patientNo");
                String machineName = rs.getString("machineName");
                int time = rs.getInt("time");

                oblistPatient.add(new ModelPatient((name + " " + surname), machineName, patientTC, patientNo, String.valueOf(time)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPersonelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        patientTable.setItems(oblistPatient);

    }

    private void insertPatient() {
        try {
            ModelPatient patient = new ModelPatient((nameText.getText() + " " + surnameText.getText()), machineChoice.getSelectionModel().getSelectedItem(), TcText.getText(), PNoText.getText(), timeText.getText());

            Connection con = DBConnection.getConnection();
            /* String sqlCreate = "CREATE TABLE IF NOT EXISTS " + patient.getMachineName()
             + "(date_curr varchar(40),"
             + "time_curr varchar(40),"
             + "temperature float,"
             + "humidity float)";
             Statement stmt = con.createStatement();
             stmt.execute(sqlCreate);*/

            String sqlInsert = "INSERT INTO patients (idpatients,machineName ,name,surname,patientTC,patientNo,time)"
                    + "VALUES (NULL, ?, ?, ?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
            preparedStatement.setString(1, patient.getMachineName());
            String[] temp = patient.getNameSurname().split("\\s+");
            preparedStatement.setString(2, temp[0]);
            preparedStatement.setString(3, temp[1]);
            preparedStatement.setString(4, patient.getPatientTC());
            preparedStatement.setString(5, patient.getPatientNo());
            preparedStatement.setInt(6, Integer.parseInt(patient.getTime()));
            preparedStatement.executeUpdate();

            String sqlUpdate = "UPDATE temperature_db.machines SET state = 1 WHERE  machineName ='" + patient.getMachineName() + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlUpdate);
            setChoiceBox();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deletePatient(String tc) {
        try {
            Connection con = DBConnection.getConnection();
            String sqlInsert = "DELETE from patients where patientTC = " + "'" + tc + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void makeMachineStateZero(String machine) {
        try {
            Connection con = DBConnection.getConnection();

            String sqlUpdate = "UPDATE temperature_db.machines SET state = 0 WHERE  machineName ='" + machine + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlUpdate);
            setChoiceBox();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
