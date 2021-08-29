package com.thephoenixzone.phoenixzone.nurselogin;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPatient extends Fragment {

    PatientAdapter patientAdapter;
    ListView listView;
    JSONParser jsonParser = new JSONParser();
    ProgressDialog progressDialog;

    public ViewPatient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_patient, container, false);
        listView = (ListView) view.findViewById(R.id.patient_list);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Patient patient = (Patient) patientAdapter.getItem(i);
               PatientHistoryFragment.patient_name = patient.getName();
               PatientHistoryFragment.patient_id = patient.getPatient_id();
               PatientHistoryFragment patientHistoryFragment = new PatientHistoryFragment();
               FragmentManager manager = getFragmentManager();
               manager.beginTransaction().replace(R.id.content_home, patientHistoryFragment, patientHistoryFragment.getTag()).commit();

           }
       });
        new GetPatientInfo().execute();
        return view;
    }

    public class GetPatientInfo extends AsyncTask<String, String, String> {

        List<NameValuePair> params = new ArrayList<>();
        boolean flag = false;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(ViewPatient.this.getContext());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object = jsonParser.makeHttpRequest(ServerUtility.url_get_patient_info(), "POST", params);
            try {
                patientAdapter = new PatientAdapter(ViewPatient.this.getContext(), R.layout.single_patient);
                JSONArray jsonArray = object.getJSONArray("PatientInfo");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    Patient patient = new Patient();
                    patient.setPatient_id(object1.getString("patient_id"));
                    patient.setName(object1.getString("name"));
                    patient.setAddress(object1.getString("address"));
                    patient.setMobile(object1.getString("mobile"));
                    patient.setEmail(object1.getString("email"));
                    patientAdapter.add(patient);
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
            listView.setAdapter(patientAdapter);

        }
    }


}
