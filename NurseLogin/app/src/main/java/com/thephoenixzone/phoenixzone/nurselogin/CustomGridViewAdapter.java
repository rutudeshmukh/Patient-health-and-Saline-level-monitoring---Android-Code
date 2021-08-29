package com.thephoenixzone.phoenixzone.nurselogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomGridViewAdapter extends ArrayAdapter<Item> {

    Context context;
    int layoutResourceId;
    ArrayList<Item> data = new ArrayList<Item>();
    boolean flag = false;
    int id = -1;

    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        ViewBedsFragment.selectedSeat = id;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        final RecordHolder holder;
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            row.setTag(holder);

        } else {
            row = convertView;
            holder = (RecordHolder) row.getTag();
        }

        final Item item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        Bitmap myPictureBitmap = item.getImage();
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            myPictureBitmap = Bitmap.createScaledBitmap(myPictureBitmap, 24, 24, true);
        }
        holder.imageItem.setImageBitmap(myPictureBitmap);
        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isSelected()) {

                    //update admitted patient information
                    id = Integer.parseInt(item.title);

                    ViewBedsFragment.selectedSeat = id;
                    Intent intent = new Intent(context, ViewAdmittedPatient.class);
                    context.startActivity(intent);
                } else {
                    id = Integer.parseInt(item.title);

                    ViewBedsFragment.selectedSeat = id;
                    Intent intent = new Intent(context, AdmitPatient.class);
                    context.startActivity(intent);
                }
            }
        });

        return row;

    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

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

}