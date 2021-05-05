package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ArrayList<MenuEntry> menu = null;
    private ArrayList<MenuEntry> orders = new ArrayList<MenuEntry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



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
        return orders;
    }

    public void setOrders(ArrayList<MenuEntry> orders) {
        this.orders = orders;
    }
    public void addSingleOrder(MenuEntry singleOrder)
    {
        orders.add(singleOrder);
    }
}