package com.thephoenixzone.phoenixzone.nurselogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public PatientAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Patient object) {
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final PatientHolder patientHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.single_patient, parent, false);
            patientHolder = new PatientHolder();
            patientHolder.txtName = (TextView) row.findViewById(R.id.txtName);
            patientHolder.txtEmail = (TextView) row.findViewById(R.id.txtEmail);
            patientHolder.txtMobile = (TextView) row.findViewById(R.id.txtMobile);
            patientHolder.txtAddress = (TextView) row.findViewById(R.id.txtAddress);

            row.setTag(patientHolder);

        } else {
            patientHolder = (PatientHolder) row.getTag();
        }
        try {
            Patient historyInfo = (Patient) getItem(position);
            patientHolder.txtAddress.setText(historyInfo.getAddress());
            patientHolder.txtName.setText(historyInfo.getName());
            patientHolder.txtEmail.setText(historyInfo.getEmail());
            patientHolder.txtMobile.setText(historyInfo.getMobile());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }


    public static class PatientHolder {
        TextView txtName, txtMobile, txtEmail, txtAddress;
    }

}
