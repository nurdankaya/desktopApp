/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLPopupController implements Initializable {

    @FXML
    private Button okayButton;
    @FXML
    private Text alarmText;

    String patientName;

    public void setPatientName(String patientName) {
        this.patientName = patientName;
        System.out.println(patientName + "\nHastanın ateşi yükseldi!");
        alarmText.setText(patientName + "\nHastanın ateşi yükseldi!");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void handleButtonOkay(ActionEvent event) {      //close window
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }

}
