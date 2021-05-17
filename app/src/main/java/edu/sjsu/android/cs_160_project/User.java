package edu.sjsu.android.cs_160_project;

public class User {
    private String fullName;
    private String email;
    private String restaurantID;
    private int type;


    public User(String name, String email, int type, String restaurantID)
    {
        this.fullName = name;
        this.email = email;
        this.type = type;
        this.restaurantID = restaurantID;
    }

    public User()  // needed to load information back from cloud database
    {

    }

    // Getters and Setters
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
