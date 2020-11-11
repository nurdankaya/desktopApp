/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.modelTables;

/**
 *
 * @author NURDAN
 */
public class ModelPatient {

    String nameSurname, machineName, patientTC, patientNo, time;

    public ModelPatient(String nameSurname, String machineName, String patientTC, String patientNo, String time) {

        this.nameSurname = nameSurname;
        this.machineName = machineName;
        this.patientTC = patientTC;
        this.patientNo = patientNo;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getPatientTC() {
        return patientTC;
    }

    public void setPatientTC(String patientTC) {
        this.patientTC = patientTC;
    }

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

}
