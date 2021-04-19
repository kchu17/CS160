package edu.sjsu.android.cs_160_project;



import java.util.ArrayList;

public class Restaurant {

    private String restaurantName;
    private double rating;
    private String type;
    private int image;
    ArrayList<MenuEntry> menu;

    public Restaurant(String input_name, String input_type, double input_rating, int input_image, ArrayList<MenuEntry> input_menu)
    {
        restaurantName = input_name;
        type = input_type;
        rating = input_rating;
        image = input_image;
        menu = input_menu;
    }

    public ArrayList<MenuEntry> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuEntry> menu) {
        this.menu = menu;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
}
