package com.alryada.etqan;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.Constants;
import com.alryada.etqan.Model.Login.LoginResponse;
import com.alryada.etqan.Model.UserData;
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

import static com.alryada.etqan.Helpers.Constants.KEY_USER_DATA;
import static com.alryada.etqan.Helpers.Constants.PREF_KEY;
import static com.alryada.etqan.Helpers.Constants.PREF_USER_LOGGED;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    //region declare views
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;
    //
    @BindView(R.id.txtForgetPassword)
    TextView txtForgetPassword;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

//    @BindView(R.id.relSignUp)
//    RelativeLayout relSignUp;
    //endregion

    private ProgressDialog progressDialog;
    private String email, password;
    private LoginResponse loginResponse;
    private ApplicationClass applicationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initScreen();
    }

    void initScreen() {
        ButterKnife.bind(this);

        applicationClass = (ApplicationClass) getApplication();

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
        findViewById(R.id.relEnterApp).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp: {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            }
            case R.id.btnSignIn: {
                Constants.DICOVER_APP = 0;
                loginWithEmail();
                break;
            }
            case R.id.txtForgetPassword: {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            }
            case R.id.relEnterApp:
                Constants.DICOVER_APP = 1;
                Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    void loginWithEmail() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        if (vaildateData())
            loginWithEmailAPI();
    }

    boolean vaildateData() {
        boolean isValid = true;
        if (email == null || email.matches("")) {
            isValid = false;
            etEmail.setError(getResources().getString(R.string.error_email_validation));
        }

        if (password == null || password.matches("")) {
            isValid = false;
            etPassword.setError(getResources().getString(R.string.error_password));
        }

        return isValid;
    }

    void loginWithEmailAPI() {
        progressDialog = ProgressDialog.show(LoginActivity.this, null, getResources().getString(R.string.loading_text));
        progressDialog.setCancelable(false);

//        Log.e("loginWith", email + " " + password);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.login(email, password, "1", refreshedToken, "en");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
//                Log.e("Response", "here");
                Log.e("loginWithEmailAPI", response.body() + " ");

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
                        saveAndTransformScreen();

                    } else {
                        Toasty.error(LoginActivity.this, jsonObject.getString("message")).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(LoginActivity.this,
                            getResources().getString(R.string.error_signing_in)).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Failure", t.getMessage());
                progressDialog.dismiss();
                Toasty.error(LoginActivity.this,
                        getResources().getString(R.string.error_signing_in)).show();

            }
        });
    }

    void saveAndTransformScreen() {
        UserData userData = new Gson().fromJson(new Gson().toJson(loginResponse.getMessage()),
                UserData.class);
        applicationClass.setUserData(userData);
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        prefsEditor.putString(KEY_USER_DATA, new Gson().toJson(userData));
        prefsEditor.putBoolean(PREF_USER_LOGGED, true);
        prefsEditor.apply();

//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        startActivity(new Intent(LoginActivity.this, IntroActivity.class));
        finish();

    }
}

