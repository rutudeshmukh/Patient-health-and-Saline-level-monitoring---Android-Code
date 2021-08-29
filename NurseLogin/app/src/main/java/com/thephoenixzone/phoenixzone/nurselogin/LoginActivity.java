package com.thephoenixzone.phoenixzone.nurselogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword, editTextServerIP;
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextServerIP = (TextInputEditText) findViewById(R.id.editTextServerIp);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextEmail.getText().toString().equals("")) {
                    editTextEmail.setError("Please Enter Email");
                    return;
                }
                if (editTextPassword.getText().toString().equals("")) {
                    editTextPassword.setError("Please Enter password");
                    return;
                }
                if (editTextServerIP.getText().toString().equals("")) {
                    editTextServerIP.setError("Enter Server IP");
                    return;
                }
                ServerUtility.Server_URL="http://"+editTextServerIP.getText().toString()+"/SalineLevel/";
                new CheckLogin().execute();
            }
        });
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;
        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            params.add(new BasicNameValuePair("email", editTextEmail.getText().toString()));
            params.add(new BasicNameValuePair("password", editTextPassword.getText().toString()));
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject = jsonParser.makeHttpRequest(ServerUtility.url_login_info(), "POST", params);
            try {

                message = jsonObject.getString("message");
                flag = jsonObject.has("success");
                ServerUtility.nurse_id = jsonObject.getString("nurse_id");
                ServerUtility.ward_id = jsonObject.getString("ward_id");

                ServerUtility.nurse_mobile = jsonObject.getString("mobile");
                ServerUtility.nurse_email = editTextEmail.getText().toString();
                ServerUtility.nurse_salary = jsonObject.getString("salary");
                ServerUtility.nurse_joining = jsonObject.getString("joiningdate");
                ServerUtility.nurse_education = jsonObject.getString("education");
                ServerUtility.nurse_name = jsonObject.getString("name");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            if (flag) {
                startActivity(new Intent(LoginActivity.this, NurseHome.class));
                finish();
            }
        }
    }

}
