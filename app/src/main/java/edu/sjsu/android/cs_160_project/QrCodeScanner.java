package edu.sjsu.android.cs_160_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class QrCodeScanner extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final String ID_REPLY = "edu.sjsu.android.cs_160_project.id";
    public static final String TABLE_REPLY = "edu.sjsu.android.cs_160_project.table";
    public static final String TAG = "onQrCode";

    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView text;
    private BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        // asking for permission to use the camera
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        surfaceView = findViewById(R.id.camera);
        text = findViewById(R.id.textResult);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector).setRequestedPreviewSize(640,480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                try{
                    cameraSource.start(holder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if (qrcode.size() != 0)
                {
                    text.post(new Runnable() {
                        @Override
                        public void run() {

                            text.setText(qrcode.valueAt(0).displayValue);
                            String[] splitted = qrcode.valueAt(0).displayValue.split(",");
                            String restaurantID;
                            int tableNumber;
                            if (splitted.length == 2)
                            {
                                restaurantID = splitted[0];
                                tableNumber = Integer.parseInt(splitted[1]);

                                Log.d(TAG, "run: ID: " + restaurantID  + " Table: " + tableNumber);
                                Intent intent = new Intent();
                                Bundle extras = new Bundle();

                                extras.putString(ID_REPLY, restaurantID);
                                extras.putInt(TABLE_REPLY, tableNumber);

                                intent.putExtras(extras);
                                setResult(RESULT_OK, intent);
                                finish();
                            }


                        }
                    });
                }
            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(QrCodeScanner.this, permission) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(QrCodeScanner.this, new String[]{permission}, requestCode);
        }
        else
        {
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Premission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}