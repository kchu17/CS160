package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "OnMainActivity";
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ArrayList<MenuEntry> menu = null;
    private ArrayList<MenuEntry> orders = new ArrayList<MenuEntry>();
    private OrderModel orderTracker;
    private String user_name;
    private String user_email;

    //private OrderModel orders = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        Intent intent = getIntent();
        user_name = intent.getExtras().getString(loginPage.EXTRA_NAME);
        user_email = intent.getExtras().getString(loginPage.EXTRA_EMAIL);
        Log.d(TAG, "onCreate: User Name Received: " + user_name);
        Log.d(TAG, "onCreate: User Email Recieved: " + user_email);

        orderTracker = new OrderModel(new ArrayList<MenuEntry>(), -1, user_name, user_email, "");
        Log.d(TAG, "onCreate: created Order tracker");



    }

    public void setRestaurantId(String restaurantId)
    {
        orderTracker.setRestaurantID(restaurantId);
        Log.d(TAG, "setRestaurantId: set restaurant id as: " + restaurantId);
    }
    public String getRestaurantId()
    {
        return orderTracker.getRestaurantID();
    }

    public void setTableNumber(int tableNumber)
    {
        orderTracker.setTableNumber(tableNumber);
        Log.d(TAG, "setTableNumber: set table number to: " + tableNumber);
    }
    public void setMenu(ArrayList<MenuEntry> inputMenu)
    {
        this.menu = inputMenu;
    }
    public ArrayList<MenuEntry> getMenu()
    {
        return menu;
    }



    public ArrayList<MenuEntry> getOrders() {
        //return orders;
        return orderTracker.getOrders();

    }


    public OrderModel getOrderTracker()
    {
        return orderTracker;
    }


/*
    public void setOrders(ArrayList<MenuEntry> orders) {
        this.orders = orders;
    }

 */
    public void addSingleOrder(MenuEntry singleOrder)
    {
        //orders.add(singleOrder);
        orderTracker.getOrders().add(singleOrder);
    }
}