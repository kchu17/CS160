package edu.sjsu.android.cs_160_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.internal.InternalTokenProvider;

public class CustomRestaurantTypeDialog extends AppCompatDialogFragment
{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater =   getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.registration_type_dialog_layout, null);
        builder.setView(view);

        Button userAccountBtn = view.findViewById(R.id.userAccountButton);
        Button restaurantAccountBtn = view.findViewById(R.id.restaurantAccountBtn);

        userAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterPage.class);
                startActivity(intent);
            }
        });

        restaurantAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantRegistrationActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }
}
