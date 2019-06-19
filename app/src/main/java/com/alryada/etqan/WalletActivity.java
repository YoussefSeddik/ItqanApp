package com.alryada.etqan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alryada.etqan.Adapters.PaymentMethodsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends AppCompatActivity {


    @BindView(R.id.rvPaymentMethods)
    RecyclerView rvPaymentMethods;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgBackBtn)
    ImageView imgBackBtn;

    private PaymentMethodsAdapter notificationsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);

        initScreen();
    }

    void initScreen() {

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setUpRecycleView();
    }

    void setUpRecycleView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPaymentMethods.setLayoutManager(mLayoutManager);
        rvPaymentMethods.setItemAnimator(new DefaultItemAnimator());

        notificationsViewAdapter = new PaymentMethodsAdapter(this, null);
        rvPaymentMethods.setAdapter(notificationsViewAdapter);
    }
}
