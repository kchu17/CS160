package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    public final static String ORDER_MODEL_KEY = "edu.sjsu.android.cs_160_project.order";
    public final static int ORDER_DETAIL_REQUEST = 20;
    private final static String TAG = "OnAdminMain";
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private ProgressBar progressBar;

    private String restaurantID;
    int usedIndex;
    String orderID;

    FirebaseFirestore db;

    private MenuEntry entry;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        bottomNavigationView  = findViewById(R.id.bottomNavigationView2);
        navController = Navigation.findNavController(this, R.id.fragment2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        progressBar = findViewById(R.id.adminProgresBar);

        usedIndex = -1;
        restaurantID = "";
        orderID = "";
        db = FirebaseFirestore.getInstance();

        Bundle arguments = getIntent().getExtras();

        if (arguments != null)
        {
            if (arguments.getString(loginPage.RESTAURANT_ID) != null)
            {
                restaurantID = arguments.getString(loginPage.RESTAURANT_ID);
                Log.d(TAG, "onCreate: Update restaurant id to: " + restaurantID);
            }
        }

    }


    public String getRestaurantID(){
        Log.d(TAG, "getRestaurantID: called ");
        return restaurantID;
    }

    public void goToActivityFromOrders(OrderModel orderModel, int position, String orderID)
    {
        Bundle extras = new Bundle();
        extras.putParcelable(ORDER_MODEL_KEY, orderModel);
        Intent intent = new Intent(AdminMainActivity.this, AdminOrderDetails.class);
        intent.putExtras(extras);
        usedIndex = position;
        this.orderID = orderID;
        startActivityForResult(intent, ORDER_DETAIL_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ORDER_DETAIL_REQUEST)
        {
            if (resultCode == RESULT_OK) {  // could be RESULT_OK, RESULT_CANCELLED, RESULT_FIRST_USER (for defining your own result codes)

                Log.d(TAG, "onActivityResult: Recieved response from order details");
                Bundle budle = data.getExtras();
                OrderModel temp = budle.getParcelable(AdminOrderDetails.REPLY_KEY);
                //Log.d(TAG, "onActivityResult: customer anem = "  + temp.getCustomerName());

                DocumentReference docRef = db.collection("restaurants").document(restaurantID).collection("Orders").document(orderID);

                if (temp != null)
                {
                    Log.d(TAG, "onActivityResult: Updating status of order to database");
                    progressBar.setVisibility(View.VISIBLE);
                    docRef.update("orderStatus", temp.getOrderStatus()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(AdminMainActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(AdminMainActivity.this, "Failed to save changes!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



            }
        }
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void setEntry(MenuEntry inputEntry)
    {
        this.entry = inputEntry;
    }
    public MenuEntry getEntry()
    {
        return entry;
    }

}