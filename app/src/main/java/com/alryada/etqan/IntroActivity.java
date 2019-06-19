package com.alryada.etqan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alryada.etqan.Adapters.SliderAdabter;

public class IntroActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;
    private SliderAdabter sliderAdabter;
    private TextView[] mDots;

    private Button mFinishtBtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        sliderAdabter = new SliderAdabter(this);
        mSlideViewPager.setAdapter(sliderAdabter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewlistener);

        mFinishtBtn = (Button) findViewById(R.id.finBtn);

        mFinishtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(34);
            mDots[i].setTextColor(getResources().getColor(R.color.WhGray));
            mDotsLayout.addView(mDots[i]);

        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.backg));
        }
    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            mCurrentPage = position;
            if (position == mDots.length - 1) {
                mFinishtBtn.setEnabled(true);
                mFinishtBtn.setVisibility(View.VISIBLE);
                mFinishtBtn.setText(R.string.txt_finish);
            } else {
                mFinishtBtn.setEnabled(false);
                mFinishtBtn.setVisibility(View.INVISIBLE);
                mFinishtBtn.setText("");

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
