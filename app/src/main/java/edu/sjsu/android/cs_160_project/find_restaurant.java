package edu.sjsu.android.cs_160_project;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link find_restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class find_restaurant extends Fragment implements FindRestaurantsAdapter.OnRowListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

    // dialog


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

        ArrayList<Restaurant> input = new ArrayList<>();


        /*
        input.add(new Restaurant("Taqueria los Gallos", "Mexican Food, Burritos, Tacos", 4.9, R.drawable.taqueria_los_gallos));
        input.add(new Restaurant("Creamery N Joy", "Ice Cream, Shakes, Cookies", 4.7, R.drawable.ice_cream_restaurant));
        input.add(new Restaurant("Toki", "Asian Food, Sushi", 4.8, R.drawable.sushi_restaurant));;
        input.add(new Restaurant("Nations", "American Classics, Burger, Hot Dogs", 4.7, R.drawable.burger_restaurant));
        input.add(new Restaurant("Milk Tea Lab", "Boba, Tea, Shakes", 4.9, R.drawable.boba_restaurant));*/


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_restaurant, container, false);
        recyclerView = view.findViewById(R.id.Myrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.Adapter<FindRestaurantsAdapter.ViewHolder> mAdapter = new FindRestaurantsAdapter(input, this);
        recyclerView.setAdapter(mAdapter);







        return view;
    }


    @Override
    public void onRowClick(int position) {
        Toast.makeText(getActivity(), "You clicked row #" + Integer.toString(position), Toast.LENGTH_SHORT).show();

        // setting up dialog
        final Dialog dialog = new Dialog(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View custom_dialog = inflater.inflate(R.layout.table_number_layout, null);
        Button readyBtn = custom_dialog.findViewById(R.id.readyButton);
        Button cancelBtn = custom_dialog.findViewById(R.id.cancelButton);

        // setting action listeners
        readyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pressed Ready!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pressed Cancel!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.setContentView(custom_dialog);
        dialog.show();
    }
}