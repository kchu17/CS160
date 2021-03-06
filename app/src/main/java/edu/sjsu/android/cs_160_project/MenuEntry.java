package edu.sjsu.android.cs_160_project;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuEntry implements Parcelable {

    String name;
    String description;
    String image_url;
    double price;
    boolean show;

    public MenuEntry(String name, String description, double price, String image_url, boolean show) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.show = show;
    }
    public MenuEntry()
    {

    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String imageUri) {
        this.image_url = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.image_url);
        dest.writeDouble(this.price);
        dest.writeByte(this.show ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.description = source.readString();
        this.image_url = source.readString();
        this.price = source.readDouble();
        this.show = source.readByte() != 0;
    }

    protected MenuEntry(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.image_url = in.readString();
        this.price = in.readDouble();
        this.show = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MenuEntry> CREATOR = new Parcelable.Creator<MenuEntry>() {
        @Override
        public MenuEntry createFromParcel(Parcel source) {
            return new MenuEntry(source);
        }

        @Override
        public MenuEntry[] newArray(int size) {
            return new MenuEntry[size];
        }
    };
}
