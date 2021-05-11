package edu.sjsu.android.cs_160_project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class OrderModel implements Parcelable {
    private ArrayList<MenuEntry> orders;
    private int tableNumber;
    private String customerName;
    private String customerEmail;
    private String restaurantID;
    private int orderStatus;
    private Timestamp timestamp;
    private String totalAmount;

    public OrderModel(ArrayList<MenuEntry> order, int tableNumber, String customerName, String customerEmail, String restaurantID, String totalAmount) {
        this.orders = order;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.restaurantID = restaurantID;
        this.orderStatus = OrderStatus.ORDERED;
        this.totalAmount = totalAmount;
    }


    public OrderModel() // for firebase
    {

    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }


    public ArrayList<MenuEntry> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<MenuEntry> orders) {
        this.orders = orders;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.orders);
        dest.writeInt(this.tableNumber);
        dest.writeString(this.customerName);
        dest.writeString(this.customerEmail);
        dest.writeString(this.restaurantID);
        dest.writeInt(this.orderStatus);
        dest.writeParcelable(this.timestamp, flags);
        dest.writeString(this.totalAmount);
    }

    public void readFromParcel(Parcel source) {
        this.orders = source.createTypedArrayList(MenuEntry.CREATOR);
        this.tableNumber = source.readInt();
        this.customerName = source.readString();
        this.customerEmail = source.readString();
        this.restaurantID = source.readString();
        this.orderStatus = source.readInt();
        this.timestamp = source.readParcelable(Timestamp.class.getClassLoader());
        this.totalAmount = source.readString();
    }

    protected OrderModel(Parcel in) {
        this.orders = in.createTypedArrayList(MenuEntry.CREATOR);
        this.tableNumber = in.readInt();
        this.customerName = in.readString();
        this.customerEmail = in.readString();
        this.restaurantID = in.readString();
        this.orderStatus = in.readInt();
        this.timestamp = in.readParcelable(Timestamp.class.getClassLoader());
        this.totalAmount = in.readString();
    }

    public static final Parcelable.Creator<OrderModel> CREATOR = new Parcelable.Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel source) {
            return new OrderModel(source);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };
}
