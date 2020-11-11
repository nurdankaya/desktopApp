/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.modelTables;

import java.awt.Checkbox;
import javafx.scene.control.CheckBox;

/**
 *
 * @author NURDAN
 */
public class ModelCommunication {
int idPersonel;
String nameSurname;
String job;
String department;
CheckBox sms;
CheckBox mail;

    public ModelCommunication(int idPersonel, String nameSurname, String job, String department, CheckBox sms, CheckBox mail) {
        this.idPersonel = idPersonel;
        this.nameSurname = nameSurname;
        this.job = job;
        this.department = department;
        this.sms = sms;
        this.mail = mail;
    }

    public int getIdPersonel() {
        return idPersonel;
    }

    public void setIdPersonel(int idPersonel) {
        this.idPersonel = idPersonel;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public CheckBox getSms() {
        return sms;
    }

    public void setSms(CheckBox sms) {
        this.sms = sms;
    }

    public CheckBox getMail() {
        return mail;
    }

    public void setMail(CheckBox mail) {
        this.mail = mail;
    }
   
    
}
