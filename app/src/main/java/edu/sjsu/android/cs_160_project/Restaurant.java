package edu.sjsu.android.cs_160_project;



import android.net.Uri;
import android.widget.ImageView;

import com.google.type.LatLng;

import java.util.ArrayList;

public class Restaurant {

    private String restaurantName;
    private double rating;
    private String type;
    private String imageUri;
    private String restaurantAddress;
    private double longitude;
    private double latitude;
    ArrayList<MenuEntry> menu;

    public Restaurant(String input_name, String input_type, double input_rating, String input_imageUri,  ArrayList<MenuEntry> input_menu)
    {
        restaurantName = input_name;
        type = input_type;
        rating = input_rating;
        imageUri = input_imageUri;
        menu = input_menu;
    }

    public Restaurant(String input_name, String input_type, double input_rating, String input_imageUri, ArrayList<MenuEntry> input_menu, double longitude, double latitude, String address)
    {
        restaurantName = input_name;
        type = input_type;
        rating = input_rating;
        imageUri = input_imageUri;
        menu = input_menu;
        this.longitude = longitude;
        this.latitude = latitude;
        restaurantAddress = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Restaurant() // for firebase
    {

    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }


    public ArrayList<MenuEntry> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuEntry> menu) {
        this.menu = menu;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    /*
    public ImageView getImageView()
    {

    }*/
}
