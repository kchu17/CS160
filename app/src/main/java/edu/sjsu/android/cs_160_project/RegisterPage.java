package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.sjsu.android.cs_160_project.databinding.ActivityLoginPageBinding;
import edu.sjsu.android.cs_160_project.databinding.ActivityRegisterPageBinding;

public class RegisterPage extends AppCompatActivity {

    private ActivityRegisterPageBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText email;
    private EditText password;
    private EditText fullName;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        email = binding.email;
        password = binding.password;
        fullName = binding.fullName;
        progressBar = binding.progressBar;
    }

    public void register_user(View view) {

        String email_input = email.getText().toString().trim();
        String password_input = password.getText().toString().trim();
        String fullName_input = fullName.getText().toString().trim();

        if (fullName_input.isEmpty())
        {
            fullName.setError("Full name is required!");
            fullName.requestFocus();
            return;
        }
        if (email_input.isEmpty())
        {
            email.setError("An email is required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_input).matches())
        {
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }
        if (password_input.isEmpty())
        {
            password.setError("A password is required!");
            password.requestFocus();
            return;
        }
        if (password_input.length() < 8)
        {
            password.setError("Password length must be at least 8 characters!");
            password.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email_input, password_input).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                    User user = new User(fullName_input, email_input,UserType.REGULAR_USER, "");

                    Toast toast = Toast.makeText(RegisterPage.this,"Registration Successful!", Toast.LENGTH_SHORT);
                    toast.show();

                    db.collection("users").document(mAuth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterPage.this,"User Successfully saved in Database!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(RegisterPage.this, loginPage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterPage.this,"Failed to save User in Database", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else
                {
                    Toast toast = Toast.makeText(RegisterPage.this,"Registration Failed!", Toast.LENGTH_SHORT);
                    toast.show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}