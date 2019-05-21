package com.baseinfotech.juscep.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baseinfotech.juscep.R;


public class AskPermissionActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 11;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 12;
    private final int MY_PERMISSIONS_REQUEST_INTERNET = 13;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 14;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 15;
    private final int MY_PERMISSIONS_REQUEST_READ_GESERVICES = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permission);
        checkPermissions();

    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    MY_PERMISSIONS_REQUEST_INTERNET);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);
        }
        else {
            startAction();
        }

    }

    private void startAction(){


        startActivity(new Intent(this, MainActivity.class));
        finish();


    }


    private void configureSettings(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    Toast.makeText(this, "permission needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    Toast.makeText(this, "permission needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    Toast.makeText(this, "permission needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    Toast.makeText(this, "permission needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            } case MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    Toast.makeText(this, "permission needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}