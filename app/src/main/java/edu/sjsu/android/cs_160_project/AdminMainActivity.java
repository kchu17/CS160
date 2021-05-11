package edu.sjsu.android.cs_160_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainActivity extends AppCompatActivity {

    public final static String ORDER_MODEL_KEY = "edu.sjsu.android.cs_160_project.order";
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        bottomNavigationView  = findViewById(R.id.bottomNavigationView2);
        navController = Navigation.findNavController(this, R.id.fragment2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }



    public void goToActivityFromOrders(OrderModel orderModel)
    {
        Bundle extras = new Bundle();
        extras.putParcelable(ORDER_MODEL_KEY, orderModel);
        Intent intent = new Intent(AdminMainActivity.this, AdminOrderDetails.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}