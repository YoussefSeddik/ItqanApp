package com.alryada.etqan;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Model.OrderDetails;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alryada.etqan.Helpers.CommonsMethods.getDifferenceDate;
import static com.alryada.etqan.Helpers.Constants.KEY_ORDER_ID;

public class OrderDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {


    //region declare views
    @BindView(R.id.imgBackBtn)
    ImageView imgBackBtn;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtStartTime)
    TextView txtStartTime;
    @BindView(R.id.txtDuration)
    TextView txtDuration;
    @BindView(R.id.txtWaiting)
    TextView txtWaiting;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtWorkCounter)
    TextView txtWorkCounter;

    //endregion

    private MapView mapView;
    private GoogleMap googleMap;

    String orderId;
    private boolean isMapReady = false, isDataHere = false;
    OrderDetails orderDetails;
    private ApplicationClass applicationClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        initScren(savedInstanceState);
    }

    void initScren(Bundle bundle) {
        applicationClass = (ApplicationClass) getApplication();
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(bundle);
        mapView.onResume();
        mapView.getMapAsync(this);
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        orderId = getIntent().getStringExtra(KEY_ORDER_ID);
        getOrderDetailsAPI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // lat and lng of the order
        isMapReady = true;
//        if(isDataHere)
    }


    void getOrderDetailsAPI() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getOrderDetails(orderId, applicationClass.getLangKey());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e("getOrderDetailsAPI ", response.body() + " ");
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        orderDetails = new Gson().fromJson(jsonObject.get
                                ("message").toString(), OrderDetails.class);
                        bindDetailsToScreen();
                        if (isMapReady)
                            ainmateToOrderLocation();

//                        txtTotalPrice.setText(messageResponse.getString("total_price"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.e("Failure", t.getMessage());
            }
        });
    }

    void bindDetailsToScreen() {
        txtDate.setText(orderDetails.getDate());
        txtStatus.setText(orderDetails.getStatus());
        txtStartTime.setText(orderDetails.getFrom());
        txtDuration.setText(String.format("%s %s", getDifferenceDate(orderDetails.getFrom(),
                (orderDetails.getTo())), getString(R.string.txt_hours)));

        //cacluate distance
        LatLng cairoLatLng;
        try {
            cairoLatLng = new LatLng(orderDetails.getClientLatitude(),
                    orderDetails.getClientLongitude());
        } catch (Exception e) {
            e.printStackTrace();
            cairoLatLng = new LatLng(24.774265, 46.738586);
        }

        double distance = SphericalUtil.computeDistanceBetween(cairoLatLng, cairoLatLng);

        float[] results = new float[1];
        Location.distanceBetween(cairoLatLng.latitude, cairoLatLng.longitude,
                cairoLatLng.latitude, cairoLatLng.longitude, results);


//        txtWaiting.setText(orderDetails.getFrom());

        //draw a path
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(orderDetails.getClientLatitude(), orderDetails.getClientLongitude()),
                        new LatLng(orderDetails.getSupervisorLatitude(), orderDetails.getSupervisorLongitude())));

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));

        ainmateToOrderLocation();
    }

    void ainmateToOrderLocation() {

        LatLng cairoLatLng;
        //check if the order already have cordinate or noti
        if (orderDetails != null) {

            try {
                cairoLatLng = new LatLng(orderDetails.getClientLatitude(),
                        orderDetails.getClientLongitude());
            } catch (Exception e) {
                e.printStackTrace();
                cairoLatLng = new LatLng(24.774265, 46.738586);
            }
        } else {
            cairoLatLng = new LatLng(24.774265, 46.738586);

        }
        googleMap.addMarker(new MarkerOptions()
                .position(cairoLatLng).title(getString(R.string.hint_address)));
//                .snippet("Marker" + " " + "Description"))       ;

        CameraPosition cameraPosition = new CameraPosition.Builder().target(cairoLatLng).zoom(13)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
