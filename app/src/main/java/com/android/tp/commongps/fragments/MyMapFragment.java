package com.android.tp.commongps.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.tp.commongps.R;
import com.android.tp.commongps.activity.MainActivity;
import com.android.tp.commongps.core.MapManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Objects;


public class MyMapFragment extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    Button findMeButton;
    GoogleMap map;
    MapManager mapManager;

    public static Fragment newInstance() {
        MyMapFragment myMapFragment = new MyMapFragment();

        Bundle myBundle = new Bundle();
        //myBundle.putInt(somedata);
        //myMapFragment.setArguments(myBundle);

        return myMapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        // work with arguments
//        if (arguments != null) {
//            data = arguments.getData(DATA_KEY);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); // приводит к вызову onMapReady
        findMeButton = (Button) view.findViewById(R.id.find_me);

        // при нажатии на кнопку происходит проверка включен ли GPS и карта зумится на местоположение клиента
        findMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapManager.checkGps();
                mapManager.showClientLocation();
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        this.map.getUiSettings().setMyLocationButtonEnabled(false);

        mapManager = new MapManager((MainActivity) Objects.requireNonNull(getActivity()), map);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    public MapView getMapView() {
        return this.mapView;
    }
    public Button getFindMeButton() {
        return this.findMeButton;
    }
}