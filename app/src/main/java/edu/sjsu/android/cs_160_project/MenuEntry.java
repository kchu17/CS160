package edu.sjsu.android.cs_160_project;

public class MenuEntry {

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
}
