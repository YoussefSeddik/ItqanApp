package com.alryada.etqan.Helpers;

import android.app.Application;

import com.alryada.etqan.Model.UserData;

/**
 * Created by Abd El-Sattar
 * on 2/3/2018.
 */

public class ApplicationClass extends Application {

    UserData userData;
    String langKey;
//    MakeOrderRequest makeOrderRequest;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
//    public MakeOrderRequest getMakeOrderRequest() {
//        return makeOrderRequest;
//    }
//
//    public void setMakeOrderRequest(MakeOrderRequest makeOrderRequest) {
//        this.makeOrderRequest = makeOrderRequest;
//    }
}
