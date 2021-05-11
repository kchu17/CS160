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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

public class RestaurantRegistrationActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 500;
    private final String PROFILE_PIC_PATH = "profiles";
    private final String TAG = "RestaurantRegistration";


    private ImageView restaurantImage;
    private EditText restaurantName;
    private EditText restaurantType;
    private EditText adminEmail;
    private EditText passwordEditText;
    private Uri imageURI;
    private ProgressBar progressBar;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private StorageReference reference;
    private String uploadedImageURL;
    private String restaurantAddress;
    private String restaurantDbId;
    private FirebaseAuth mAuth;
    private double longitude;
    private double latitude;


    private boolean imageSelected;
    private boolean latLngSelected;
    private boolean restaurantAddressSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_registration);

        restaurantDbId = "";
        restaurantImage = findViewById(R.id.restaurantImageView);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantType = findViewById(R.id.restaurantType);
        adminEmail = findViewById(R.id.adminEmail);
        passwordEditText = findViewById(R.id.password);
        restaurantImage = findViewById(R.id.restaurantImageView);
        progressBar = findViewById(R.id.progressBar2);

        // setting image view to default
        restaurantImage.setImageResource(R.drawable.image_placeholder);
        imageSelected = false; //setting the appropriate flag
        latLngSelected = false;
        restaurantAddressSelected = false;

        //getting database
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = storage.getReference();


        // Initializing Google Places
        Places.initialize(getApplicationContext(), "AIzaSyDsqqKSWeYyRAiHPx3t1Vozn7tyBp_SXBA");
        PlacesClient placesClient = Places.createClient(this);

        // Set up autocomplete fragement
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setCountries("US");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d(TAG, "onPlaceSelected: Place: " + place.getName() + " || Address: " + place.getAddress() + " || LatLng: " + place.getLatLng());
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
                restaurantAddress = place.getAddress();
                latLngSelected = true;
                restaurantAddressSelected = true;

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: Error in Places API " + status.toString());
            }
        });




    }



    public void onRegisterBtn(View view) {

        String restaurantNameInput = restaurantName.getText().toString().trim();
        String restaurantTypeInput = restaurantType.getText().toString().trim();
        String adminEmailInput = adminEmail.getText().toString().trim();
        String passwordInput = passwordEditText.getText().toString().trim();




        if (restaurantNameInput.isEmpty()){
            restaurantName.setError("Restaurant name is required!");
            restaurantName.requestFocus();
            return;
        }
        if (restaurantTypeInput.isEmpty())
        {
            restaurantType.setError("Restaurant Type is required!");
            restaurantType.requestFocus();
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
        if (latLngSelected != true)
        {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (restaurantAddressSelected != true)
        {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "onRegisterBtn: Passed all the requirements");


        Log.d(TAG, "onRegisterBtn: Adding restaurant to database");
        progressBar.setVisibility(View.VISIBLE);
        db.collection("restaurants").add(new Restaurant(restaurantNameInput, restaurantTypeInput,
                4.7,"",new ArrayList<MenuEntry>(),longitude,latitude ,restaurantAddress )).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: restaurant was successfully added to the database");
                    restaurantDbId = task.getResult().getId();
                    //uploadPic();
                    addAdminUser(adminEmailInput, passwordInput);

                }
                else
                {
                    Log.d(TAG, "onComplete: restaurant FAILED to be added to the database");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
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

            }
        }
    }

    private void uploadPic()
    {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        Log.d(TAG, "uploadPic: Beginning to upload pic");

        //Uri file = Uri.fromFile(new File("test/restaurantPhoto "));
        Uri file = imageURI;
        StorageReference riversRef = reference.child(""+restaurantDbId+"/ProfilePic");


        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                pd.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RestaurantRegistrationActivity.this, "Image Uploading failed..", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: Failed to upload restaurant picture");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "onSuccess: restaurant picture was uploaded successfully");


                Log.d(TAG, "uploadPic: Attempting to get download url for the image that was uploaded");
                riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: Successfully obtain url for image uploaded.");
                            updateImageUrl(task.getResult().toString());
                        }
                        else
                        {
                            Log.d(TAG, "onComplete: Failed to get url for image");
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: " + (int) progressPercent + "%");
            }
        });



    }

    private void updateImageUrl(String url)
    {
        Log.d(TAG, "updateImageUrl: Attempting to update the url to the database");
        DocumentReference docRef = db.collection("restaurants").document(restaurantDbId);
        docRef.update("imageUri", url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: Successfully updated image url in database");
                    Toast.makeText(RestaurantRegistrationActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RestaurantRegistrationActivity.this, loginPage.class));
                    finish();


                }
                else
                {
                    Log.d(TAG, "onComplete: FAILED to update image url in database");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void addAdminUser(String email, String password)
    {
        Log.d(TAG, "addAdminUser: Creating User");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: admin user was successfully created");

                    User user = new User("", email, UserType.ADMIN_USER, restaurantDbId);
                    Log.d(TAG, "onComplete: attemting to create user entry in database");
                    db.collection("users").document(mAuth.getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Log.d(TAG, "onComplete: Successfully added user file to database");
                                uploadPic();
                            }
                            else
                            {
                                Log.d(TAG, "onComplete: Failed to add user file to database");
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else
                {
                    Log.d(TAG, "onComplete: Could not create admin user");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}