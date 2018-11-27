package com.android.tp.commongps.core;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.tp.commongps.fragments.MyMapFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.Map;


public class MapManager implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    private FragmentActivity context;
    private GoogleMap map;
    private LocationManager locationManager;
    private Map<Integer, Marker> markersMap;

    private static final long MIN_TIME = 1000 * 5; // 5 секунд
    private static final float MIN_DISTANCE = 0.1f;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    //private static final boolean FIRST_INIT_USER = true;


    public MapManager(FragmentActivity context, MyMapFragment mapFragment) {
        this.context = context;
        mapFragment.getMapAsync(this); // приводит к вызову onMapReady
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        this.map.getUiSettings().setMyLocationButtonEnabled(false);
    }


    //работа с маркерами друзей
    public void addMarker(int id, String name, long lastonline, double latitude, double longtitude) {
       markersMap.put(id, createMarker(name, lastonline, latitude, longtitude));
    }

    private Marker createMarker(String name, long lastonline, double latitude, double longtitude) {
        Marker perth =  map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longtitude))
                .title(name)
                .snippet(" Last online: " + new Date(lastonline)));

        return perth;
    }

    public boolean setMarkerPosition(int id, double latitude, double longitude){
        try {
            markersMap.get(id).setPosition(new LatLng(latitude, longitude));
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

    public boolean MarkerExist(int checkingId){
       return markersMap.containsKey(checkingId);
    }
    //



    /**
     * Логика работы метода:
     * Если карта появилась и на ней есть местоположение, то камера зумится на клиента.
     * В противном случае ставим точку местоположения и инициализируем слушателей на измение местоположения
     *  (метод onLocationChanged)
     */

    private boolean findMeButtonPressed = false;
    public void showClientLocation() {
        if (map != null && map.isMyLocationEnabled()) {
            moveCameraToTheClient(map.getMyLocation());
        } else if (checkPermission()) {
            try {
                map.setMyLocationEnabled(true);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            }
            catch (Exception ex){
                Toast.makeText(context, "Some problem with your location. Please, check Google Service", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void moveCameraToTheClient(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("DEBUG", "onLocationChanged");

        CoreApplication.getClient().setPosition(location.getLatitude(), location.getLongitude(), 0D);
        FirebaseManager.getInstance().initUser(CoreApplication.getClient());

        if (findMeButtonPressed) {
            moveCameraToTheClient(location);
            findMeButtonPressed = false;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void checkGps() {
        locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
    }

    /*
        PERMISSIONS
     */
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            showClientLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            showMissingPermissionError();
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(this.context.getSupportFragmentManager(), "dialog");
    }


    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this.context, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }

        return ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}