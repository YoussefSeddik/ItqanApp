package com.alryada.etqan.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alryada.etqan.Adapters.OrderRecyclerViewAdapter;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.Constants;
import com.alryada.etqan.Model.Order;
import com.alryada.etqan.Model.UserData;
import com.alryada.etqan.R;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;
import com.alryada.etqan.SignUpActivity;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    @BindView(R.id.rvOrders)
    RecyclerView rvOrders;

    @BindView(R.id.btnNoData)
    Button btnNoData;

    @BindView(R.id.spinKitLoadingBar)
    SpinKitView spinKitLoadingBar;


    private View rootView;
    private OrderRecyclerViewAdapter orderRecyclerViewAdapter;
    private ViewPager viewPager;

    ApplicationClass applicationClass;
    UserData userData;
    ArrayList<Order> orders;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        initScreen();
        return rootView;
    }

    void initScreen() {
        ButterKnife.bind(this, rootView);
        applicationClass = (ApplicationClass) getActivity().getApplication();
        userData = applicationClass.getUserData();
        if(Constants.DICOVER_APP == 1){
            btnNoData.setText(getResources().getString(R.string.sign_up));
        }else{
            btnNoData.setText(getResources().getString(R.string.make_order));
        }
        btnNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.DICOVER_APP == 1) {
                    startActivity(new Intent(getActivity(),SignUpActivity.class));
                }else{
                    viewPager.setCurrentItem(0);
                }




            }
        });

        orders = new ArrayList<>();
        if (userData != null && Constants.DICOVER_APP!=1 )
            getOrdersAPI();
        else {
            spinKitLoadingBar.setVisibility(View.GONE);
            btnNoData.setVisibility(View.VISIBLE);
        }
    }

    void setUpRecycleView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvOrders.setLayoutManager(mLayoutManager);
        rvOrders.setItemAnimator(new DefaultItemAnimator());
        orderRecyclerViewAdapter = new OrderRecyclerViewAdapter(getContext(), orders);
        rvOrders.setAdapter(orderRecyclerViewAdapter);
    }

    void getOrdersAPI() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getOrders(userData.getId(), applicationClass.getLangKey());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                progressDialog.dismiss();
                spinKitLoadingBar.setVisibility(View.GONE);
                Log.e("getOrdersAPI ", response.body() + " ");
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");
//                    Toast.makeText(getActivity().getBaseContext(), "fffff  "+ status, Toast.LENGTH_SHORT).show();

                        if (status) {
                            JSONArray messageResponse = new JSONArray(jsonObject.get
                                    ("message").toString());
                            Type listType = new TypeToken<ArrayList<Order>>() {
                            }.getType();

                            orders = new Gson().fromJson(messageResponse.toString(), listType);
//                            Toast.makeText(getActivity().getBaseContext(), "fffff  "+ orders.size(), Toast.LENGTH_SHORT).show();

                            setUpRecycleView();

                            if (orders.size() < 1){
                                btnNoData.setVisibility(View.VISIBLE);
                                orderRecyclerViewAdapter.notifyDataSetChanged();
//                                Toast.makeText(getActivity().getBaseContext(), "ffffllllll  "+ orders.size(), Toast.LENGTH_SHORT).show();

                            }else{
                                btnNoData.setVisibility(View.GONE);

                            }



                        } else {
                            btnNoData.setVisibility(View.VISIBLE);
                        }

                } catch (JSONException e) {
                    btnNoData.setVisibility(View.VISIBLE);
                    Log.e("Exception ", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
                spinKitLoadingBar.setVisibility(View.GONE);
                btnNoData.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initScreen();
    }

    public void setArguments(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
