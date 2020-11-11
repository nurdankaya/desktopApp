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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import temperatureproject.DBConnection;
import temperatureproject.modelTables.ModelPersonel;

/**
 * FXML Controller class
 *
 * @author NURDAN
 */
public class FXMLPersonelController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView goBack;
    @FXML
    private TableView<ModelPersonel> personelTable;
    @FXML
    private TableColumn<ModelPersonel, String> columnPersonelId;
    @FXML
    private TableColumn<ModelPersonel, String> columnNameSurname;
    @FXML
    private TableColumn<ModelPersonel, String> columnJob;
    @FXML
    private TableColumn<ModelPersonel, String> columnDepartment;
    @FXML
    private TableColumn<ModelPersonel, String> columnPhone;
    @FXML
    private TableColumn<ModelPersonel, String> columnMail;
    @FXML
    private TextField pName;
    @FXML
    private TextField pSurname;
    @FXML
    private TextField pDepartment;
    @FXML
    private ChoiceBox<String> jobChoice;
    @FXML
    private Button registerPersonel;
    @FXML
    private TextField pPhone;
    @FXML
    private TextField pMail;

    List<String> options = new ArrayList<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ObservableList<ModelPersonel> oblistPersonel = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        personelTable();
        jobSelectBox();
    }

    private void addColumn(String phone) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("select * from personel");  //take personel infos from database
        int idPersonel;
        while (rs.next()) {  //export tables of all machines
            if (phone == pPhone.getText()) {
                idPersonel = rs.getInt("idpersonel");
                String sqlAddColumn = "ALTER TABLE temperature_db.communication ADD COLUMN `" + Integer.toString(idPersonel) + "` TINYINT DEFAULT 0;";

                Statement stmt = con.createStatement();
                stmt.executeUpdate(sqlAddColumn);
            }
        }

    }

    @FXML
    private void handleRegisterEmploye() {
        try {

            Connection con = DBConnection.getConnection();
            String sqlCreate = "CREATE TABLE  IF NOT EXISTS temperature_db.`personel` (`idpersonel` int NOT NULL AUTO_INCREMENT, `name` varchar(45) DEFAULT NULL, `surname` varchar(45) DEFAULT NULL,`job` varchar(45) DEFAULT NULL,`department` varchar(45) DEFAULT NULL,`phone` varchar(45) DEFAULT NULL,`mail` varchar(45) DEFAULT NULL,PRIMARY KEY (`idpersonel`));";
            Statement stmt0 = con.createStatement();
            stmt0.execute(sqlCreate);

            String sqlInsert = "INSERT INTO personel (name,surname ,job,department,phone,mail) "
                    + "VALUES ( ?, ?, ?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
            preparedStatement.setString(1, pName.getText());
            preparedStatement.setString(2, pSurname.getText());
            preparedStatement.setString(4, pDepartment.getText());
            preparedStatement.setString(3, jobChoice.getSelectionModel().getSelectedItem());
            preparedStatement.setString(5, pPhone.getText());
            preparedStatement.setString(6, pMail.getText());
            preparedStatement.executeUpdate();
            addColumn(pPhone.getText());         //  eklenen personelin communication tablosunda kolonunu oluşturur
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        personelTable();

    }

    private void jobSelectBox() {

        options.add("doktor");
        options.add("hemşire");

        jobChoice.setItems(FXCollections.observableArrayList(options));

    }

    private void personelTable() {
        try {
            columnPersonelId.setCellValueFactory(new PropertyValueFactory<>("idPersonel"));
            columnNameSurname.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
            columnJob.setCellValueFactory(new PropertyValueFactory<>("job"));      //give names for columns
            columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

            Connection con = DBConnection.getConnection();

            scheduler.scheduleAtFixedRate(() -> {

                personelTable.getItems().clear();  //table in the main page clean when you turn the main page
                try {
                    //periodically search for new additions every selected second

                    ResultSet rs = con.createStatement().executeQuery("select * from personel");  //take personel infos from database

                    while (rs.next()) {  //export tables of all machines

                        int idPersonel = rs.getInt("idPersonel");
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        String job = rs.getString("job");
                        String department = rs.getString("department");
                        String phone = rs.getString("phone");
                        String mail = rs.getString("mail");

                        oblistPersonel.add(new ModelPersonel(idPersonel, (name + " " + surname), job, department, phone, mail));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLPersonelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }, 1, 10000L, TimeUnit.MILLISECONDS);//run every 1 minute and look is there any new data

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPersonelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        personelTable.setItems(oblistPersonel);

        personelTable.getItems().clear();  //table in the main page clean when you turn the main page

    }

    @FXML
    private void showSelectedPersonels() {

    }

    @FXML
    private void handleIconDelete(MouseEvent event) {
        ObservableList<ModelPersonel> selectedRows, allRows;
        allRows = personelTable.getItems();
        selectedRows = personelTable.getSelectionModel().getSelectedItems();
        for (ModelPersonel personel : selectedRows) {
            deletePersonel(personel.getIdPersonel());

            allRows.remove(personel);
        }
    }

    private void deletePersonel(int idPersonel) {

        try {
            Connection con = DBConnection.getConnection();
            String sqlInsert = "DELETE from personel where idpersonel = " + "'" + idPersonel + "'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
