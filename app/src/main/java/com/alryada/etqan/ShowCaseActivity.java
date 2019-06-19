package com.alryada.etqan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ShowCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_case);

        findViewById(R.id.btnEnterAPP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ShowCaseActivity.this, MainActivity.class));
                startActivity(new Intent(ShowCaseActivity.this, IntroActivity.class));
                finish();
            }
        });
    }
}
