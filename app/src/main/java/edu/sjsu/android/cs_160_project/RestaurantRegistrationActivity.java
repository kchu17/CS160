package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

public class RestaurantRegistrationActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 500;

    private ImageView restaurantImage;
    private EditText restaurantName;
    private EditText restaurantPhoneNumber;
    private EditText adminEmail;
    private EditText passwordEditText;
    private Uri imageURI;
    private FirebaseStorage storage;
    private StorageReference reference;

    private boolean imageSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_registration);


        restaurantImage = findViewById(R.id.restaurantImageView);
        restaurantName = findViewById(R.id.restaurantName);
        //restaurantPhoneNumber = findViewById(R.id.restaurantPhoneNumber);
        adminEmail = findViewById(R.id.adminEmail);
        passwordEditText = findViewById(R.id.password);
        restaurantImage = findViewById(R.id.restaurantImageView);

        // setting image view to default
        restaurantImage.setImageResource(R.drawable.image_placeholder);
        imageSelected = false; //setting the appropriate flag

        //getting database
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();


        // Initializing Google Places
        Places.initialize(getApplicationContext(), "AIzaSyDsqqKSWeYyRAiHPx3t1Vozn7tyBp_SXBA");

        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setCountries("US");

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("PlacesAPI", "onPlaceSelected: Place: " + place.getName() + " || Address: " + place.getAddress() + " || LatLng: " + place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d("PlacesAPI", "onError: Error: " + status.toString());
            }
        });




    }



    public void onRegisterBtn(View view) {

        String restaurantNameInput = restaurantName.getText().toString().trim();
        String restaurantPhoneNumberInput = "hello"; // restaurantPhoneNumber.getText().toString().trim();
        String adminEmailInput = adminEmail.getText().toString().trim();
        String passwordInput = passwordEditText.getText().toString().trim();




        if (restaurantNameInput.isEmpty()){
            restaurantName.setError("Restaurant name is required!");
            restaurantName.requestFocus();
            return;
        }
        if (restaurantPhoneNumberInput.isEmpty())
        {
            restaurantPhoneNumber.setError("A phone number is required!");
            restaurantPhoneNumber.requestFocus();
            return;
        }
        if (adminEmailInput.isEmpty())
        {
            adminEmail.setError("An email is required!");
            adminEmail.requestFocus();
            return;
        }
        if (passwordInput.isEmpty())
        {
            passwordEditText.setError("A password is required!");
            passwordEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(adminEmailInput).matches())
        {
            adminEmail.setError("Please enter a valid email!");
            adminEmail.requestFocus();
            return;
        }
        if (passwordInput.length() < 8)
        {
            passwordEditText.setError("Password length must be at least 8 characters!");
            passwordEditText.requestFocus();
            return;
        }

        if (imageSelected != true)
        {
            Toast.makeText(this, "Please upload a profile image", Toast.LENGTH_SHORT).show();
            return;
        }



    }




    public void onRestaurantImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK && data != null && data.getData() != null)
            {
                imageURI = data.getData();
                restaurantImage.setImageURI(imageURI);
                imageSelected = true;
                uploadPic();
            }
        }
    }

    private void uploadPic()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        //Uri file = Uri.fromFile(new File("test/restaurantPhoto "));
        Uri file = imageURI;
        StorageReference riversRef = reference.child("test/profileImage");


        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                pd.dismiss();
                Toast.makeText(RestaurantRegistrationActivity.this, "Image Uploading failed..", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: " + (int) progressPercent + "%");
            }
        });
    }
}