/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

/**
 *
 * @author NURDAN
 */
public class ModelTableMainPage {

    String idPatient;
    String nameSurname;
    String date, time;
    float temperature, humidity;

    public ModelTableMainPage(String idPatient, String nameSurname, String date, String time, float temperature, float humidity) {
        this.idPatient = idPatient;
        this.nameSurname = nameSurname;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }



}
