package com.thephoenixzone.phoenixzone.nurselogin;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextInputEditText editTextEmail, editTextMobile, editTextName, editTextEducation, editTextSalary, editTextPassword, editTextCPassword;
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;
    Button btnAdd;
//    List<Patient> patientList = new ArrayList<>();
////    Spinner patient_name;
////    boolean flag = false;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editTextEducation = (TextInputEditText) view.findViewById(R.id.editTextEducation);
        editTextSalary = (TextInputEditText) view.findViewById(R.id.editTextSalary);
        editTextEmail = (TextInputEditText) view.findViewById(R.id.editTextEmail);
        editTextMobile = (TextInputEditText) view.findViewById(R.id.editTextMobile);
        editTextName = (TextInputEditText) view.findViewById(R.id.editTextName);
        editTextPassword = (TextInputEditText) view.findViewById(R.id.editTextPassword);
        editTextCPassword = (TextInputEditText) view.findViewById(R.id.editTextCPassword);
        btnAdd = (Button) view.findViewById(R.id.btnUpdate);
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
                if (editTextPassword.getText().toString().equals("")) {
                    editTextPassword.setError("Enter Password");
                    return;
                }
                if (editTextPassword.getText().toString().equals("")) {
                    editTextCPassword.setError("Password does not matched");
                    return;
                }
                if (editTextMobile.getText().toString().equals("") || editTextMobile.getText().toString().length() != 10) {
                    editTextMobile.setError("Valid mobile number");
                    return;
                }
                if (editTextEducation.getText().toString().equals("")) {
                    editTextEducation.setError("Enter Education");
                    return;
                }
                if (editTextSalary.getText().toString().equals("")) {
                    editTextSalary.setError("Enter Salary");
                    return;
                }
                new UpdateNurseInfo().execute();
            }
        });
        displayNurseInfo();
        return view;
    }

    public class UpdateNurseInfo extends AsyncTask<String, String, String> {
        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;
        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProfileFragment.this.getContext());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            params.add(new BasicNameValuePair("name", editTextName.getText().toString()));
            params.add(new BasicNameValuePair("email", editTextEmail.getText().toString()));
            params.add(new BasicNameValuePair("mobile", editTextMobile.getText().toString()));
            params.add(new BasicNameValuePair("education", editTextEducation.getText().toString()));
            params.add(new BasicNameValuePair("salary", editTextSalary.getText().toString()));
            params.add(new BasicNameValuePair("password", editTextPassword.getText().toString()));
            params.add(new BasicNameValuePair("nurse_id", ServerUtility.nurse_id));

        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_update_profile(), "POST", params);
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
                Toast.makeText(ProfileFragment.this.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    void displayNurseInfo() {
        editTextName.setText(ServerUtility.nurse_name);
        editTextEmail.setText(ServerUtility.nurse_email);
        editTextMobile.setText(ServerUtility.nurse_mobile);
        editTextSalary.setText("" + ServerUtility.nurse_salary);
        editTextEducation.setText(ServerUtility.nurse_education);
    }
}
