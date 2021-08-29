package com.thephoenixzone.phoenixzone.nurselogin;

public class PatientHistory {
    private String patient_name;
    private String admitted_on;
    private String discharge_on;
    private String nurse_name;
    private String ward_number;
    private String bed_number;
    private String description;

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
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

    public String getNurse_name() {
        return nurse_name;
    }

    public void setNurse_name(String nurse_name) {
        this.nurse_name = nurse_name;
    }

    public String getWard_number() {
        return ward_number;
    }

    public void setWard_number(String ward_number) {
        this.ward_number = ward_number;
    }

    public String getBed_number() {
        return bed_number;
    }

    public void setBed_number(String bed_number) {
        this.bed_number = bed_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
