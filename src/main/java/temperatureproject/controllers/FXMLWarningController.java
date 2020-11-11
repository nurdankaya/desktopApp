/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author NURDAN
 */
public class FXMLWarningController {
    @FXML
    private Button okayButton;
    @FXML
    private TextArea textWarning;

    @FXML
    private void handleButtonOkay(ActionEvent event)  {
             
    }
    
    
    /*
    *  Shows warning stage 
    */
    void showWarning() throws IOException{
          Parent root;
         root = FXMLLoader.load(getClass().getClassLoader().getResource("/src/main/resources/fxml/FXMLWarning.fxml"));
         Stage stage = new Stage();
         stage.setTitle("My New Stage Title");
         stage.setScene(new Scene(root, 450, 450));
         stage.show();  
    }
    
}
