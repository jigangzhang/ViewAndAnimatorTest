package com.example.administrator.rentcar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.rentcar.R;

/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/26 15:46
 */

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.view
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/26 15:46
 * @description: <p>
 * <p>
 * </p>
 */

public class TestEditText extends LinearLayout {
    private static final String KET_CONTENT = "content";

    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private EditText mCenterContent;
    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;
    private boolean isAddTextLeft = false;
    private String leftText;
    private TextView mLeftText;

    public TestEditText(Context context) {
        this(context, null);
    }

    public TestEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TestEditText);
        mLeftDrawable = a.getDrawable(R.styleable.TestEditText_leftIcon);
        mRightDrawable = a.getDrawable(R.styleable.TestEditText_rightIcon);
        leftText = a.getString(R.styleable.TestEditText_text);
        isAddTextLeft = a.getBoolean(R.styleable.TestEditText_addLeftText, false);
        a.recycle();
        initViews();
    }

    private void initViews() {
        //
        mLeftIcon = new ImageView(getContext());
        LayoutParams imageLp = new LayoutParams(dpToPx(40), ViewGroup.LayoutParams.MATCH_PARENT);
        mLeftIcon.setLayoutParams(imageLp);
        mLeftIcon.setImageDrawable(mLeftDrawable);
        addView(mLeftIcon);

        if (isAddTextLeft) {
            Drawable drawable = getResources().getDrawable(R.drawable.drawable_line);
            mLeftText = new TextView(getContext());
            LayoutParams textParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLeftText.setLayoutParams(textParam);
            mLeftText.setText(leftText);
            mLeftText.setCompoundDrawables(null, null, drawable, null);
            addView(mLeftText);

            View line = new View(getContext());
            line.setBackgroundResource(R.drawable.drawable_line);
            LayoutParams lineParam = new LayoutParams(dpToPx(2), ViewGroup.LayoutParams.MATCH_PARENT);
            lineParam.setMargins(8, 8, 8, 20);
            line.setLayoutParams(lineParam);
            addView(line);
        }
        //
        mCenterContent = new EditText(getContext());
        LayoutParams editLp = new LayoutParams(dpToPx(80), ViewGroup.LayoutParams.MATCH_PARENT);
        mCenterContent.setLayoutParams(editLp);
        addView(mCenterContent);
        //
        mRightIcon = new ImageView(getContext());
        LayoutParams imageLp01 = new LayoutParams(dpToPx(40), ViewGroup.LayoutParams.MATCH_PARENT);
        mRightIcon.setImageDrawable(mRightDrawable);
        mRightIcon.setLayoutParams(imageLp01);
        addView(mRightIcon);


        mRightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCenterContent.setText("");
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            String content = bundle.getString(KET_CONTENT);
            mCenterContent.setText(content);
            Parcelable aSuper = bundle.getParcelable("super");
            super.onRestoreInstanceState(aSuper);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString(KET_CONTENT, mCenterContent.getText().toString());
        bundle.putParcelable("super", super.onSaveInstanceState());
        return bundle;
    }

    public void setLeftText(String leftText) {
        if (isAddTextLeft) {
            mLeftText.setText(leftText);
        }
    }

    public void setAddTextLeft(boolean addTextLeft) {
        isAddTextLeft = addTextLeft;
    }

    public Drawable getLeftDrawable() {
        return mLeftDrawable;
    }

    public void setLeftDrawable(Drawable leftDrawable) {
        mLeftDrawable = leftDrawable;
        mLeftIcon.setImageDrawable(mLeftDrawable);
    }

    public void setLeftDrawableRedId(@DrawableRes int resId) {
        mLeftIcon.setImageResource(resId);
    }

    public Drawable getRightDrawable() {
        return mRightDrawable;
    }

    public void setRightDrawable(Drawable rightDrawable) {
        mRightDrawable = rightDrawable;
        mRightIcon.setImageDrawable(mRightDrawable);
    }

    public void setRightDrawableResId(@DrawableRes int resId) {
        mRightIcon.setImageResource(resId);
    }

    private int dpToPx(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }
}
