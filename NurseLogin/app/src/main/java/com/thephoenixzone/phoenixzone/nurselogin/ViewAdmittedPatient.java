package com.thephoenixzone.phoenixzone.nurselogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewAdmittedPatient extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextMobile, editTextName, editTextAddress, editTextDescription, editTextBedNumber, editTextSaline, editTextAdmitted;
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;
    Button btnAdd;
    Patient patient = new Patient();
    Spinner status;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admitted_patient);
        editTextAddress = (TextInputEditText) findViewById(R.id.editTextAddress);
        editTextDescription = (TextInputEditText) findViewById(R.id.editTextDescription);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextMobile = (TextInputEditText) findViewById(R.id.editTextMobile);
        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        editTextBedNumber = (TextInputEditText) findViewById(R.id.editTextBedNumber);
        editTextSaline = (TextInputEditText) findViewById(R.id.editTextSaline);
        editTextAdmitted = (TextInputEditText) findViewById(R.id.editTextAdmitted);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText().toString().equals("")) {
                    editTextName.setError("Please enter patient name");
                    return;
                }
                if (editTextEmail.getText().toString().equals("")) {
                    editTextEmail.setError("Please enter email address");
                    return;
                }
                if (editTextMobile.getText().toString().equals("") || editTextMobile.getText().toString().length() != 10) {
                    editTextMobile.setError("Valid mobile number");
                    return;
                }
                if (editTextAddress.getText().toString().equals("")) {
                    editTextAddress.setError("Enter Address");
                    return;
                }
                if (editTextDescription.getText().toString().equals("")) {

                    editTextDescription.setError("Enter Description");
                    return;
                }
                if (status.getSelectedItemId() == 0) {
                    Toast.makeText(ViewAdmittedPatient.this, "Please select status", Toast.LENGTH_SHORT).show();
                    return;
                }
                new UpdatePatientInfo().execute();
            }
        });
        status = (Spinner) findViewById(R.id.status);
        status.setSelection(1);
        new GetPatientInfo().execute();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    public class UpdatePatientInfo extends AsyncTask<String, String, String> {

        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;
        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ViewAdmittedPatient.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            params.add(new BasicNameValuePair("patient_id", patient.getPatient_id()));
            params.add(new BasicNameValuePair("name", editTextName.getText().toString()));
            params.add(new BasicNameValuePair("email", editTextEmail.getText().toString()));
            params.add(new BasicNameValuePair("mobile", editTextMobile.getText().toString()));
            params.add(new BasicNameValuePair("address", editTextAddress.getText().toString()));
            params.add(new BasicNameValuePair("description", editTextDescription.getText().toString()));
            params.add(new BasicNameValuePair("nurse_id", ServerUtility.nurse_id));
            params.add(new BasicNameValuePair("bed_number", "" + ViewBedsFragment.selectedSeat));
            params.add(new BasicNameValuePair("status", status.getSelectedItem().toString()));
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_update_admitted_patient_info(), "POST", params);
            try {
                flag = object.has("success");
                message = object.getString("message");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (flag) {
                Toast.makeText(ViewAdmittedPatient.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NurseHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    public class GetPatientInfo extends AsyncTask<String, String, String> {

        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(ViewAdmittedPatient.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            params.add(new BasicNameValuePair("bed_number", "" + ViewBedsFragment.selectedSeat));

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_get_admitted_patient(), "POST", params);
            try {

                JSONArray jsonArray = object.getJSONArray("PatientInfo");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);

                    patient.setName(object1.getString("name"));
                    patient.setAddress(object1.getString("address"));
                    patient.setMobile(object1.getString("mobile"));
                    patient.setEmail(object1.getString("email"));
                    patient.setSaline_level(object1.getString("saline_level"));
                    patient.setAdmitted_on(object1.getString("admitted_on"));
                    patient.setDescription(object1.getString("description"));
                    patient.setPatient_id(object1.getString("patient_id"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            displayPatientInfo();

        }
    }

    void displayPatientInfo() {
        editTextName.setText(patient.getName());
        editTextEmail.setText(patient.getEmail());
        editTextMobile.setText(patient.getMobile());
        editTextAddress.setText(patient.getAddress());
        editTextAdmitted.setText(patient.getAdmitted_on());
        editTextAdmitted.setEnabled(false);
        editTextSaline.setText(patient.getSaline_level());
        editTextSaline.setEnabled(false);
        editTextBedNumber.setText("" + ViewBedsFragment.selectedSeat);
        editTextBedNumber.setEnabled(false);
        editTextDescription.setText(patient.getDescription());
    }

}
