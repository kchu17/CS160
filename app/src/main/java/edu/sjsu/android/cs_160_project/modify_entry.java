package edu.sjsu.android.cs_160_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link modify_entry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class modify_entry extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AdminMainActivity activity;
    private MenuEntry entry;

    public modify_entry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment modify_entry.
     */
    // TODO: Rename and change types and number of parameters
    public static modify_entry newInstance(String param1, String param2) {
        modify_entry fragment = new modify_entry();
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
        View view = inflater.inflate(R.layout.fragment_modify_entry, container, false);
        activity = (AdminMainActivity) getActivity();
        entry = activity.getEntry();
        EditText editTitle = (EditText) view.findViewById(R.id.editTitle);
        editTitle.setText(entry.getName());
        EditText editDescription = view.findViewById(R.id.editDesc);
        editDescription.setText(entry.getDescription());
        EditText editPrice = view.findViewById(R.id.editPrice);
        editPrice.setText(Double.toString(entry.getPrice()));
        EditText imageURL = view.findViewById(R.id.editURL);
        imageURL.setText(entry.getImage_url());
        Switch switchShow = view.findViewById(R.id.switchShow);
        switchShow.setChecked(entry.getShow());
        return view;
    }
}