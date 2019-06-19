package com.alryada.etqan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alryada.etqan.Adapters.NotificationsViewAdapter;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Model.Notification;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgBackBtn)
    ImageView imgBackBtn;

    @BindView(R.id.spinKitLoadingBar)
    SpinKitView spinKitLoadingBar;


    private NotificationsViewAdapter notificationsViewAdapter;
    private ApplicationClass applicationClass;
    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        initScreen();
    }

    void initScreen() {
        applicationClass = (ApplicationClass) getApplication();
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getNotificationAPI();
    }

    void setUpRecycleView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvNotification.setLayoutManager(mLayoutManager);
        rvNotification.setItemAnimator(new DefaultItemAnimator());

        notificationsViewAdapter = new NotificationsViewAdapter(this, notifications);
        rvNotification.setAdapter(notificationsViewAdapter);
    }

    void getNotificationAPI() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getNotifications(applicationClass.getUserData().getId(), applicationClass.getLangKey());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                spinKitLoadingBar.setVisibility(View.GONE);

                Log.e("getNotificationAPI ", response.body() + " ");
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        JSONArray messageResponse = new JSONArray(jsonObject.get
                                ("message").toString());
                        Type listType = new TypeToken<ArrayList<Notification>>() {
                        }.getType();
                        notifications = new Gson().fromJson(messageResponse.toString(), listType);
                        setUpRecycleView();

                        if (notifications.size() < 1)
                            txtNoData.setVisibility(View.VISIBLE);
                        else
                            txtNoData.setVisibility(View.GONE);

                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    txtNoData.setVisibility(View.VISIBLE);
                    txtNoData.setText(getString(R.string.error_getting_data));
                    Log.e("Exception ", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                spinKitLoadingBar.setVisibility(View.GONE);

//                Log.e("Failure", t.getMessage());
            }
        });
    }

}
