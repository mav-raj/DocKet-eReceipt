package com.solution.engineering.docket_ereceipt;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private sliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button mNextButton;
    private Button mPrevButton;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/circular_std_book.otf")
        .setFontAttrId(R.attr.fontPath)
        .build());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        mSlideViewPager = (ViewPager)findViewById(R.id.slide_view_pager);
        mDotLayout = (LinearLayout)findViewById(R.id.dots_layout);
        sliderAdapter = new sliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        mNextButton = (Button)findViewById(R.id.btn_next);
        mPrevButton = (Button)findViewById(R.id.btn_prev);

        //onclick listeners of buttons
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage == mDots.length-1){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                else mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage == 0){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                else mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i< mDots.length;i++ ){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transparentWhite));

            mDotLayout.addView(mDots[i]);

        }
        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;
            if (position == 0){
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);

                mPrevButton.setText("skip");
                mNextButton.setText("next");
            }else if (position == mDots.length-1){
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);

                mNextButton.setText("finish");
                mPrevButton.setText("back");
            }else {
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);

                mNextButton.setText("next");
                mPrevButton.setText("back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



}
