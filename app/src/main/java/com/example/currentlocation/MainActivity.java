package com.example.currentlocation;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button button_location;
    TextView textview1, textview2, textview3, textview4, textview5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_location = findViewById(R.id.button_location);
        textview1 = findViewById(R.id.textview1);
        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);
        textview4 = findViewById(R.id.textview4);
        textview5 = findViewById(R.id.textview5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLatitude(), 1);
                        textview1.setText(Html.fromHtml(
                            "<font color='#6200EE'><b>Latitude:</b><br></font>"
                            + addresses.get(0).getLatitude()
                         ));
                        textview2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude:</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        textview3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country name:</b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));
                        textview4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality:</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));
                        textview5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Adress:</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}