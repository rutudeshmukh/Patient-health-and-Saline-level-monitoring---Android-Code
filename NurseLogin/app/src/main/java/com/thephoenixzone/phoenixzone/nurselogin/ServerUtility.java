package com.thephoenixzone.phoenixzone.nurselogin;

/**
 * Created by Dell on 28-10-2017.
 */
public class ServerUtility {
    public static String Server_URL = "http://192.168.0.106:8084/SalineLevel/";
    public static boolean flag_Activity = false;
    public static String ward_name = "";
    public static String ward_id = "";
    public static String nurse_id = "";
    public static String nurse_name = "";
    public static String nurse_mobile = "";
    public static String nurse_email = "";
    public static String nurse_education = "";
    public static String nurse_joining = "";
    public static String nurse_salary = "";


    public static String url_get_patient_info() {
        return ServerUtility.Server_URL + "GetPatientInfo";
    }

    public static String url_login_info() {
        return ServerUtility.Server_URL + "NurseLogin";
    }

    public static String url_get_seats() {
        return ServerUtility.Server_URL + "GetBedNumbers";
    }

    public static String url_add_patient_info() {
        return ServerUtility.Server_URL + "AddPatientInfo";
    }

    public static String url_get_admitted_patient() {
        return ServerUtility.Server_URL + "GetPatientById";
    }

    public static String url_update_admitted_patient_info() {
        return ServerUtility.Server_URL + "UpdateAdmittedPatientInfo";
    }
    public static String url_update_profile() {
        return ServerUtility.Server_URL + "UpdateNurseProfile";
    }
    public static String url_patient_history() {
        return ServerUtility.Server_URL + "PatientHistory";
    }
    public static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "items";
    public static final String TAG_ITEMS = "Bhel";
    public static final String TAG_CAT = "Bhel";
}
