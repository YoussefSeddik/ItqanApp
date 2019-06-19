package com.alryada.etqan.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.CommonsMethods;
import com.alryada.etqan.MakeOrderActivity;
import com.alryada.etqan.Model.UserData;
import com.alryada.etqan.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    private MapView mapView;
    private GoogleApiClient mGoogleApiClient;
    private boolean isPermissionGranted = false;
    private Location mLastLocation;
    private GoogleMap googleMap;
    private boolean isAlreadyPinned = false;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private static final int REQUEST_PERMISSIONS = 20;
    private boolean GPSoff = false;
    private AlertDialog alertDialog;
    private DrawerLayout drawer;
    private ProgressDialog progressDialog;
    private LatLng mCenterLatLong;
    private PlaceAutocompleteFragment autocompleteFragment;
    private ApplicationClass applicationClass;
    private UserData userData;
    private PermissionListener permissionlistener;
    private View rootView;

    @BindView(R.id.btnOneTimeOrder)
    Button btnOneTimeOrder;

    @BindView(R.id.btnSubscriptionOrder)
    Button btnSubscriptionOrder;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        ButterKnife.bind(this, rootView);
        initScreen();
        return rootView;
    }

    void initScreen() {

        applicationClass = (ApplicationClass) getActivity().getApplication();
        userData = applicationClass.getUserData();

        initPermissionCheck();

        btnOneTimeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MakeOrderActivity.class);
                intent.putExtra("mCenterLatLong", new Gson().toJson(mCenterLatLong));
                intent.putExtra("onTime", true);
                startActivity(intent);
            }
        });

        btnSubscriptionOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MakeOrderActivity.class);
                intent.putExtra("mCenterLatLong", new Gson().toJson(mCenterLatLong));
                intent.putExtra("onTime", false);
                startActivity(intent);
//                getActivity().finish();
            }
        });
        autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.frgAutoComplete);
        autocompleteFragment.setHint(getString(R.string.txt_search_places));
//        ((View)findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                ((EditText) autocompleteFragment.getView().findViewById(R.id
                        .place_autocomplete_search_input)).setText(place.getName());

                double lat = place.getLatLng().latitude;
                double lng = place.getLatLng().longitude;
                mCenterLatLong = new LatLng(lat, lng);

                LatLng locationLatLng = new LatLng(place.getLatLng().latitude,
                        place.getLatLng().longitude);

                googleMap.addMarker(new MarkerOptions()
                        .position(locationLatLng)
                        .snippet("My location").icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 17));

            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    void initPermissionCheck() {
        checkPermission();
        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                isPermissionGranted = false;
            }
        };

        new TedPermission(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getResources().getString(R.string.msg_rejection))
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(rootView.findViewById(android.R.id.content),
                            "Grant location access.",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{android.Manifest.permission
                                                    .ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQUEST_PERMISSIONS);
                                }

                            }).show();
                } else {
                    // request the permission.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission
                                    .ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_PERMISSIONS);
                    // The callback method gets the result of the request.
                }
            }
            // return;
        }
        CheckGpsConnection();
    }

    private void GetLocationOnce() {
        boolean x;
        x = SmartLocation.with(getContext()).location().state().locationServicesEnabled();

        SmartLocation.with(getContext()).location().oneFix().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(android.location.Location location) {
                //   Toast.makeText(getApplicationContext(), "Current Location is : Lat: " + location.getLatitude() + " Long: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                mCurrentLocation = location;
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                Log.e("Location ", new Gson().toJson(mCurrentLocation));
                if (null != mCurrentLocation) {

                    LatLng locationLatLng = new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude());

                    googleMap.addMarker(new MarkerOptions()
                            .position(locationLatLng)
                            .snippet("My location").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 17));

                }
            }
        });
    }

    private void CheckGpsConnection() {

        if (!CommonsMethods.isGPSEnabled(getContext())) {
            alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle(getString(R.string.title_turn_GPS));
            alertDialog.setMessage(getString(R.string.msg_turn_GPS));
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.msg_turn_location),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(onGPS);
                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.msg_cancel), new
                    DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        rPositionMyLocationButton();

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                //todo add marker here to location
//                Toast.makeText(getActivity(), "Good", Toast.LENGTH_SHORT).show();

                if (CommonsMethods.isGPSEnabled(getContext())) {
                    if (!isAlreadyPinned) {
                        GetLocationOnce();
                    } else {
                        try {
                            LatLng locationLatLng = new LatLng(mCurrentLocation.getLatitude(),
                                    mCurrentLocation.getLongitude());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 17));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    isAlreadyPinned = true;

                } else {
                    Toasty.info(getActivity(),
                            getResources().getString(R.string.msg_open_gps)).show();
                }

                return false;
            }
        });

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                googleMap.getCameraPosition();
                mCenterLatLong = googleMap.getCameraPosition().target;

                googleMap.clear();
                try {

                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);

                    ((EditText) autocompleteFragment.getView().findViewById(R.id
                            .place_autocomplete_search_input)).setText(getString(R.string.loading_text));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((EditText) autocompleteFragment.getView().findViewById(R.id
                                    .place_autocomplete_search_input)).setText(CommonsMethods
                                    .getAddress(getContext(), mCenterLatLong.latitude, mCenterLatLong.longitude));
                        }
                    }, 100);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // postion to the middle of suadi arbia
        LatLng cairoLatLng = new LatLng(24.774265, 46.738586);
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(cairoLatLng).zoom(5)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        GetLocationOnce();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
                .ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        //todo animate to my current location

    }

    void rPositionMyLocationButton() {
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                .getParent()).findViewById(Integer.parseInt("2"));
        // and next place it, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 150);
    }
}
