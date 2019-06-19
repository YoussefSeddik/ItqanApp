package com.alryada.etqan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alryada.etqan.Adapters.MyAdapter;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.CommonsMethods;
import com.alryada.etqan.Helpers.Constants;
import com.alryada.etqan.Model.MakeOrderRequest;
import com.alryada.etqan.Model.Services.AppService;
import com.alryada.etqan.Model.UserData;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeOrderActivity extends AppCompatActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    @BindView(R.id.spChoosePaymentMethod)
    Spinner spChoosePaymentMethod;

    @BindView(R.id.rvWeekDays)
    RecyclerView rvWeekDays;

    @BindView(R.id.lyChooseDays)
    LinearLayout lyChooseDays;

    @BindView(R.id.imgBackBtn)
    ImageView imgBackBtn;
    @BindView(R.id.txtUserLocation)
    TextView txtUserLocation;
    @BindView(R.id.spSubscription)
    Spinner spSubscription;
    @BindView(R.id.spServices)
    Spinner spServices;
    @BindView(R.id.spNationalities)
    Spinner spNationalities;
    @BindView(R.id.btnMinusEmployess)
    Button btnMinusEmployess;
    @BindView(R.id.txtNumEmpolyess)
    TextView txtNumEmpolyess;
    @BindView(R.id.btnAddEmployees)
    Button btnAddEmployees;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radioGender)
    RadioGroup radioGender;
    @BindView(R.id.btnMinusDuration)
    Button btnMinusDuration;
    @BindView(R.id.txtNumDuration)
    TextView txtNumDuration;
    @BindView(R.id.btnAddDuration)
    Button btnAddDuration;
    @BindView(R.id.dateText)
    TextView dateText;
    @BindView(R.id.btnChooseDate)
    Button btnChooseDate;
    @BindView(R.id.txtSelectedDate)
    TextView txtSelectedDate;
    @BindView(R.id.timeText)
    TextView timeText;
    @BindView(R.id.btnChooseTime)
    Button btnChooseTime;
    @BindView(R.id.linSub)
    LinearLayout linSub;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.txtSelectedTime)
    TextView txtSelectedTime;
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;
    @BindView(R.id.lyOrder)
    LinearLayout lyOrder;


    //endregion

    UserData userData;
    private ProgressDialog progressDialog;
    private int employeesNum = 1, durationHours = 2;
    private ArrayList<AppService> services;
    private String serviceId;
    private DatePickerDialog dateDilog;
    private TimePickerDialog timeDialog;
    private String date = null, time = null;
    MakeOrderRequest makeOrderRequest;
    private ApplicationClass applicationClass;
    private LatLng mCenterLatLong;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    boolean isOneTime;
    boolean[] daysSelected = new boolean[7];
    private int subPeriod = 1;
    String gender = "female";
    private Call<String> call;
    boolean isOnDaySelected = false;
    int genderIsSelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        ButterKnife.bind(this);
        initScreen();


    }

    void initScreen() {
        if (Constants.DICOVER_APP == 1) {
            btnSubmit.setVisibility(View.GONE);
        } else {
            btnSubmit.setVisibility(View.VISIBLE);

        }
        mCenterLatLong = new Gson().fromJson(getIntent().getStringExtra("mCenterLatLong"), LatLng
                .class);
        txtUserLocation.setText(CommonsMethods.getAddress(this,
                mCenterLatLong.latitude, mCenterLatLong.longitude));

        applicationClass = (ApplicationClass) getApplication();
        userData = applicationClass.getUserData();

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddDuration.setOnClickListener(this);
        btnMinusDuration.setOnClickListener(this);
        btnAddEmployees.setOnClickListener(this);
        btnMinusEmployess.setOnClickListener(this);
        btnChooseDate.setOnClickListener(this);
        txtSelectedDate.setOnClickListener(this);
        btnChooseTime.setOnClickListener(this);
        txtSelectedTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        getServicesListAPI();
        initDialogs();
        //for subcription mode

        initWeekDays("");
//        setupSubscriptionSpinner();
//        setupPaymentSpinner();


        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                genderIsSelected = 1;
                if (radioMale.isChecked()) {
                    gender = "male";
                } else {
                    gender = "female";
                }
                getSubscribePriceAPI();
            }

        });

        spServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int id1 = i;
                if (id1 != 0) {
                    serviceId = services.get(id1 - 1).getId();
                    enableDisableViews("enable");
                    getSubscribePriceAPI();

                } else {
                    enableDisableViews("");
                    serviceId = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //todo YoussefSeddik
        spSubscription.setEnabled(false);
        spSubscription.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subPeriod = i;
                if (subPeriod > 4) {
                    subPeriod = (subPeriod - 3) * 4;
                }
                Log.e("subPeriod", " " + subPeriod);
                getSubscribePriceAPI();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spChoosePaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {

                } else if (position == 2) {
//                    final Dialog summaryDialog = new Dialog(MakeOrderActivity.this);
//                    summaryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    summaryDialog.setContentView(R.layout.dialog_order_summary);
//                    summaryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    Dialog dialog = new Dialog(getBaseContext());
//                    dialog.setContentView(R.layout.fragment_credit_card);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.show();

//                    Dialog dialog = new Dialog(MakeOrderActivity.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.fragment_credit_card);
//                    Window window = dialog.getWindow();
//                    if (window != null) {
//                        dialog.getWindow().getAttributes().width = getResources().getDisplayMetrics().widthPixels;
//                    }
//                    dialog.show();
                    Intent intent = new Intent(getBaseContext(),CreditCardActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void initDialogs() {
        Calendar now = Calendar.getInstance();
        dateDilog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dateDilog.setAccentColor(getResources().getColor(R.color.app_red));

        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        dateDilog.setMinDate(gc);
        timeDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                false
        );
        Timepoint timepointStart = new Timepoint(8);
        Timepoint timepointEnd = new Timepoint(23);
        timeDialog.setMinTime(timepointStart);
        timeDialog.setMaxTime(timepointEnd);
        timeDialog.setAccentColor(getResources().getColor(R.color.app_red));
        timeDialog.enableMinutes(false);
    }

    void initWeekDays(String enable) {

        if (enable.matches("enable")) {
            String[] myDatasetEn = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                    "Friday"};
            String[] myDatasetAr = {"السبت", "الاحد", "الاثنين", "الثلاثاء", "الاربعاء", "الخميس",
                    "الجمعة"};


            mRecyclerView = (RecyclerView) findViewById(R.id.rvWeekDays);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            if (userData != null)
                if (applicationClass.getLangKey().equals("en"))
                    mAdapter = new MyAdapter(this, myDatasetEn);
                else
                    mAdapter = new MyAdapter(this, myDatasetAr);
            else {
                mAdapter = new MyAdapter(this, myDatasetEn);
            }

            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnMinusEmployess: {
                if (employeesNum != 1) {
                    employeesNum--;
                    txtNumEmpolyess.setText(String.valueOf(employeesNum));
                    getSubscribePriceAPI();
                }

                break;
            }

            case R.id.btnAddEmployees: {

                if (employeesNum != 5) {
                    employeesNum++;
                    txtNumEmpolyess.setText(String.valueOf(employeesNum));
                    getSubscribePriceAPI();

                }
                break;
            }
            case R.id.btnMinusDuration: {
                if (durationHours != 2) {
                    durationHours -= 2;
                    txtNumDuration.setText(String.valueOf(durationHours));
                    getSubscribePriceAPI();
                }

                break;
            }

            case R.id.btnAddDuration: {
                if (durationHours != 8) {
                    durationHours += 2;
                    txtNumDuration.setText(String.valueOf(durationHours));
                    getSubscribePriceAPI();
                }
                break;
            }

            case R.id.txtSelectedDate:
            case R.id.btnChooseDate: {
                dateDilog.show(getFragmentManager(), "Datepickerdialog");
                break;
            }

            case R.id.txtSelectedTime:
            case R.id.btnChooseTime: {
                timeDialog.show(getFragmentManager(), "timepickerdialog");
                break;
            }

            case R.id.btnSubmit: {
                if (userData != null)
                    gettingServiceDateFromUI();
                else {
                    Toasty.info(this, getString(R.string.txt_must_sign)).show();
                }
                break;
            }

        }
    }

    void getServicesListAPI() {
        progressDialog = ProgressDialog.show(this, null, getResources().getString(R.string
                .loading_text));
        progressDialog.setCancelable(false);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.getServicesList(applicationClass.getLangKey());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {

                        Type listType = new TypeToken<List<AppService>>() {
                        }.getType();
                        services = new Gson().fromJson
                                (jsonObject.getString("message"),
                                        listType);
                        setupServicesSpinner();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(MakeOrderActivity.this,
                            getResources().getString(R.string.err_services)).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Failure", t.getMessage());
                progressDialog.dismiss();
                Toasty.error(MakeOrderActivity.this,
                        getResources().getString(R.string.err_services)).show();

            }
        });
    }

    public void setupServicesSpinner() {

        int indexOfSelected = 0;
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.title_services));
        for (int i = 0; i < services.size(); i++) {
            list.add(services.get(i).getName());
            if (makeOrderRequest != null)
                if (services.get(i).getId().equals(makeOrderRequest.getServiceId()))
                    indexOfSelected = i + 1;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(R.layout.list_item_drop_down);
        spServices.setAdapter(dataAdapter);
        spServices.setSelection(indexOfSelected);
    }

    public void setupSubscriptionSpinner() {

        int indexOfSelected = 0;
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.txt_choose_duration));
        list.add("1 month");
        list.add("2 month");
        list.add("3 month");
        list.add("4 month");
        list.add("5 month");
        list.add("6 month");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(R.layout.list_item_drop_down);
        spSubscription.setAdapter(dataAdapter);
        spSubscription.setSelection(indexOfSelected);
    }

    public void updateDaysSelected(int pos) {
        daysSelected[pos] = !daysSelected[pos];
        getSubscribePriceAPI();
    }

    ArrayList<String> getDaysSelected() {
        ArrayList<String> days = new ArrayList<>();

        if (daysSelected[0]) {
            days.add("saturday");
            isOnDaySelected = true;
        }
        if (daysSelected[1]) {
            days.add("sunday");
            isOnDaySelected = true;
        }
        if (daysSelected[2]) {
            days.add("monday");
            isOnDaySelected = true;
        }
        if (daysSelected[3]) {
            days.add("tuesday");
            isOnDaySelected = true;
        }
        if (daysSelected[4]) {
            days.add("wednesday");
            isOnDaySelected = true;
        }
        if (daysSelected[5]) {
            days.add("thursday");
            isOnDaySelected = true;
        }
        if (daysSelected[6]) {
            days.add("friday");
            isOnDaySelected = true;
        }

        return days;
    }

    void gettingServiceDateFromUI() {

        int id1 = (int) spServices.getSelectedItemId();
        if (id1 != 0) {
            serviceId = services.get(id1 - 1).getId();
        } else {
            Toasty.info(this, getString(R.string.txt_choose_service)).show();
            return;
        }


        if (!isOneTime) {

            subPeriod = (int) spSubscription.getSelectedItemId();
            subPeriod = subPeriod * 4;

            Log.e("subPeriod", subPeriod + "");
            if (subPeriod == 0) {
                Toasty.info(this, "choose subscription duration").show();
                return;
            }
        }

        if (date == null) {
            Toasty.info(this, getString(R.string.err_chose_date)).show();
            return;
        }

        if (time == null) {
            Toasty.info(this, getString(R.string.err_choose_time)).show();
            return;
        }

        if (!radioMale.isChecked() && !radioFemale.isChecked()) {
            Toasty.info(this, getString(R.string.err_choose_gender)).show();
            return;
        }
        if (!isOnDaySelected) {
            Toasty.info(this, getString(R.string.err_day_selected)).show();
            return;
        }

//        if (isValidData) {
        //todo setting data
        if (makeOrderRequest == null)
            makeOrderRequest = new MakeOrderRequest();
        makeOrderRequest.setServiceId(serviceId);
        makeOrderRequest.setTeamNum(String.valueOf(employeesNum));
        makeOrderRequest.setHoursNum(String.valueOf(durationHours));

        if (radioMale.isChecked())
            makeOrderRequest.setGender("male");
        else
            makeOrderRequest.setGender("female");

        makeOrderRequest.setDate(date);
        makeOrderRequest.setTimeFrom(time);


        showSummaryDialog();
        //makeSubscriptionOrderAPI();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String monthStr = String.valueOf(monthOfYear + 1);
        String dayStr = String.valueOf(dayOfMonth);

        if (monthStr.length() == 1)
            monthStr = "0" + monthStr;
        if (dayStr.length() == 1)
            dayStr = "0" + dayStr;

        date = year + "-" + monthStr + "-" + dayStr;

        txtSelectedDate.setText(date);
        txtSelectedDate.setVisibility(View.VISIBLE);
        btnChooseDate.setVisibility(View.GONE);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {


        String hour = String.valueOf(hourOfDay);

        if (hour.length() == 1)
            hour = "0" + hour;
        time = hour + ":00";
        txtSelectedTime.setText(time);
        txtSelectedTime.setVisibility(View.VISIBLE);
        btnChooseTime.setVisibility(View.GONE);

        getSubscribePriceAPI();
    }


    void makeSubscriptionOrderAPI() {

        progressDialog = ProgressDialog.show(this, null, getResources().getString(R.string
                .loading_text));
        progressDialog.setCancelable(false);

        Log.e("Request_Subscribe", applicationClass.getUserData().getId() + " " +
                String.valueOf(mCenterLatLong.latitude) + " " +
                String.valueOf(mCenterLatLong.longitude) + " " +
                makeOrderRequest.getHoursNum() + " " +
                makeOrderRequest.getDate() + " " +
                makeOrderRequest.getTimeFrom() + " " +
                makeOrderRequest.getServiceId() + " " +
                makeOrderRequest.getGender() + " " +
                makeOrderRequest.getTeamNum() + " " +
                new Gson().toJson(getDaysSelected()) + " " +
                subPeriod + " " +
                applicationClass.getLangKey());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = (Call<String>) apiService.makeSubscriptionOrder(applicationClass.getUserData().getId(),
                String.valueOf(mCenterLatLong.latitude),
                String.valueOf(mCenterLatLong.longitude),
                makeOrderRequest.getHoursNum(),
                makeOrderRequest.getDate(),
                makeOrderRequest.getTimeFrom(),
                makeOrderRequest.getServiceId(),
                makeOrderRequest.getGender(),
                makeOrderRequest.getTeamNum(),
                new Gson().toJson(getDaysSelected()),
                subPeriod,
                applicationClass.getLangKey());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                Log.e("makeOrderAPI ", response.body() + " ");
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {
                        Toasty.success(MakeOrderActivity.this, getString(R.string.txt_order_siccess)).show();
                        Toasty.success(MakeOrderActivity.this, getString(R.string.txt_rep_order)).show();
//                        applicationClass.setMakeOrderRequest(new MakeOrderRequest());

                        //todo YoussefSeddik
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("MakeOrder", "1");
                        startActivity(intent);
                        finish();
                        //todo YoussefSeddik


                    } else {
                        Toasty.error(MakeOrderActivity.this, jsonObject.getString("message")).show();
                        Log.e("Request_Subscribe", response.body());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Failure", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    void getSubscribePriceAPI() {

        if (call != null)
            if (call.isExecuted())
                call.cancel();


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        call = (Call<String>) apiService.getSubscribePrice(serviceId,
                gender,
                String.valueOf(employeesNum),
                String.valueOf(durationHours),
                new Gson().toJson(getDaysSelected()),
                subPeriod + "",
                applicationClass.getLangKey());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null) {
                    Log.e("getPrice sub ", response.body() + " ");

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean status = jsonObject.getBoolean("status");


                        if (status) {
                            JSONObject messageResponse = new JSONObject(jsonObject.get
                                    ("message").toString());
                            txtTotalPrice.setText(String.format("%s SAR", messageResponse.getString("total_price")));
                        } else {
                            String messageResponse = jsonObject.get
                                    ("message").toString();
//                            Toasty.error(MakeOrderActivity.this,
//                                    messageResponse).show();
                            txtTotalPrice.setText("0.0 SAR");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        txtTotalPrice.setText("0.0 SAR");
                    }
                } else {
                    txtTotalPrice.setText("0.0 SAR");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.e("Failure", t.getMessage());
                txtTotalPrice.setText("0.0 SAR");

            }
        });
    }

    void showSummaryDialog() {
        final Dialog summaryDialog = new Dialog(MakeOrderActivity.this);
        summaryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        summaryDialog.setContentView(R.layout.dialog_order_summary);
        summaryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final DialogViewHolder dialogViewHolder = new DialogViewHolder(summaryDialog.findViewById(R.id
                .cvDialog));
        dialogViewHolder.txtOrderSerivce.setText(services.get(spServices
                .getSelectedItemPosition() - 1).getName());
        dialogViewHolder.txtDuration.setText(String.format("%s %s",
                String.valueOf(durationHours), getString(R.string.txt_hours)));
        dialogViewHolder.txtDate.setText(date);
        dialogViewHolder.txtStartTime.setText(time);

        String daysSelsctedStr = "";
        ArrayList<String> daysSelected = getDaysSelected();
        for (int i = 0; i < daysSelected.size(); i++) {
            daysSelsctedStr += daysSelected.get(i) + " ";
        }
        dialogViewHolder.txtDaysSelected.setText(daysSelsctedStr);
        dialogViewHolder.txtSubDur.setText(String.format("%s %s", String.valueOf(subPeriod),
                getString(R.string.txt_weeks)));
        dialogViewHolder.txtOrderPrice.setText(txtTotalPrice.getText().toString());
        dialogViewHolder.linConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeSubscriptionOrderAPI();
                summaryDialog.dismiss();
            }
        });
        dialogViewHolder.linCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                summaryDialog.dismiss();

            }
        });
        summaryDialog.show();

    }

    public void enableDisableViews(String enable) {
        if (enable.matches("enable")) {
            initWeekDays(enable);
            btnAddEmployees.setEnabled(true);
            btnMinusEmployess.setEnabled(true);
            radioMale.setEnabled(true);
            radioFemale.setEnabled(true);
            btnAddDuration.setEnabled(true);
            btnMinusDuration.setEnabled(true);
            btnChooseDate.setEnabled(true);
            btnChooseDate.setBackgroundResource(R.drawable.rounded_orange);
            btnChooseTime.setEnabled(true);
            btnChooseTime.setBackgroundResource(R.drawable.rounded_orange);
            spSubscription.setEnabled(true);
            txtTotalPrice.setTextColor(getResources().getColor(R.color.app_red));
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundResource(R.drawable.rounded_orange);
            spChoosePaymentMethod.setEnabled(true);

        } else {
            txtNumEmpolyess.setText("1");
            txtNumDuration.setText("2");
            employeesNum = 1;
            durationHours = 2;
            btnAddEmployees.setEnabled(false);
            btnMinusEmployess.setEnabled(false);
            radioMale.setEnabled(false);
            radioFemale.setEnabled(false);
            btnAddDuration.setEnabled(false);
            btnMinusDuration.setEnabled(false);
            btnChooseDate.setEnabled(false);
            btnChooseDate.setBackgroundResource(R.drawable.rounded_gray);
            btnChooseTime.setEnabled(false);
            btnChooseTime.setBackgroundResource(R.drawable.rounded_gray);
            spSubscription.setEnabled(false);
            txtTotalPrice.setTextColor(getResources().getColor(R.color.gray));
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundResource(R.drawable.rounded_gray);
            spChoosePaymentMethod.setEnabled(false);
        }
    }

    static class DialogViewHolder {
        @BindView(R.id.txtOrderSerivce)
        TextView txtOrderSerivce;
        @BindView(R.id.txtDaysSelected)
        TextView txtDaysSelected;
        @BindView(R.id.txtSubDur)
        TextView txtSubDur;
        @BindView(R.id.txtDuration)
        TextView txtDuration;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtStartTime)
        TextView txtStartTime;
        @BindView(R.id.txtOrderPrice)
        TextView txtOrderPrice;
        @BindView(R.id.linConfirm)
        LinearLayout linConfirm;
        @BindView(R.id.linCancel)
        LinearLayout linCancel;

        DialogViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}