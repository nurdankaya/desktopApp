/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author NURDAN
 */
public class SelectPatient {

    public SelectPatient(ComboBox<String> selectPatient, ObservableList<String> options) throws SQLException, ClassNotFoundException {   //show patient name with combobox ----
        int flag = 0;
        String item;
        Connection con2 = DBConnection.getConnection();
        selectPatient.getItems().clear();
        ResultSet rs2 = con2.createStatement().executeQuery("select * from temperature_db.patients");

        while (rs2.next()) {
            item = rs2.getInt("idpatients") + " - " + rs2.getString("name") + " " + rs2.getString("surname");

            for (int i = 0; i < options.size(); i++) {                //don't insert more than one
                if ((options.get(i)).equals(item)) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                options.add(item);
            }
        }
        selectPatient.setItems(FXCollections.observableArrayList(options));  // loads patient informations in combobox
    }

}
