package edu.sjsu.android.cs_160_project;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link find_restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class find_restaurant extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;

    // dialog

    private FirestoreRecyclerAdapter adapter;
    private ArrayList<ArrayList<MenuEntry>>menus;
    private ArrayList<String> documentIds;

    public find_restaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment find_restaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static find_restaurant newInstance(String param1, String param2) {
        find_restaurant fragment = new find_restaurant();
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
                             Bundle savedInstanceState)
    {


        menus = new ArrayList<ArrayList<MenuEntry>>();
        documentIds = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_restaurant, container, false);

        db = FirebaseFirestore.getInstance(); // getting database
        Query query = db.collection("restaurants"); // preparing query to get information from database
        recyclerView = view.findViewById(R.id.Myrecycler);

        // Recycler Options
        FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(query, Restaurant.class).build();

        // Creating Recycler Adapter
         adapter = new FirestoreRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
            @NonNull
            @Override
            public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = inflater.from(parent.getContext()) .inflate(R.layout.find_restaurant, parent, false);
                return new RestaurantViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position, @NonNull Restaurant model) {

                holder.restaurantName.setText(model.getRestaurantName());
                holder.restaurantType.setText(model.getType());
                holder.restaurantRating.setText(Double.toString(model.getRating()));
                if (model.getMenu() != null && !model.getMenu().isEmpty())
                {
                    Log.d("MenuEntry,", "onCreateView:  Added menu for " + model.getRestaurantName());
                    menus.add(model.getMenu());
                    documentIds.add(getSnapshots().getSnapshot(position).getId());

                }
                else
                {
                    menus.add(new ArrayList<MenuEntry>());
                    documentIds.add("");
                }


                Glide.with(getContext()).load(Uri.parse(model.getImageUri())).into(holder.restaurantImage);



            }
        };


        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening(); // will listen for any new files added.
    }






    private class RestaurantViewHolder extends RecyclerView.ViewHolder  {

        private TextView restaurantName;
        private TextView restaurantRating;
        private TextView restaurantType;
        private ImageView restaurantImage;
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantRating = itemView.findViewById(R.id.restaurantRating);
            restaurantType = itemView.findViewById(R.id.restaurantType);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        Toast.makeText(getActivity(), "Clicked row # " + getAdapterPosition() , Toast.LENGTH_SHORT).show();



                            // TODO: check if I have to check if its null before hand
                            MainActivity activity = (MainActivity) getActivity();
                            activity.setMenu(menus.get(getAdapterPosition()));
                            activity.setRestaurantId(documentIds.get(getAdapterPosition()));



                        final NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.menu);
                    }

                }
            });


        }


    }


}