package com.example.weatherapp.mar.view.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.mar.view.API.ApiClient;
import com.example.weatherapp.mar.view.API.ApiInterface;
import com.example.weatherapp.mar.view.API.Example;
import com.example.weatherapp.mar.view.API.One;
import com.example.weatherapp.mar.view.LocationClass.GPSTracker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private ImageView location;
    private View view;
    private TextView tempText, dateText, timeText, descText, humidtyText, senderText, eventText, startText, endText, description;
    private EditText locat;

    public static LatLng position;
    EditText edlocation;
    static double longitude;
    static double latitude;

    int PERMISSIONS_REQUEST_CODE = 3;
    String[] appPermissions = {

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,

    };

    public boolean checkAndRequestPermissions() {
        // Check which permissions are granted
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }
        // Ask for non-granted permissions
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(),
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSIONS_REQUEST_CODE
            );
            return false;
        }
        // App has all permissions. Proceed ahead
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            // Gather permission grant results
            for (int i = 0; i < grantResults.length; i++) {
                // Add only permissions which are denied
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            // Check if all permissions are granted
            if (deniedCount == 0) {
                // Proceed ahead with the app
                //initApp();
            }
            // Atleast one or all permissions are denied
            else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();
                    // permission is denied (this is the first time, when "never ask again" is not checked)
                    // so ask again explaining the usage of permission
                    // shouldShowRequestPermissionRationale will return true
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permName)) {
                        // Show dialog of explanation
                        showDialog("", "This app needs Storage permissions to work wihout any issues and problems.",
                                "Yes, Grant permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkAndRequestPermissions();
                                    }
                                },
                                "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }, false);
                    }
                    //permission is denied (and never ask again is  checked)
                    //shouldShowRequestPermissionRationale will return false
                    else {
                        // Ask user to go to settings and manually allow permissions
                        showDialog("", "You have denied some permissions to the app. Please allow all permissions at [Setting] > [Permissions] screen",
                                "Go to Settings",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        // Go to app settings
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getActivity().getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                },
                                "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }, false);
                        break;
                    }
                }
            }
        }
    }

    public AlertDialog showDialog(String title, String msg, String positiveLabel,
                                  DialogInterface.OnClickListener positiveOnClick,
                                  String negativeLabel, DialogInterface.OnClickListener negativeOnClick,
                                  boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        checkAndRequestPermissions();
        tempText = view.findViewById(R.id.fragment_home_tv_temp);
        dateText = view.findViewById(R.id.fragment_home_tv_date);
        timeText = view.findViewById(R.id.fragment_home_tv_time);
        location = view.findViewById(R.id.fragment_home_iv_loc);
        locat = view.findViewById(R.id.fragment_home_edt_locat);
        descText = view.findViewById(R.id.fragment_home_tv_desc);
        humidtyText = view.findViewById(R.id.fragment_home_tv_humidity);
        senderText = view.findViewById(R.id.fragment_home_tv_sender);
        startText = view.findViewById(R.id.fragment_home_tv_start);
        endText = view.findViewById(R.id.fragment_home_tv_end);
        description = view.findViewById(R.id.fragment_home_tv_description);

        GPSTracker gpsTracker = new GPSTracker(getActivity());
        gpsTracker.getLocation();
        if (gpsTracker.getLatitude() != 0.0 && gpsTracker.getLongitude() != 0.0) {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

            position = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
//                location.setText(address);


//
//                Toast.makeText(CurrentLocation.this, "" + address, Toast.LENGTH_SHORT).show();
//                Toast.makeText(CurrentLocation.this, "" + position, Toast.LENGTH_SHORT).show();

            }
        }
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        String dateToStr = format.format(today);
        dateText.setText(dateToStr);
        System.out.println(dateToStr);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherData(locat.getText().toString().trim());
            }

        });
     //   getAlerts("NWS Tulsa (Eastern Oklahoma)");

        return view;

    }


    private void getWeatherData(String name) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Example> call = apiInterface.getWeatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                tempText.setText("Temp" + " " + response.body().getMain().getTemp() + " C");
                descText.setText("Feels Like" + " " + response.body().getMain().getFeels_like());
                humidtyText.setText("Humidity" + " " + response.body().getMain().getHumidity());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

   /* private void getAlerts(String q) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<One> call = apiInterface.getAlerts(q);
        call.enqueue(new Callback<One>() {
            @Override
            public void onResponse(Call<One> call, Response<One> response) {
                senderText.setText("sender_name" + " " + response.body().getAlerts().getSenderName());
                startText.setText("event" + " " + response.body().getAlerts().getStart());
                endText.setText("end" + " " + response.body().getAlerts().getEnd());
                description.setText("description" + " " + response.body().getAlerts().getDescription());
            }

            @Override
            public void onFailure(Call<One> call, Throwable t) {

            }
        });

    }*/

}


