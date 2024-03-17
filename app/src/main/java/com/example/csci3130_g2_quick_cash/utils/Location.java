package com.example.csci3130_g2_quick_cash.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location {
    private static android.location.Location currUserLocation;

    /**
     * This method turns on the GPS on device
     * @param activity the activity that wants GPS turned on
     * @param locationRequest the location request
     */
    public static void turnOnGPS(Activity activity, LocationRequest locationRequest) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices
                .getSettingsClient(activity.getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(task -> {

            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {

                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(activity, 2);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Device does not have location feature
                        break;
                }
            }
        });
    }

    /**
     * This method will check if GPS is enabled on device
     * @param activity the activity that needs GPS enabled
     * @return true if the GPS was enabled, false otherwise
     */
    public static boolean isGPSEnabled(Activity activity) {
        LocationManager locationManager;
        boolean isEnabled;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    /**
     * This method will return a ZipCode from a Location
     * @param activity the activity that needs the zip code
     * @param location the location which contains the zip code
     * @return the zip code from @location
     * @throws IOException An exception is thrown if the location provided was invalid
     */
    public static String getPostalCodeByLocation(Activity activity, android.location.Location location) throws IOException {

        Geocoder mGeocoder = new Geocoder(activity, Locale.getDefault());
        String zipcode = null;

        List<Address> addresses = mGeocoder.getFromLocation(location.getLatitude(),
                location.getLongitude(), 5);

        if (addresses != null && addresses.size() > 0) {

            for (int i = 0; i < addresses.size(); i++) {
                Address address = addresses.get(i);
                if (address.getPostalCode() != null) {
                    zipcode = address.getPostalCode();
                    System.out.println(zipcode);
                    break;
                }

            }
            return zipcode;
        }

        return null;
    }

    /**
     * This method will set the current user location with help of GPS
     * @param activity the activity that needs the location to be set
     */
    public static void setCurrUserLocation(Activity activity) {
        LocationRequest locationRequest;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted
            if (Location.isGPSEnabled(activity)) {
                // And GPS is enabled
                // then set the current user location to user's actual current location
                LocationServices.getFusedLocationProviderClient(activity).
                        requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @Override
                                    public void onLocationResult(@NonNull LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        LocationServices.
                                                getFusedLocationProviderClient(
                                                        activity).
                                                removeLocationUpdates(this);
                                        if (locationResult.getLocations().size() > 0) {
                                            int index = locationResult.getLocations().size() - 1;
                                            currUserLocation = locationResult.getLocations().get(index);
                                        }
                                    }
                                }
                                , Looper.getMainLooper());
            } else {
                Location.turnOnGPS(activity, locationRequest);
            }
        } else {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /**
     * A getter for current user location
     *
     * @return the current user location
     */
    public static android.location.Location getCurrUserLocation() {
        return currUserLocation;
    }

    /**
     * This method will convert a zip code into a Location a with same Latitude and Longitude
     *
     * @param activity the activity that needs this conversion
     * @param zip      the zip code to be converted
     * @return the Location with same latitude and longitude
     */
    public static android.location.Location getLocationFromZipCode(Activity activity, String zip) {
        if (activity == null || zip == null || zip.isEmpty()) {
            return null;
        }

        final Geocoder geocoder = new Geocoder(activity);
        android.location.Location location = null;

        try {
            List<Address> addresses = geocoder.getFromLocationName(zip, 1);
            if (addresses != null && !addresses.isEmpty()) {
                if (addresses.get(0) != null) {
                    location = new android.location.Location("");
                    location.setLatitude(addresses.get(0).getLatitude());
                    location.setLongitude(addresses.get(0).getLongitude());
                }
            }
        } catch (IOException e) {
            // handle exception
        }

        return location;
    }
}