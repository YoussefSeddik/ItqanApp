package com.alryada.etqan;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.CommonsMethods;
import com.alryada.etqan.Model.DefaultResponse;
import com.alryada.etqan.Model.UserData;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alryada.etqan.Helpers.Constants.KEY_USER_DATA;

public class SignUpActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etConfirmPass)
    EditText etConfirmPass;

    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.etMobile)
    EditText etMobile;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.cbTerms)
    CheckBox cbTerms;

    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnTerms)
    TextView btnTerms;





//    @BindView(R.id.relSignUp)
//    RelativeLayout relSignUp;

    String email, password;
    private String confirmPassword;
    boolean isVaildData = true;
    //    Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private boolean isPermissionGranted = false;
    private ProgressDialog progressDialog;
    private String fcmToken;
    private String gender;
    private String mobile, address, phone;
    private String name;
    private String userId;
    private TextView btnPrivacy , btnSeeMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initScreen();
    }

    void initScreen() {

        fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Tokenmmmm", "initScreen: " + fcmToken);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndValidateDataFromScreen();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
            }
        });

        btnTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set terms dialog here ya youssef
                showTermsDialog();
            }
        });

//        initPermissionCheck();
//        initGoogleClient();
    }

    public void showTermsDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.terms_dialog);
        Window dialogWindow = dialog.getWindow();
        dialog.setCancelable(true);
        if (dialogWindow != null) {
            dialog.getWindow().getAttributes().width = getResources().getDisplayMetrics().widthPixels;
//            dialog.getWindow().getAttributes().height = getResources().getDisplayMetrics().heightPixels - 500 ;
        }

        btnPrivacy = dialog.findViewById(R.id.btnPrivacy);
        btnSeeMore = dialog.findViewById(R.id.btnSeeMore);

        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://etqan.app/admin/privacy_policy.html")));
            }
        });

        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://etqan.app/admin/terms.html")));
            }
        });
        dialog.show();


    }
    void getAndValidateDataFromScreen() {

        isVaildData = true;
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        name = etUserName.getEditableText().toString().trim();
        confirmPassword = etConfirmPass.getEditableText().toString().trim();

        if (address.isEmpty() || address.matches("")) {
            etAddress.setError(getResources().getString(R.string.error_address));
            isVaildData = false;
        }
        if (name.isEmpty() || name.matches("")) {
            etUserName.setError(getResources().getString(R.string.error_name));
            isVaildData = false;
        }

        if (name.length() < 3) {
            etUserName.setError(getResources().getString(R.string.error_name_length));
            isVaildData = false;
        }
        if (email.isEmpty() || email.matches("") || !email.contains("@") || !email.contains(".com")) {
            etEmail.setError(getResources().getString(R.string.error_email_validation));
            isVaildData = false;
        }
        if (mobile.isEmpty() || mobile.matches("") || mobile.length() != 10) {
            etMobile.setError(getResources().getString(R.string.error_mobile));
            isVaildData = false;
        }
//        || phone.length() <= 8
        if (phone.isEmpty() || phone.matches("")) {
            etPhone.setError(getResources().getString(R.string.error_phone));
            //etPhone.setError(getResources().getString(R.string.error_phone_lenght));
            isVaildData = false;
        }
        if (password.length() < 6 || confirmPassword.length() < 6){
            isVaildData = false;
            etPassword.setError(getResources().getString(R.string.error_password_lenght));
            etConfirmPass.setError(getResources().getString(R.string.error_password_lenght));

        }
        if (password.isEmpty() || password.matches("")) {
            etPassword.setError(getResources().getString(R.string.error_password));
            isVaildData = false;
        }
        if (confirmPassword.isEmpty() || confirmPassword.matches("")) {
            etConfirmPass.setError(getResources().getString(R.string.error_password));
            isVaildData = false;
        }

        if (!confirmPassword.matches(password)) {
            etConfirmPass.setError(getResources().getString(R.string.error_password_not_match));

            isVaildData = false;
        }

        if (!cbTerms.isChecked()) {

            Toasty.error(this, getResources().getString(R.string.error_accept_terms))
                    .show();
            isVaildData = false;
        }

        if (radioMale.isChecked())
            gender = "male";
        else
            gender = "female";

        if (isVaildData)
            signUpWithEmilAPI();
    }


    /**
     * init google client to get current location
     */
    void initGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Disconnect from API onPause()
//        if (mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            mGoogleApiClient.disconnect();
//        }
    }

    void initPermissionCheck() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                isPermissionGranted = true;
//                Toast.makeText(SignUpActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                isPermissionGranted = false;
                Toast.makeText(SignUpActivity.this, "Permission Denied\n"
                        + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(getResources().getString(R.string.msg_rejection))
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            initPermissionCheck();
            Log.e("location ", "not  granted");

            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            Log.e("location ", "null");

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            Log.e("location ", currentLatitude + " WORKS " + currentLongitude + "");
            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("location ", "onConnectionSuspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("location ", "onConnectionFailed");
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.e("location ", "onLocationChanged");
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

    void signUpWithEmilAPI() {

        try {
            progressDialog = ProgressDialog.show(SignUpActivity.this, null, getResources().getString(R.string.loading_text));
            progressDialog.setCancelable(false);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<DefaultResponse> call = apiService.registerEmail(email, password, name, gender, mobile, "en",
                    phone, address, "", "", "1", fcmToken);
            call.enqueue(new Callback<DefaultResponse>() {

                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    DefaultResponse loginResponse;
                    progressDialog.dismiss();
                    loginResponse = response.body();

                    if (loginResponse != null) {
                        if (loginResponse.getStatus()) {
                            userId = loginResponse.getMessage();
                            saveAndTransformScreen();

                        } else {
                            Toasty.error(SignUpActivity.this,
                                    (CharSequence) loginResponse.getMessage()).show();
                        }
                    } else {
                        Toasty.error(SignUpActivity.this,
                                getResources().getString(R.string.error_signing_in)).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                    Log.e("signUpWithEmilAPI", "onFailure " + t.getMessage());
                    progressDialog.dismiss();
                    Toasty.error(SignUpActivity.this,
                            getResources().getString(R.string.error_signing_up)).show();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("signUpWithEmilAPI", e.getMessage());
        }
    }

    void saveAndTransformScreen() {
        UserData userData = new UserData();
        userData.setEmail(email);
        userData.setNameAR(name);
        userData.setNameEN(name);
        userData.setMobile(mobile);
        userData.setPhone(phone);
        userData.setAddressAR(address);
        userData.setAddressEN(address);
        userData.setPlatformToken(fcmToken);
        userData.setGender(gender);
        userData.setId(userId);
        ApplicationClass applicationClass = (ApplicationClass) getApplication();
        applicationClass.setUserData(userData);

        CommonsMethods.storeDataToSharedPref(SignUpActivity.this,
                new Gson().toJson(userData),
                KEY_USER_DATA);
        finish();
    }

}
