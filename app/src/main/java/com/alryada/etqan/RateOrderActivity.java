package com.alryada.etqan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Retofit.ApiClient;
import com.alryada.etqan.Retofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateOrderActivity extends AppCompatActivity {


    @BindView(R.id.imgBackBtn)
    ImageView imgBackBtn;
    @BindView(R.id.mRateOnTime)
    MaterialRatingBar mRateOnTime;
    @BindView(R.id.txtRateOnTime)
    TextView txtRateOnTime;
    @BindView(R.id.mRateQuality)
    MaterialRatingBar mRateQuality;
    @BindView(R.id.txtRateQuality)
    TextView txtRateQuality;
    @BindView(R.id.mRateTeam)
    MaterialRatingBar mRateTeam;

    @BindView(R.id.txtRateTeam)
    TextView txtRateTeam;

    @BindView(R.id.etRateComment)
    EditText etRateComment;

    @BindView(R.id.btnRateOrder)
    Button btnRateOrder;

    private ProgressDialog progressDialog;
    String orderId;
    float onTimeRate = 0, teamRate = 0, qualityRate = 0;
    String comment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_order);
        ButterKnife.bind(this);

        initScreen();
    }

    void initScreen() {
        orderId = getIntent().getStringExtra("order_id");


        if (getIntent().getBooleanExtra("has_rate", false)) {
            try {
                comment = getIntent().getStringExtra("comment");

                onTimeRate = Float.parseFloat(getIntent().getStringExtra("ontime_rate"));
                qualityRate = Float.parseFloat(getIntent().getStringExtra("quality_rate"));
                teamRate = Float.parseFloat(getIntent().getStringExtra("team_rate"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Log.e("Id", orderId);
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromScreen();
            }
        });

        mRateOnTime.setRating(onTimeRate);
        mRateOnTime.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                txtRateOnTime.setText(String.valueOf(rating));
            }
        });

        mRateQuality.setRating(qualityRate);
        mRateQuality.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                txtRateQuality.setText(String.valueOf(rating));
            }
        });

        mRateTeam.setRating(teamRate);
        mRateTeam.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                txtRateTeam.setText(String.valueOf(rating));
            }
        });

        etRateComment.setText(comment);
    }

    void getDataFromScreen() {
        boolean isVaild = true;
        onTimeRate = mRateOnTime.getRating();
        teamRate = mRateTeam.getRating();
        qualityRate = mRateQuality.getRating();

        comment = etRateComment.getText().toString();

        if (!(onTimeRate > 0)) {
            isVaild = false;

        }
        if (!(teamRate > 0)) {
            isVaild = false;
        }
        if (!(qualityRate > 0)) {
            isVaild = false;

        }

        if (!isVaild) {
            Toasty.info(this, getString(R.string.err_rate)).show();
            return;
        }

        if (comment.isEmpty() || comment.matches("")) {
            Toasty.info(this, getString(R.string.err_rate_comment)).show();
            isVaild = false;
            return;
        }

        rateOrderAPI();
    }

    void rateOrderAPI() {

        ApplicationClass applicationClass = (ApplicationClass) getApplication();
        progressDialog = ProgressDialog.show(this, null, getResources().getString(R.string
                .loading_text));
        progressDialog.setCancelable(false);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiService.rateOrder(
                applicationClass.getUserData().getId(),
                orderId,
                String.valueOf(onTimeRate),
                String.valueOf(qualityRate),
                String.valueOf(teamRate),
                comment,   applicationClass.getLangKey());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                Log.e("rateOrderAPI ", response.body() + " ");
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    boolean status = jsonObject.getBoolean("status");

                    if (status) {

                        Toasty.success(RateOrderActivity.this,
                                getString(R.string.suc_rate_added)).show();
                        finish();

                    } else {

                        Toasty.success(RateOrderActivity.this, getString(R.string.err_save_data)).show();
//                        txtNoData.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    Log.e("Exception ", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
