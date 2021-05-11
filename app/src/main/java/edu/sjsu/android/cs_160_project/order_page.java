package edu.sjsu.android.cs_160_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.StructuredQuery;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link order_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class order_page extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "onClientOrderPage";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<MenuEntry> orders;
    private RecyclerView mRecyclerView;
    private TextView amountTxt;
    private double amountNumeric;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private Button placeOrderBtn;
    public order_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment order_page.
     */
    // TODO: Rename and change types and number of parameters
    public static order_page newInstance(String param1, String param2) {
        order_page fragment = new order_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_page, container, false);
        amountTxt = view.findViewById(R.id.numberTotal);
        amountNumeric = 0.0;
        placeOrderBtn = view.findViewById(R.id.placeOrderBtn);
        placeOrderBtn.setOnClickListener(this);


        MainActivity activity = (MainActivity) getActivity();
        ArrayList<MenuEntry> temp_orders  = activity.getOrders();

        if (temp_orders != null)
        {
            orders = temp_orders;
        }
        else
        {
            orders = new ArrayList<MenuEntry>();
        }

        for (int i = 0; i < orders.size(); i++)
        {
            amountNumeric += orders.get(i).getPrice();
        }

        amountTxt.setText("$ " + decimalFormat.format(amountNumeric));
        activity.setAmount("$" + decimalFormat.format(amountNumeric));


        setUpRecyclerView(view);
        return view;
    }

    private void setUpRecyclerView(View view)
    {
        mRecyclerView = view.findViewById(R.id.ordersRecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> adapter = new CustomerOrderAdapter(orders);
        mRecyclerView.setAdapter(adapter);
    }

    public void placeOrder()
    {
        Log.d(TAG, "onPlaceOrder: Placing order...");
        MainActivity activity = (MainActivity)getActivity();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        OrderModel order =  activity.getOrderTracker();
        order.setTimestamp(Timestamp.now());
        String documentID = order.getRestaurantID();

        if (documentID == null || documentID.length() == 0)
        {return;}

        db.collection("restaurants").document(documentID).collection("Orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: Added order successfully");
                    Toast.makeText(getActivity(), "Order has been placed!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d(TAG, "onComplete: Failed to save order in the database");
                    Toast.makeText(getActivity(), "Failed to place order!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.placeOrderBtn:
                placeOrder();
                break;
            default: break;

        }
    }
}