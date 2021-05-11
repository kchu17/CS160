package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.WriteResult;
import com.google.type.LatLng;

import java.util.ArrayList;

import edu.sjsu.android.cs_160_project.databinding.ActivityLoginPageBinding;

public class loginPage extends AppCompatActivity {

    public static final String EXTRA = "edu.sjsu.android.cs_160_project.extra";
    public static final String EXTRA_EMAIL = "edu.sjsu.android.cs_160_project.email";
    public static final String EXTRA_NAME = "edu.sjsu.android.cs_160_project.name";
    private static final String TAG = "onLoginPage";

    private ActivityLoginPageBinding binding;
    private EditText passwordInput;
    private EditText emailInput;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


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
        db  = FirebaseFirestore.getInstance();



    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) // Checking to see if the user is currently logged in
        {
            /*
            Intent intent = new Intent(loginPage.this, MainActivity.class);
            startActivity(intent);
             */

            start_correct_activity(mAuth.getUid());
        }
    }


    private void start_correct_activity(String id)
    {
        Log.d(TAG, "start_correct_activity: Attempting to get user from database");
        DocumentReference reference = db.collection("users").document(id);

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: Successfully retrieved user from database");

                    User user = task.getResult().toObject(User.class);
                    progressBar.setVisibility(View.INVISIBLE);

                    if (user.getType()== UserType.ADMIN_USER)
                    {
                        Log.d(TAG, "onComplete: User is of type admin");
                        startActivity(new Intent(loginPage.this, AdminMainActivity.class));
                        finish();
                    }
                    else
                    {
                        Log.d(TAG, "onComplete: user is of type regular");

                        Bundle extras = new Bundle();
                        extras.putString(EXTRA_NAME, user.getFullName());
                        extras.putString(EXTRA_EMAIL, user.getEmail());

                        Intent intent = new Intent(loginPage.this, MainActivity.class);
                        intent.putExtras(extras);

                        startActivity(intent);
                        finish();
                    }
                }
                else
                {
                    Log.d(TAG, "onComplete: Could not retrieve user from database");
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(loginPage.this, "Unsuccessful Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void register_user(View view) {

       /*
        startActivity(new Intent(this, RegisterPage.class));
        finish();

        */
        CustomRestaurantTypeDialog customRestaurantTypeDialog = new CustomRestaurantTypeDialog();
        customRestaurantTypeDialog.show(getSupportFragmentManager(), "testing");
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


                    start_correct_activity(mAuth.getUid());
                    /*Intent intent = new Intent(loginPage.this, MainActivity.class);  // change intent
                    startActivity(intent);
                     */

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