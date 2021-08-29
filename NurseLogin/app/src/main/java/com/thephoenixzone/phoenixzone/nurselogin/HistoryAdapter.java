package com.thephoenixzone.phoenixzone.nurselogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public HistoryAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(PatientHistory object) {
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
        final HistoryHolder historyHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.single_history, parent, false);
                historyHolder = new HistoryHolder();
            historyHolder.txtAdmitted_on = (TextView) row.findViewById(R.id.txtAdmitted_on);
            historyHolder.txtWardNo = (TextView) row.findViewById(R.id.txtWardNo);
            historyHolder.txtNurseName = (TextView) row.findViewById(R.id.txtNurseName);
            historyHolder.txtBedNo = (TextView) row.findViewById(R.id.txtBedNo);
            historyHolder.txtDischarge = (TextView) row.findViewById(R.id.txtDischarge);
            historyHolder.txtDescription = (TextView) row.findViewById(R.id.txtDescription);


            row.setTag(historyHolder);

        } else {
            historyHolder = (HistoryHolder) row.getTag();
        }
        try {
            PatientHistory historyInfo = (PatientHistory) getItem(position);
            historyHolder.txtAdmitted_on.setText(historyInfo.getAdmitted_on());
            historyHolder.txtWardNo.setText(historyInfo.getWard_number());
            historyHolder.txtNurseName.setText(historyInfo.getNurse_name());
            historyHolder.txtBedNo.setText(historyInfo.getBed_number());
            historyHolder.txtDischarge.setText(historyInfo.getDischarge_on());
            historyHolder.txtDescription.setText(historyInfo.getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }


    public static class HistoryHolder {
        TextView txtAdmitted_on, txtWardNo, txtNurseName, txtBedNo, txtDischarge, txtDescription;
    }
}
