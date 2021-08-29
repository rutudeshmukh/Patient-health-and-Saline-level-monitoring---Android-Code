package com.thephoenixzone.phoenixzone.nurselogin;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHistoryFragment extends Fragment {
    public static String patient_name = "";
    public static String patient_id = "";
    ListView history_list;

    HistoryAdapter historyAdapter;
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;

    public PatientHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_history, container, false);
        TextView txtName = (TextView) view.findViewById(R.id.txtPatientName);
        txtName.setText(patient_name);
        history_list = (ListView) view.findViewById(R.id.patient_history);
        new GetPatientHistory().execute();
        return view;
    }

    public class GetPatientHistory extends AsyncTask<String, String, String> {
        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            params.add(new BasicNameValuePair("id", patient_id));
            progressDialog = new ProgressDialog(PatientHistoryFragment.this.getContext());
            progressDialog.setMessage("Please wait..");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_patient_history(), "POST", params);
            try {
                historyAdapter = new HistoryAdapter(PatientHistoryFragment.this.getContext(), R.layout.single_history);
                JSONArray array = object.getJSONArray("PatientInfo");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    PatientHistory patientHistory = new PatientHistory();
                    patientHistory.setAdmitted_on(object1.getString("admitted_on"));
                    patientHistory.setNurse_name(object1.getString("name"));
                    patientHistory.setWard_number(object1.getString("ward_id"));
                    patientHistory.setBed_number(object1.getString("bed_number"));
                    patientHistory.setDischarge_on(object1.getString("discharge_on"));
                    patientHistory.setDescription(object1.getString("description"));
                    historyAdapter.add(patientHistory);

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
            history_list.setAdapter(historyAdapter);
        }
    }

}
