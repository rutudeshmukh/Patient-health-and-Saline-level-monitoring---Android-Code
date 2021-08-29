package com.thephoenixzone.phoenixzone.nurselogin;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
public class ViewBedsFragment extends Fragment {

    GridView gridView;
    JSONParser jsonParser = new JSONParser();
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
    ArrayList<Integer> seat_numbers = new ArrayList<>();
    Context ctx;
    TextView txtBedNumber;
    public static int selectedSeat = -1;

    public ViewBedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_beds, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView1);
        txtBedNumber = (TextView) view.findViewById(R.id.txtBedNumber);
        txtBedNumber.setText(ServerUtility.ward_id );
        //get current context
        ctx = getContext();
        new GetCotNumbers().execute();
        return view;
    }

    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat || drawable instanceof VectorDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    void displaySeats(int total_seat, ArrayList<Integer> seats) {
        Bitmap seatIcon = getBitmapFromDrawable(ctx, R.drawable.ic_event_seat_black_24dp);
        Bitmap seatIconSelected = getBitmapFromDrawable(ctx, R.drawable.ic_event_seat_red_24dp);
//        gridView = (GridView) view.findViewById(R.id.gridView1);
        gridArray = new ArrayList<Item>();
        gridView.setVisibility(View.VISIBLE);
        boolean flag = false;
        for (int i = 1; i <= total_seat; i++) {
            if (seats.contains(i)) {
                flag = true;
                gridArray.add(new Item(seatIconSelected, "" + i, flag));
            } else {
                flag = false;
                gridArray.add(new Item(seatIcon, "" + i, flag));
            }


        }
        customGridAdapter = new CustomGridViewAdapter(ctx, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
        //gridView.notifyAll();
    }

    public class GetCotNumbers extends AsyncTask<String, String, String> {
        List<NameValuePair> params = new ArrayList<>();
        int cot_count = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String id = ServerUtility.ward_id;
            params.add(new BasicNameValuePair("ward_id", id));
        }


        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject = jsonParser.makeHttpRequest(ServerUtility.url_get_seats(), "POST", params);
            try {
                seat_numbers = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("seat_number");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    seat_numbers.add(object.getInt("seat_id"));
                }
                cot_count = jsonObject.getInt("cot_count");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            displaySeats(cot_count, seat_numbers);
        }

    }
}
