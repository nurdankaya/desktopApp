/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import temperatureproject.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import temperatureproject.DBConnection;
import temperatureproject.DBConnection;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLExportController implements Initializable {

    @FXML
    private ChoiceBox<String> patientChoice;
    @FXML
    private Button exportTable;

    public ObservableList<String> options = FXCollections.observableArrayList();
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField fileName;

    /**
     * Initializes the controller class , choose patient name with combobox to export table from database
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            String item;                                                    
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from temperature_db.patients");

            while (rs.next()) {
                item = rs.getString("idpatients") + " - " + rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("machineName");
                options.add(item);

            }
            patientChoice.setItems(FXCollections.observableArrayList(options));  
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLExportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /*
     * export table with the given name  
     */
    @FXML
    private void handleButtonExportPatient(ActionEvent event) throws ClassNotFoundException, SQLException {
        String[] splited = patientChoice.getValue().split("\\s+");
        Connection con = DBConnection.getConnection();

        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + splited[5] + " INTO OUTFILE '" + fileName.getText() + ".csv'  FIELDS ENCLOSED BY '\"'  TERMINATED BY ';'  ESCAPED BY '\"'  LINES TERMINATED BY '\\r\\n'");
        System.out.println("exported!!");            // export at  C:\ProgramData\MySQL\MySQL Server 8.0\Data\temperature_db
        fileName.clear();
    }

}
