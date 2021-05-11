package edu.sjsu.android.cs_160_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button add;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddToMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddToMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static AddToMenu newInstance(String param1, String param2) {
        AddToMenu fragment = new AddToMenu();
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
        View view = inflater.inflate(R.layout.fragment_add_to_menu, container, false);


        add = view.findViewById(R.id.modify_firestore_entry);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTitle = view.findViewById(R.id.editTitle);
                EditText editDescription = view.findViewById(R.id.editDesc);
                EditText editPrice = view.findViewById(R.id.editPrice);
                EditText imageURL = view.findViewById(R.id.editURL);
                Switch switchShow = view.findViewById(R.id.switchShow);
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

                FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                DocumentReference ref = db.collection("restaurants").document(documentId);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Restaurant temp = Objects.requireNonNull(task.getResult()).toObject(Restaurant.class);
                            assert temp != null;
                            ArrayList<MenuEntry> menu = temp.getMenu();
                            menu.add(newEntry);
                            ref.update("menu", menu);
                            Toast.makeText(getActivity(), "Successfully saved to you menu!" , Toast.LENGTH_LONG).show();
                        }
                    }
                });


                Navigation.findNavController(v).navigate(R.id.restaurant_menu);
                Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT);
//                Fragment fragment = new restaurant_menu();
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.rest_frag, fragment);
//                fragmentTransaction.commit();
            }
        });

        return view;
    }
}