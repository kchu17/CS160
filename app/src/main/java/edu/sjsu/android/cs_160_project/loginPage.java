package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;

import edu.sjsu.android.cs_160_project.databinding.ActivityLoginPageBinding;

public class loginPage extends AppCompatActivity {

    public static final String EXTRA = "edu.sjsu.android.cs_160_project.extra";
    public static final String EXTRA_EMAIL = "edu.sjsu.android.cs_160_project.email";

    private ActivityLoginPageBinding binding;
    private EditText passwordInput;
    private EditText emailInput;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        emailInput = binding.email;
        passwordInput = binding.password;
        progressBar = binding.progressBar;
        mAuth = FirebaseAuth.getInstance();


        // delete this later
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<MenuEntry> menu = new ArrayList<>();
        menu.add(new MenuEntry("Regular Burrito", "beans, fries, and chipts", 10.99, "https://hdlsd"));
        Restaurant restaurant = new Restaurant("Taqueria Los Gallos", "Mexican Food, Burritos, Tacos",4.9, 4, menu);


        db.collection("restaurants").add(restaurant);




    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) // Checking to see if the user is currently logged in
        {
            Intent intent = new Intent(loginPage.this, MainActivity.class);
            // UNCOMMENT THIS startActivity(intent);
        }
    }

    public void register_user(View view) {

        startActivity(new Intent(this, RegisterPage.class));
        finish();
    }

    public void login_user(View view) {


        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(loginPage.this, "Sucessfully logged in!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(loginPage.this, MainActivity.class);
                    // UNCOMMENT THIS startActivity(intent);

                }
                else
                {
                    Toast.makeText(loginPage.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void password_reset(View view) {

        String email = emailInput.getText().toString();

        Bundle extras = new Bundle();
        extras.putString(EXTRA_EMAIL, email);

        Intent intent = new Intent(this, PasswordReset.class);
        intent.putExtra(EXTRA, extras);
        startActivity(intent);
    }
}