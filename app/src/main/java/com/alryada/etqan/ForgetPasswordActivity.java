package com.alryada.etqan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    MaterialEditText etEmail;
    @BindView(R.id.btnForgetPassword)
    Button btnForgetPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initScreen();
    }

    void initScreen() {
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo check email is empty and then hit api

                if (!etEmail.getText().toString().isEmpty()) {
                    forgetPassword();
                } else {
                    etEmail.setError("Please enter a vail email");
                }
            }
        });
    }

    void forgetPassword() {
        progressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.loading_text));
        progressDialog.setCancelable(false);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.forgetPassword(etEmail.getText().toString(),"en");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
//                Log.e("Response", "here");

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        Toast.makeText(ForgetPasswordActivity.this,
                                jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toasty.error(ForgetPasswordActivity.this, jsonObject.getString("message")).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(ForgetPasswordActivity.this, getString(R.string.err_data)).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Failure", t.getMessage());
                progressDialog.dismiss();
                Toasty.error(ForgetPasswordActivity.this,
                        getResources().getString(R.string.err_data)).show();

            }
        });
    }

}
