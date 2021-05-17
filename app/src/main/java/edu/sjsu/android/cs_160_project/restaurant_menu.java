package edu.sjsu.android.cs_160_project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class restaurant_menu extends Fragment {
    private Button newItem;
    private List<MenuEntry>menu;
    public static final String TAG = "OnRestaurantMenu";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        AdminMainActivity activity = (AdminMainActivity) getActivity();
        String documentId;
        if (activity.getRestaurantID() == null)
        {
            documentId = "38LjguRoduFlv7Ne7sWn";
        }
        else
        {
            documentId = activity.getRestaurantID();
        }

        Log.d(TAG, "onCreateView: Document ID = " + documentId);
        DocumentReference ref = db.collection("restaurants").document(documentId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant temp = Objects.requireNonNull(task.getResult()).toObject(Restaurant.class);
                    assert temp != null;
                    menu = temp.getMenu();
                   // Log.d("testing", "On Complete: name: " + menu.get(0).getName() + "price: " + menu.get(0).getPrice());
                    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                    HelperAdapter helperAdapter = new HelperAdapter(menu, getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(helperAdapter);
                }
            }
        });

        // Inflate the layout for this fragment


        newItem = view.findViewById(R.id.add_new_item);

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.addToMenu);
//                Fragment fragment = new AddToMenu();
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.rest_frag, fragment);
//                fragmentTransaction.commit();
            }
        });

        return view;
    }
}