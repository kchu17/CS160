package edu.sjsu.android.cs_160_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test_database();
    }

    public void test_database()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, String> user = new HashMap<>();
        user.put("first_name", "kuk");
        user.put("last_name", "ore");
        user.put("born", "2006");

        db.collection("users").add(user);
    }
}