package edu.sjsu.android.cs_160_project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firestore.v1.StructuredQuery;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link adminOrders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adminOrders extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView adminOrdersRV;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;


    public adminOrders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adminOrders.
     */
    // TODO: Rename and change types and number of parameters
    public static adminOrders newInstance(String param1, String param2) {
        adminOrders fragment = new adminOrders();
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
        View view = inflater.inflate(R.layout.fragment_admin_orders, container, false);
        adminOrdersRV = view.findViewById(R.id.adminOrdersRV);
        db = FirebaseFirestore.getInstance();

        //Query
       /* Query query = db.collection("restaurants").document("6rt1UbQGVV0H0dGaXK0J")
                .collection("Orders").whereLessThanOrEqualTo("orderStatus", 11).orderBy("orderStatus")
                .orderBy("timestamp"); // order by timespam*/

        AdminMainActivity activity = (AdminMainActivity) getActivity();
        Query query = db.collection("restaurants").document(activity.getRestaurantID())
                .collection("Orders") .orderBy("orderStatus").whereNotEqualTo("orderStatus", 12);


        // Recycler Options
        FirestoreRecyclerOptions<OrderModel> options = new FirestoreRecyclerOptions.Builder<OrderModel>().setQuery(query, OrderModel.class).build();

        adapter = new FirestoreRecyclerAdapter<OrderModel, OrdersViewHolder>(options) {
            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.admin_orders_single_item, parent, false);
                return new OrdersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull OrderModel model) {
                holder.tableIdentifier.setText(Integer.toString(model.getTableNumber()));
                holder.clientName.setText(model.getCustomerName());
                holder.price.setText(model.getTotalAmount());

                int status = model.getOrderStatus();

                if (status == OrderStatus.IN_PROGRESS)
                {
                    holder.orderStatus.setText("Progress");
                    holder.orderStatus.setBackgroundColor(Color.rgb(255, 87,34));
                }
                else if (status == OrderStatus.DONE)
                {
                    holder.orderStatus.setText("Done");
                    holder.orderStatus.setBackgroundColor(Color.GREEN);
                }
                else
                {
                    holder.orderStatus.setText("Recieved");
                    holder.orderStatus.setBackgroundColor(Color.BLUE);
                }


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "You touched #" + position, Toast.LENGTH_SHORT).show();
                        AdminMainActivity activity = (AdminMainActivity) getActivity();
                        activity.goToActivityFromOrders(model, position, getSnapshots().getSnapshot(position).getId());
                    }
                });

                Timestamp timestamp = model.getTimestamp();
                if (timestamp != null)
                {


                    holder.timeStamp.setText(timestamp.toDate().toString());
                }
                else
                {
                    holder.timeStamp.setText("");
                }


            }
        } ;

        adminOrdersRV.setHasFixedSize(true);
        adminOrdersRV.addItemDecoration(new DividerItemDecoration(adminOrdersRV.getContext(), DividerItemDecoration.VERTICAL));
        adminOrdersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adminOrdersRV.setAdapter(adapter);
        //View Holder class




        return view;
    }

    private class OrdersViewHolder extends  RecyclerView.ViewHolder {
        private TextView tableIdentifier;
        private TextView clientName;
        private TextView timeStamp;
        private TextView price;
        private Button orderStatus;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            tableIdentifier = itemView.findViewById(R.id.tableIdentifierTxt);
            clientName = itemView.findViewById(R.id.clientNameTxt);
            timeStamp = itemView.findViewById(R.id.timestampTxt);
            price = itemView.findViewById(R.id.priceTxt);
            orderStatus = itemView.findViewById(R.id.statusBTN);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}