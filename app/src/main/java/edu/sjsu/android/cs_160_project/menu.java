package edu.sjsu.android.cs_160_project;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menu extends Fragment implements MenuAdapter.onCartMenuListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // Declaring  variables
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuEntry> menu;
    private MainActivity activity;

    public menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu.
     */
    // TODO: Rename and change types and number of parameters
    public static menu newInstance(String param1, String param2) {
        menu fragment = new menu();
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
            Log.d("Passing Info", "onCreateView: " + getArguments().getString("RestaurantName"));
        }
        Log.d("MenuEntry,", "onCreate:  onCreate" );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("MenuEntry,,", "onCreateView:  onCreateView" );
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        activity = (MainActivity) getActivity();
        if (activity.getMenu() != null)
        {
            menu = activity.getMenu();

        }
        else
        {
            menu = new ArrayList<MenuEntry>();
        }

        onRowClick(0);
        setUpRecyclerView(view);
/*
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference ref = firebaseFirestore.collection("restaurants").document("38LjguRoduFlv7Ne7sWn");

        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    Restaurant temp = task.getResult().toObject(Restaurant.class);
                    //menu = temp.getMenu();
                    setUpRecyclerView(view);
                    onRowClick(0);

                }
            }
        });*/

        return view;
    }

    private void setUpRecyclerView(View view)
    {
        mRecyclerView = view.findViewById(R.id.Myrecycler);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter<MenuAdapter.ViewHolder> adapter = new MenuAdapter(menu,this);
        mRecyclerView.setAdapter(adapter);
    }

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

    @Override
    public void onCartMenuClick(int position) {
        activity.addSingleOrder(menu.get(position));
        Toast.makeText(getActivity(), "Item Added To Cart!", Toast.LENGTH_SHORT).show();
    }


    /*
    private class MenuViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView menuEntryImage;
        private TextView menuEntryName;
        private TextView menuEntryDescription;

        public MenuViewHolder(@NonNull View itemView) {

            super(itemView);
            menuEntryImage  = itemView.findViewById(R.id.menuEntryImage);
            menuEntryName = itemView.findViewById(R.id.menuEntryName);
            menuEntryDescription = itemView.findViewById(R.id.menuEntryDescription);
        }
    }*/


}