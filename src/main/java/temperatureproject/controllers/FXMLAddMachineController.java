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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import temperatureproject.DBConnection;
import temperatureproject.modelTables.ModelMachine;
import temperatureproject.modelTables.ModelPatient;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLAddMachineController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField name;
    @FXML
    private Button registerMachine;
    @FXML
    private TextField portName;
    @FXML
    private TableView<ModelMachine> machineTable;
    @FXML
    private TableColumn<ModelMachine, String> columnPort;
    @FXML
    private TableColumn<ModelMachine, String> columnMachine;
    @FXML
    private TableColumn<ModelMachine, String> columnState;

    List<String> options = new ArrayList<>();
    ObservableList<ModelMachine> oblistMachine = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            machineTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            machineTable();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLAddMachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonRegisterMachine(ActionEvent event) throws SQLException, ClassNotFoundException {
        registerMachineDB(portName.getText(), name.getText());
        machineTable();
    }

    private void registerMachineDB(String portName, String machineName) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getConnection();

        String sqlInsert = "INSERT INTO machines (machineName,portName,state)"
                + "VALUES (?, ?,?)";

        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        preparedStatement.setString(1, machineName);
        preparedStatement.setString(2, portName);
        int state = 0;
        preparedStatement.setInt(3, state);
        preparedStatement.executeUpdate();
    }

    private void machineTable() throws SQLException, ClassNotFoundException {
        machineTable.getItems().clear();  //table in the main page clean when you turn the main page

        columnPort.setCellValueFactory(new PropertyValueFactory<>("portName"));
        columnMachine.setCellValueFactory(new PropertyValueFactory<>("machineName"));      //give names for columns
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));

        Connection con = DBConnection.getConnection();

        machineTable.getItems().clear();  //table in the main page clean when you turn the main page
        try {
            //periodically search for new additions every selected second

            ResultSet rs = con.createStatement().executeQuery("select * from machines");  //take personel infos from database

            while (rs.next()) {  //export tables of all machines

                String machineName = rs.getString("machineName");
                String state;
                if (rs.getInt("state") == 1) {
                    state = "dolu";
                } else {
                    state = "boş";
                }

                String portName = rs.getString("portName");

                oblistMachine.add(new ModelMachine(machineName, state, portName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPersonelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        machineTable.setItems(oblistMachine);

    }

    @FXML
    private void handleIconDelete(MouseEvent event) {
        ObservableList<ModelMachine> selectedRows, allRows;
        allRows = machineTable.getItems();
        selectedRows = machineTable.getSelectionModel().getSelectedItems();
        for (ModelMachine machine : selectedRows) {
            deleteMachine(machine.getMachineName());
          
            allRows.remove(machine);
        }
    }
    
     private void deleteMachine(String machineName) {
        try {         
            Connection con = DBConnection.getConnection();       
            String sqlInsert = "DELETE from machines where machineName = "+"'"+machineName+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
