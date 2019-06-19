package com.alryada.etqan.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alryada.etqan.Model.PayFortData;
import com.alryada.etqan.R;
import com.alryada.etqan.Retofit.ApiClientPay;
import com.alryada.etqan.Retofit.ApiInterfacePay;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.payfort.fort.android.sdk.base.FortSdk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.payfort.sdk.android.dependancies.commons.Constants.FORT_PARAMS.SDK_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreditCardFragment extends Fragment {
    private FortCallBackManager fortCallback= null;
    Button credit_card_pay ;
    EditText credit_card_num , credit_card_expire_date ,credit_card_cvv, credit_card_name;


    public CreditCardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);
        credit_card_pay = view.findViewById(R.id.credit_card_pay);
        credit_card_num = view.findViewById(R.id.credit_card_num);
        credit_card_expire_date = view.findViewById(R.id.credit_card_expire_date);
        credit_card_cvv = view.findViewById(R.id.credit_card_cvv);
        credit_card_name = view.findViewById(R.id.credit_card_name);

        fortCallback = FortCallback.Factory.create();

        credit_card_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceToken();

            }
        });





        // Inflate the layout for this fragment
        return view;
    }


    public void getDeviceToken()
    {
        Toast.makeText(getContext(), "gggg", Toast.LENGTH_SHORT).show();

        String deviceId = FortSdk.getDeviceId(getContext());
        ApiInterfacePay apiInterfacePay = ApiClientPay.getClient().create(ApiInterfacePay.class);
        Call<PayFortData> call = apiInterfacePay.getToken(
                SDK_TOKEN,"zx0IPmPy5jp1vAz8Kpg7","CycHZxVj","en",deviceId,
                "7cad05f0212ed933c9a5d5dffa31661acf2c827a"

                );
        Toast.makeText(getContext(), "gggg", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<PayFortData>() {
            @Override
            public void onResponse(Call<PayFortData> call, Response<PayFortData> response) {
                Toast.makeText(getContext(), "onResponse"+response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PayFortData> call, Throwable t) {
                Toast.makeText(getContext(), "onResponse"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
