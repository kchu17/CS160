package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private EditText enterEmailTxt;
    private Button resetPassBtn;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        enterEmailTxt = findViewById(R.id.enterEmailTxt);

        Intent intent = getIntent();
        Bundle extras = intent.getBundleExtra(loginPage.EXTRA);
        email = extras.getString(loginPage.EXTRA_EMAIL);

        if (!email.isEmpty())
        {
            enterEmailTxt.setText(email);
        }


    }

    public void reset_password(View view) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        email = enterEmailTxt.getText().toString();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(PasswordReset.this, "Email Successfully Sent!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PasswordReset.this, "Could send email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}