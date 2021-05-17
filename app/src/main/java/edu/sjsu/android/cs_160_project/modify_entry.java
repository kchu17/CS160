package edu.sjsu.android.cs_160_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

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

        Button modify = view.findViewById(R.id.modify_firestore_entry);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                String image_url = imageURL.getText().toString().trim();
                double price = 0;
                if (!editPrice.getText().toString().trim().isEmpty())
                    price = Double.parseDouble(editPrice.getText().toString().trim());
                boolean show = switchShow.isChecked();
                if (name.isEmpty())
                {
                    editTitle.setError("Title is required!");
                    editTitle.requestFocus();
                    return;
                }
                if (description.isEmpty())
                {
                    editDescription.setError("Description is required!");
                    editDescription.requestFocus();
                    return;
                }
                if (image_url.isEmpty())
                {
                    editTitle.setError("Image url is required!");
                    editTitle.requestFocus();
                    return;
                }
                if (Double.toString(price).isEmpty())
                {
                    editTitle.setError("Price cannot be empty!");
                    editTitle.requestFocus();
                    return;
                } else if (price < 0) {
                    editTitle.setError("Price cannot be negative!");
                    editTitle.requestFocus();
                    return;
                }
                MenuEntry newEntry = new MenuEntry(name, description, price, image_url, show);


                AdminMainActivity activity = (AdminMainActivity)getActivity();
                String documentId;
                if (activity.getRestaurantID() == null)
                {
                    documentId = "38LjguRoduFlv7Ne7sWn";
                }
                else
                {
                    documentId = activity.getRestaurantID();
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference ref = db.collection("restaurants").document(documentId);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Restaurant temp = Objects.requireNonNull(task.getResult()).toObject(Restaurant.class);
                            assert temp != null;
                            ArrayList<MenuEntry> menu = temp.getMenu();
                            menu.remove(activity.getPosition());
                            menu.add(newEntry);
                            ref.update("menu", menu);
                            Toast.makeText(getActivity(), "Successfully saved to you menu!" , Toast.LENGTH_LONG).show();

                            Navigation.findNavController(v).navigate(R.id.restaurant_menu);
                            Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });

        Button delete = (Button) view.findViewById(R.id.removeEntry);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AdminMainActivity activity = (AdminMainActivity)getActivity();
                String documentId;
                if (activity.getRestaurantID() == null)
                {
                    documentId = "38LjguRoduFlv7Ne7sWn";
                }
                else
                {
                    documentId = activity.getRestaurantID();
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference ref = db.collection("restaurants").document(documentId);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Restaurant temp = Objects.requireNonNull(task.getResult()).toObject(Restaurant.class);
                            assert temp != null;
                            ArrayList<MenuEntry> menu = temp.getMenu();
                            menu.remove(activity.getPosition());
                            ref.update("menu", menu);
                            Toast.makeText(getActivity(), "Successfully saved to you menu!" , Toast.LENGTH_LONG).show();

                            Navigation.findNavController(v).navigate(R.id.restaurant_menu);
                            Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
        return view;
    }
}