package com.thephoenixzone.phoenixzone.nurselogin;

import android.graphics.Bitmap;


public class Item {
    Bitmap image;
    String title;
    boolean isSelected;

    public Item(Bitmap image, String title, boolean isSelected) {
        super();
        this.image = image;
        this.title = title;
        this.isSelected = isSelected;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
