package com.alryada.etqan.Fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Model.UserData;
import com.alryada.etqan.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {


    //region declare views
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.imgEdit)
    ImageView imgEdit;
    @BindView(R.id.etUserName)
    MaterialEditText etUserName;
    @BindView(R.id.etEmail)
    MaterialEditText etEmail;
    @BindView(R.id.etAddress)
    MaterialEditText etAddress;
    @BindView(R.id.etMobile)
    MaterialEditText etMobile;
    @BindView(R.id.etPhone)
    MaterialEditText etPhone;
    @BindView(R.id.etOldPassword)
    MaterialEditText etOldPassword;
    @BindView(R.id.imgEditPassword)
    ImageView imgEditPassword;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.btnSave)
    Button btnSave;
    //endregion

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    String oldPassword, newPassword;
    UserData userData;
    private String email;
    private String mobile;
    private String address;
    private String phone;
    private String name;
    private String gender;
    private View rootView;
    private ApplicationClass applicationClass;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        initScreen();
        return rootView;
    }


    void initScreen() {
        applicationClass = (ApplicationClass) getActivity().getApplication();
        userData = applicationClass.getUserData();

        if (userData != null)
            getUserData();

        btnSave.setVisibility(View.GONE);
        btnSave.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgEditPassword.setOnClickListener(this);
    }

    void bindUserDate() {
        etUserName.setText(userData.getName());
        etEmail.setText(userData.getEmail());
        etAddress.setText(userData.getAddress());
        etMobile.setText(userData.getMobile());
        etPhone.setText(userData.getPhone());
        etOldPassword.setText("*****");

        if (userData.getGender().equals("male"))
            radioMale.setChecked(true);
        else
            radioFemale.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgEdit: {
                enableEdit();
                break;
            }

            case R.id.imgEditPassword: {
                showDialogChangePassword();

                break;
            }
            case R.id.btnSave: {
                //call api to update it
                getAndValidateDataFromScreen();
                break;
            }
        }

    }

    void enableEdit() {
        etUserName.setEnabled(true);
        etEmail.setEnabled(true);
        etAddress.setEnabled(true);
        etMobile.setEnabled(true);
        etPhone.setEnabled(true);
        radioMale.setEnabled(true);
        radioFemale.setEnabled(true);
        btnSave.setVisibility(View.VISIBLE);
    }

    void showDialogChangePassword() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(getString(R.string.title_change_password));

        LayoutInflater li = LayoutInflater.from(getContext());
        View view = li.inflate(R.layout.dialog_change_password, null);
        final EditText etOldPassword = (EditText) view.findViewById(R.id.etOldPassword);
        final EditText etNewPassword = (EditText) view.findViewById(R.id.etNewPassword);
        alertDialog.setView(view);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string
                .txt_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //todo check if the password is correct then update the new password
                oldPassword = etOldPassword.getText().toString();
                newPassword = etNewPassword.getText().toString();

                boolean isVaild = true;
                if (oldPassword.isEmpty() || oldPassword.matches("")) {
                    etOldPassword.setError(getString(R.string.txt_err_old_password));
                    isVaild = false;
                }
                if (newPassword.isEmpty() || newPassword.matches("")) {
                    etNewPassword.setError(getString(R.string.err_new_password));
                    isVaild = false;
                }

                if (isVaild)
                    updatePasswordAPI();

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string
                .txt_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(ProfileActivity2.this, "close", Toast.LENGTH_SHORT).show();

            }
        });
        alertDialog.show();
    }

    void updatePasswordAPI() {

        try {
            progressDialog = ProgressDialog.show(getContext(), null, getResources().getString(R.string.loading_text));
            progressDialog.setCancelable(false);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<String> call = apiService.updatePassword(userData.getId(), oldPassword, newPassword,
                    applicationClass.getLangKey());
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.e("update password", response.body());
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            Toasty.success(getContext(),
                                    getString(R.string.suc_update_password)).show();
                        } else {
                            Toasty.error(getContext(), jsonObject.getString("message")).show();

                            showDialogChangePassword();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toasty.error(getContext(), getString(R.string.err_change_password)).show();
                        showDialogChangePassword();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                    Log.e("signUpWithEmilAPI", "onFailure " + t.getMessage());
                    progressDialog.dismiss();
                    Toasty.error(getContext(),
                            getResources().getString(R.string.error_signing_up)).show();
                    showDialogChangePassword();


                }
            });
        } catch (Exception e) {
//            Log.e("signUpWithEmilAPI", e.getMessage());
        }
    }

    void updateUserInfoAPI() {

        try {
            progressDialog = ProgressDialog.show(getContext(), null, getResources().getString(R.string.loading_text));
            progressDialog.setCancelable(false);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<String> call = apiService.updateUserInfo(userData.getId(),
                    email,
                    name,
                    name,
                    gender,
                    mobile,
                    phone,
                    address,  //ar
                    address,   //en
                    "",
                    "",
                    "1",
                    "",
                    applicationClass.getLangKey());
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.e("update password", response.body());
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            Toasty.success(getContext(),
                                    getString(R.string.suc_profile_data)).show();
                        } else {
                            Toasty.error(getContext(), jsonObject.getString("message")).show();

                            showDialogChangePassword();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toasty.error(getContext(), getString(R.string.err_updating_data)).show();
                        showDialogChangePassword();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                    Log.e("signUpWithEmilAPI", "onFailure " + t.getMessage());
                    progressDialog.dismiss();
                    Toasty.error(getContext(),
                            getResources().getString(R.string.error_signing_up)).show();
                    showDialogChangePassword();


                }
            });
        } catch (Exception e) {
//            Log.e("signUpWithEmilAPI", e.getMessage());
        }
    }

    void getAndValidateDataFromScreen() {

        boolean isVaildData = true;
        email = etEmail.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        name = etUserName.getText().toString().trim();

        if (name.isEmpty() || name.matches("")) {
            etUserName.setError(getResources().getString(R.string.error_name));
            isVaildData = false;
        }
        if (email.isEmpty() || email.matches("")) {
            etEmail.setError(getResources().getString(R.string.error_email_validation));
            isVaildData = false;
        }

        if (mobile.isEmpty() || mobile.matches("")) {
            etMobile.setError(getResources().getString(R.string.error_mobile));
            isVaildData = false;
        }


        if (radioMale.isChecked())
            gender = "male";
        else
            gender = "female";

        if (isVaildData)
            updateUserInfoAPI();
    }

    void getUserData() {
        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.txt_getting_user_data));
        progressDialog.setCancelable(false);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getUserdata(userData.getId(), applicationClass.getLangKey());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                Log.e("getUserData", response.body() + " ");

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        userData = new Gson().fromJson(jsonObject.getString("message"),
                                UserData.class);
                        if (userData != null)
                            bindUserDate();
                    } else {
                        Toasty.error(getContext(), jsonObject.getString("message")).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(getContext(),
                            getResources().getString(R.string.error_getting_data)).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Failure", t.getMessage());
                progressDialog.dismiss();
                Toasty.error(getContext(),
                        getResources().getString(R.string.error_getting_data)).show();

            }
        });
    }

}
