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
public class ModelPersonel {

    int idPersonel;
    String nameSurname;
    String job;
    String department;
    String phone;
    String mail;

    public ModelPersonel(int idPersonel, String nameSurname, String job, String department, String phone, String mail) {
        this.idPersonel = idPersonel;
        this.nameSurname = nameSurname;
        this.job = job;
        this.department = department;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
