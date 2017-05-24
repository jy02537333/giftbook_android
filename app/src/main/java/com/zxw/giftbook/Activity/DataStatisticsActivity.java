package com.zxw.giftbook.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zxw.giftbook.R;
import com.zxw.giftbook.config.RingView;

import pri.zxw.library.base.MyBaseActivity;
import pri.zxw.library.view.TitleBar;

/**
 * Created by sun on 2016/7/8.
 */
public class DataStatisticsActivity extends MyBaseActivity{
    private static final int TOTAL = 360;

    private Button mBtn;

    private TextView mFirstEditText;
    private TextView mSecondEditText;
    private TextView mThirdEditText;

    private RingView mRingView;
    private LinearLayout mLinearLayout;

    private int mColorFirst;
    private int mColorSecond;
    private int mColorThird;
    private TextView sjtj_Back;
    private TextView activity_sjtj_tph_proportion;
    private TextView activity_sjtj_fph_proportion;
    private TextView activity_sjtj_other_proportion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_data_statistics);
        initView();
        invalidateView();
    }

    private void initView() {

     // ImageView head_aty_back_img= (ImageView) findViewById(R.id.head_aty_back_img);
       // head_aty_back_img.setBackground();
        TitleBar head_aty_titile= (TitleBar) findViewById(R.id.lay_title_bar);
        head_aty_titile.setTitle("数据统计");
        mFirstEditText = (TextView) findViewById(R.id.sjtj_fph_people);
        mSecondEditText = (TextView) findViewById(R.id.sjtj_tph_people);
        mThirdEditText = (TextView) findViewById(R.id.other_people);
        activity_sjtj_tph_proportion= (TextView) findViewById(R.id.activity_sjtj_tph_proportion);
        activity_sjtj_fph_proportion= (TextView) findViewById(R.id.activity_sjtj_fph_proportion);
        activity_sjtj_other_proportion= (TextView) findViewById(R.id.activity_sjtj_other_proportion);
        mColorFirst = getResources().getColor(R.color.color_a);
        mColorSecond = getResources().getColor(R.color.color_b);
        mColorThird = getResources().getColor(R.color.color_c);
//        Drawable drawable =  getResources().getDrawable(R.drawable.back1);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 4, drawable.getIntrinsicHeight() / 4);
    //    sjtj_Back.setCompoundDrawables(drawable,null, null, null);
//        sjtj_Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataStatisticsActivity.this.finish();
//            }
//        });

        mLinearLayout = (LinearLayout) findViewById(R.id.Root);
        mRingView = new RingView(this);
        mRingView.setData(120, mColorFirst, 120, mColorSecond, 120,
                mColorThird, "", 60);
        LayoutParams layoutParams = new LayoutParams(750, 750);
        mRingView.setLayoutParams(layoutParams);

        mLinearLayout.addView(mRingView);
    }
    private void invalidateView() {

        float mFirstSweepAngle = 0;
        String firsInput = mFirstEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(firsInput)) {
            mFirstSweepAngle = Float.parseFloat(firsInput);
        }

        float mSecondSweepAngle = 0;
        String secondInput = mSecondEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(secondInput)) {
            mSecondSweepAngle = Float.parseFloat(secondInput);
        }

        float mThirdSweepAngle = 0;
        String thirdInput = mThirdEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(thirdInput)) {
            mThirdSweepAngle = Float.parseFloat(thirdInput);
        }

        if (mFirstSweepAngle > TOTAL || mSecondSweepAngle > TOTAL
                || mThirdSweepAngle > TOTAL) {
            Toast.makeText(DataStatisticsActivity.this, R.string.error_a,
                    Toast.LENGTH_SHORT).show();
        } else if (mFirstSweepAngle + mSecondSweepAngle > TOTAL) {
            Toast.makeText(DataStatisticsActivity.this, R.string.error_b,
                    Toast.LENGTH_SHORT).show();
        } else if (mSecondSweepAngle + mThirdSweepAngle > TOTAL) {
            Toast.makeText(DataStatisticsActivity.this, R.string.error_c,
                    Toast.LENGTH_SHORT).show();
        } else if (mFirstSweepAngle + mThirdSweepAngle > TOTAL) {
            Toast.makeText(DataStatisticsActivity.this, R.string.error_d,
                    Toast.LENGTH_SHORT).show();
        } else if (mFirstSweepAngle + mSecondSweepAngle + mThirdSweepAngle > TOTAL) {
            Toast.makeText(DataStatisticsActivity.this, R.string.error_d,
                    Toast.LENGTH_SHORT).show();
        } else {

            if (mThirdSweepAngle == 0
                    || mFirstSweepAngle + mSecondSweepAngle + mThirdSweepAngle < TOTAL) {
                mThirdEditText
                        .setText((TOTAL - mFirstSweepAngle - mSecondSweepAngle)
                                + "");
                mThirdSweepAngle = TOTAL - mFirstSweepAngle - mSecondSweepAngle;
            }

            // 刷新界面前，要重新赋值
            mRingView.setData(mFirstSweepAngle, mColorFirst, mSecondSweepAngle,
                    mColorSecond, mThirdSweepAngle, mColorThird, "", 60);

            mRingView.invalidate();
        }

    }
}
