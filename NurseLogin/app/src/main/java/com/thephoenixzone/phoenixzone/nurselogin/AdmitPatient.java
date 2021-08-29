package com.thephoenixzone.phoenixzone.nurselogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdmitPatient extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextMobile, editTextName, editTextAddress, editTextDescription;
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;
    Button btnAdd;
    List<Patient> patientList = new ArrayList<>();
    Spinner patient_name;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admit_patient);
        TextView txtBedNumber = (TextView) findViewById(R.id.txtBedNumber);
        txtBedNumber.setText("" + ViewBedsFragment.selectedSeat);
        editTextAddress = (TextInputEditText) findViewById(R.id.editTextAddress);
        editTextDescription = (TextInputEditText) findViewById(R.id.editTextDescription);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextMobile = (TextInputEditText) findViewById(R.id.editTextMobile);
        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        patient_name = (Spinner) findViewById(R.id.patient_name);
        patient_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //display selected patient information
                if (i == 0) {
                    initialiseObjects();
                    return;
                }
                displayPatientInfo(i - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                new AddPatientInfo().execute();
            }
        });
        new GetPatientInfo().execute();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class AddPatientInfo extends AsyncTask<String, String, String> {
        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;
        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AdmitPatient.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            params.add(new BasicNameValuePair("name", editTextName.getText().toString()));
            params.add(new BasicNameValuePair("email", editTextEmail.getText().toString()));
            params.add(new BasicNameValuePair("mobile", editTextMobile.getText().toString()));
            params.add(new BasicNameValuePair("address", editTextAddress.getText().toString()));
            params.add(new BasicNameValuePair("description", editTextDescription.getText().toString()));
            params.add(new BasicNameValuePair("nurse_id", ServerUtility.nurse_id));
            params.add(new BasicNameValuePair("bed_number", "" + ViewBedsFragment.selectedSeat));
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_add_patient_info(), "POST", params);
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
                Toast.makeText(AdmitPatient.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NurseHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }


    public class GetPatientInfo extends AsyncTask<String, String, String> {

        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;
        List<String> list = new ArrayList<String>();

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(AdmitPatient.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_get_patient_info(), "POST", params);
            try {

                JSONArray jsonArray = object.getJSONArray("PatientInfo");
                patientList = new ArrayList<>();
                list = new ArrayList<>();
                list.add("Select Patient Name");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    Patient patient = new Patient();
                    patient.setName(object1.getString("name"));
                    patient.setAddress(object1.getString("address"));
                    patient.setMobile(object1.getString("mobile"));
                    patient.setEmail(object1.getString("email"));
                    patientList.add(patient);
                    list.add(object1.getString("name"));
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdmitPatient.this, android.R.layout.simple_spinner_dropdown_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            patient_name.setAdapter(adapter);

        }
    }

    void displayPatientInfo(int id) {
        Patient patient = patientList.get(id);
        editTextName.setText(patient.getName());
        editTextEmail.setText(patient.getEmail());
        editTextMobile.setText(patient.getMobile());
        editTextAddress.setText(patient.getAddress());
    }

    void initialiseObjects() {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextMobile.setText("");
        editTextAddress.setText("");
    }
}
