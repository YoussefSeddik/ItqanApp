package com.alryada.etqan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.CommonsMethods;
import com.alryada.etqan.Model.UserData;

import static com.alryada.etqan.Helpers.Constants.KEY_LANGUAGE_ARABIC;
import static com.alryada.etqan.Helpers.Constants.KEY_LANGUAGE_CODE;
import static com.alryada.etqan.Helpers.Constants.KEY_USER_DATA;
import static com.alryada.etqan.Helpers.Constants.PREF_KEY;
import static com.alryada.etqan.Helpers.Constants.PREF_USER_LOGGED;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedpreferences = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
                boolean isUserLogged = sharedpreferences.getBoolean(PREF_USER_LOGGED, false);

                String lang = sharedpreferences.getString(KEY_LANGUAGE_CODE, "en");
                ApplicationClass applicationClass = (ApplicationClass) getApplication();
                applicationClass.setLangKey(lang);

                if (lang.equals(KEY_LANGUAGE_ARABIC)) {
                    CommonsMethods.setLangaugeAct(KEY_LANGUAGE_ARABIC, SplashActivity.this,
                            SplashActivity.this, false);
                }
                Intent mainIntent;
                if (isUserLogged) {
                    applicationClass.setUserData(new Gson().fromJson(CommonsMethods.retrieveDataFromSharedPref
                            (SplashActivity.this,
                                    KEY_USER_DATA), UserData.class));
                    mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(mainIntent);
                finish();
            }

        }, SPLASH_DISPLAY_LENGTH);
    }

}
