package com.alryada.etqan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alryada.etqan.Model.PayFortData;
import com.alryada.etqan.Retofit.ApiClientPay;
import com.alryada.etqan.Retofit.ApiInterfacePay;
import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payfort.sdk.android.dependancies.commons.Constants.FORT_PARAMS.SDK_TOKEN;

public class CreditCardActivity extends AppCompatActivity {
    private FortCallBackManager fortCallback= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_payment);
        fortCallback = FortCallback.Factory.create();

        String deviceId = FortSdk.getDeviceId(this);
        ApiInterfacePay apiInterfacePay = ApiClientPay.getClient().create(ApiInterfacePay.class);
        Call<PayFortData> call = apiInterfacePay.getToken(
                SDK_TOKEN,"zx0IPmPy5jp1vAz8Kpg7","CycHZxVj","en",deviceId,
                "7cad05f0212ed933c9a5d5dffa31661acf2c827a"

        );
        call.enqueue(new Callback<PayFortData>() {
            @Override
            public void onResponse(Call<PayFortData> call, Response<PayFortData> response) {
                Toast.makeText(getBaseContext(), "onResponse"+response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PayFortData> call, Throwable t) {
                Toast.makeText(getBaseContext(), "onResponse"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode,resultCode,data);
    }

    public void onBackPressed(View view) {
    }

    public void onPayPressed(View view) {
    }
}
