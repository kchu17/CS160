package edu.sjsu.android.cs_160_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdminOrderDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "OnAdminOrderDet";
    public static final String REPLY_KEY = "edu.sjsu.android.cs_160_project.detailreply";
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    // UI Variables
    private TextView tableNumber;
    private TextView customerNameTxt;
    private TextView customerEmailTxt;
    private TextView orderTotalTxt;
    private Spinner orderStatusSpinner;
    private RecyclerView orderRecyclerView;
    private OrderModel orderRepresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_details);

        tableNumber = findViewById(R.id.tableOrderNumber);
        orderStatusSpinner = findViewById(R.id.orderStatusSpinner);
        orderRecyclerView = findViewById(R.id.adminOrderRecyclerView);
        customerNameTxt = findViewById(R.id.customerNameTxt);
        customerEmailTxt = findViewById(R.id.customerEmailTxt);
        orderTotalTxt = findViewById(R.id.orderTotalTxt);






        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        Double result = 0.0;

        Log.d(TAG, "onCreate: Recieving intent...");

        if (extra != null)
        {
            Log.d(TAG, "onCreate: Extra recieved..");
            orderRepresentation = extra.getParcelable(AdminMainActivity.ORDER_MODEL_KEY);
            if (orderRepresentation != null)
            {
                Log.d(TAG, "onCreate: Order recieved...");
                Log.d(TAG, "onCreate: Table number: " + orderRepresentation.getTableNumber() + " Customer email: " + orderRepresentation.getCustomerEmail());

                tableNumber.setText(Integer.toString(orderRepresentation.getTableNumber()));
                customerNameTxt.setText(orderRepresentation.getCustomerName());
                customerEmailTxt.setText(orderRepresentation.getCustomerEmail());

                result = 0.0;
                for (int i= 0; i < orderRepresentation.getOrders().size(); i ++)
                {
                    result += orderRepresentation.getOrders().get(i).getPrice();
                }
                orderTotalTxt.setText("$ " + decimalFormat.format(result));
            }
            else
            {
                orderRepresentation = new OrderModel(new ArrayList<MenuEntry>(), -1, "Unknown", "Unknown", "Unknown", "$0.0");

            }
        }

        setUpRecyclerView();
        setUpSpinner();

    }

    private void setUpSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.OrderStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderStatusSpinner.setAdapter(adapter);
        orderStatusSpinner.setOnItemSelectedListener(this);
    }

    private void setUpRecyclerView()
    {


        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.addItemDecoration(new DividerItemDecoration(orderRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter<AdminOrderDetailsAdapter.ViewHolder> adapter = new AdminOrderDetailsAdapter(orderRepresentation.getOrders());
        orderRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString().trim();

        Toast.makeText(this, "Option selected = " + text, Toast.LENGTH_SHORT).show();
        if (text.equals("Done"))
        {
            orderRepresentation.setOrderStatus(OrderStatus.DONE);
        }
        else if (text.equals("In Progress"))
        {
            orderRepresentation.setOrderStatus(OrderStatus.IN_PROGRESS);
        }
        else
        {
            orderRepresentation.setOrderStatus(OrderStatus.ORDERED);
        }

        Toast.makeText(this, "Current Status = " + orderRepresentation.getOrderStatus(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // Related to spinner object, no need to implemtn

    }

    public void onUpdateOrder(View view) {
        Intent rIntent = new Intent();
        Bundle extras = new Bundle();
        extras.putParcelable(REPLY_KEY, orderRepresentation);
        rIntent.putExtras(extras);

        setResult(RESULT_OK, rIntent);
        finish();


    }
}