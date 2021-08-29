package com.thephoenixzone.phoenixzone.nurselogin;

public class Patient {
    private String name;
    private String mobile;
    private String email;
    private String address;
    private String admitted_on;
    private String discharge_on;
    private String status;
    private String saline_level;
    private String description;
    private String patient_id;

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getSaline_level() {
        return saline_level;
    }

    public void setSaline_level(String saline_level) {
        this.saline_level = saline_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmitted_on() {
        return admitted_on;
    }

    public void setAdmitted_on(String admitted_on) {
        this.admitted_on = admitted_on;
    }

    public String getDischarge_on() {
        return discharge_on;
    }

    public void setDischarge_on(String discharge_on) {
        this.discharge_on = discharge_on;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
