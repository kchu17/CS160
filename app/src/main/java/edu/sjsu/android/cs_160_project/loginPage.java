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

import edu.sjsu.android.cs_160_project.databinding.ActivityLoginPageBinding;

public class loginPage extends AppCompatActivity {

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

                }
                else
                {
                    Toast.makeText(loginPage.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}