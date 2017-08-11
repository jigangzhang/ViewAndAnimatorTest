package com.example.administrator.rentcar.view;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/27 9:12
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.rentcar.R;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.view
 * @version: V1.0
 * @author: zjg
 * @date: 2017/7/27 9:12
 * @description: <p>
 * <p>
 * </p>
 */

public class CombineEditText extends LinearLayout {

    private static final String KEY_EDIT_CONTENT = "editContent";
    private static final String KEY_SAVED_SUPER = "savedSuper";
    private Drawable leftIcon;
    private Drawable rightIcon;
    private Drawable iconTextRight; //文字右边分割条
    private LayoutParams params;
    private String leftText;    //textView text
    private String hintText;    //EditText hint
    private String editText;
    private String content;
    private int width;

    private ImageView mRightImg;
    private ImageView mLeftImg;
    private ImageView mTextRightImg;
    private TextView mLeftText;     //左边文字
    private EditText mEditText;

    private int editGravity;
    private boolean leftTextVisible;    //设置是否包含左边文字，只在初始化时加入组件中，默认为false，即不包含文字，若需要，在xml文件中设置此项为true
    private boolean rightImgVisible;    //是否隐藏右边图标（×），默认为false，初始化时不可见，当输入文字时，可见
    private boolean editCursorVisible;
    private boolean hidePhoneNumber;    //默认为false，不隐藏数字
    private boolean editEnabled;        //EditText可不可以输入，默认为true，可输入
    private int imgMarginLeft;
    private int imgMarginTop;
    private int imgMarginRight;
    private int imgMarginBottom;
    private float innerTextSize;    //设置字体大小，暂时不可用
    private int editInputType;

    public CombineEditText(Context context) {
        this(context, null);
    }

    public CombineEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombineEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewData(context, attrs);
        initView(context);
    }

    private void initViewData(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CombineEditText);
        leftIcon = typedArray.getDrawable(R.styleable.CombineEditText_textLeftIcon);
        if (leftIcon == null) {
            leftIcon = context.getResources().getDrawable(R.drawable.logo_left);
        }
        rightIcon = typedArray.getDrawable(R.styleable.CombineEditText_editRightIcon);
        if (rightIcon == null) {
            rightIcon = context.getResources().getDrawable(R.drawable.logo_right);
        }
        iconTextRight = typedArray.getDrawable(R.styleable.CombineEditText_textRightIcon);
        if (iconTextRight == null) {
            iconTextRight = context.getResources().getDrawable(R.drawable.drawable_line);
        }
        leftText = typedArray.getString(R.styleable.CombineEditText_leftText);
        hintText = typedArray.getString(R.styleable.CombineEditText_editHintText);
        editText = typedArray.getString(R.styleable.CombineEditText_editText);

        leftTextVisible = typedArray.getBoolean(R.styleable.CombineEditText_leftTextVisible, false);
        rightImgVisible = typedArray.getBoolean(R.styleable.CombineEditText_rightIconVisible, false);
        editCursorVisible = typedArray.getBoolean(R.styleable.CombineEditText_editCursorVisible, true);
        hidePhoneNumber = typedArray.getBoolean(R.styleable.CombineEditText_hidePhoneNumber, false);
        editEnabled = typedArray.getBoolean(R.styleable.CombineEditText_editEnabled, true);

        editInputType = typedArray.getInt(R.styleable.CombineEditText_editInputType, 0);
        editGravity = typedArray.getInt(R.styleable.CombineEditText_editTextGravity, 8388611);  //8388611=gravity.start;    即：默认文字靠右

        width = typedArray.getDimensionPixelSize(R.styleable.CombineEditText_editWidth, -1);    //dp以经自动转换为px
        if (width < 0) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        imgMarginLeft = typedArray.getDimensionPixelSize(R.styleable.CombineEditText_innerImgMarginLeft, dpToPx(5));
        imgMarginTop = typedArray.getDimensionPixelSize(R.styleable.CombineEditText_innerImgMarginTop, dpToPx(5));
        imgMarginRight = typedArray.getDimensionPixelSize(R.styleable.CombineEditText_innerImgMarginRight, dpToPx(5));
        imgMarginBottom = typedArray.getDimensionPixelSize(R.styleable.CombineEditText_innerImgMarginBottom, dpToPx(5));
        innerTextSize = typedArray.getDimension(R.styleable.CombineEditText_innerTextSize, 14);
        Log.i("MyTest", "width=" + width + "  " + imgMarginLeft + " size:" + innerTextSize);
        typedArray.recycle();
    }

    private void initView(Context context) {
        //leftImg 初始化
        mLeftImg = new ImageView(context);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(imgMarginLeft, imgMarginTop, imgMarginRight, imgMarginBottom);
        params.gravity = Gravity.CENTER;
        mLeftImg.setLayoutParams(params);
        mLeftImg.setImageDrawable(leftIcon);
        addView(mLeftImg);

        //leftText and textRightImg 初始化
        if (leftTextVisible) {
            mLeftText = new TextView(context);
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //    params.setMargins(imgMarginLeft, imgMarginTop, imgMarginRight, imgMarginBottom);
            //    mLeftText.setTextSize(innerTextSize);
            mLeftText.setGravity(Gravity.CENTER);
            mLeftText.setLayoutParams(params);
            mLeftText.setText(leftText);
            addView(mLeftText);

            mTextRightImg = new ImageView(context);
            params = new LayoutParams(dpToPx(1), ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(imgMarginLeft, imgMarginTop, imgMarginRight, imgMarginBottom);
            params.gravity = Gravity.CENTER;
            mTextRightImg.setLayoutParams(params);
            mTextRightImg.setImageDrawable(iconTextRight);
            addView(mTextRightImg);
        }

        //EditText 初始化
        mEditText = new EditText(context);
        params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;  //自适应屏幕
        params.setMargins(imgMarginLeft, imgMarginTop, imgMarginRight, imgMarginBottom);
        params.gravity = Gravity.CENTER_VERTICAL;
        //    mEditText.setTextSize(innerTextSize);
        mEditText.setLayoutParams(params);
        if (editText != null) {
            mEditText.setText(editText);
        }

        mEditText.setSingleLine(true);
        mEditText.setInputType(editInputType | InputType.TYPE_CLASS_TEXT);  //输入类型
        mEditText.setHint(hintText);
        mEditText.setBackground(null);      //去掉EditText下划线
        mEditText.setCursorVisible(editCursorVisible);      //EditText的游标是否可见,默认可见
        mEditText.setGravity(editGravity | Gravity.CENTER);      //设置EditText文字位置

        mEditText.addTextChangedListener(new TextChangeListener());
        addView(mEditText);

        //rightImg 初始化
        mRightImg = new ImageView(context);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(imgMarginLeft, imgMarginTop, imgMarginRight, imgMarginBottom);
        params.gravity = Gravity.CENTER;
        mRightImg.setLayoutParams(params);
        if (!rightImgVisible) {
            mRightImg.setVisibility(INVISIBLE);
        }
        mRightImg.setImageDrawable(rightIcon);
        mRightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editEnabled) {
                    mEditText.setText("");
                    mRightImg.setVisibility(INVISIBLE);
                }
            }
        });
        addView(mRightImg);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            content = bundle.getString(KEY_EDIT_CONTENT);
            state = bundle.getParcelable(KEY_SAVED_SUPER);
            mEditText.setText(content);
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * 只有为该组合控件指定ID后，才会触发上下两个保存、恢复方法
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        content = mEditText.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SAVED_SUPER, super.onSaveInstanceState());
        bundle.putString(KEY_EDIT_CONTENT, content);
        return bundle;
    }

    private int dpToPx(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

    private class TextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mRightImg != null && editEnabled) {
                if (s.length() > 0) {
                    mRightImg.setVisibility(VISIBLE);
                } else
                    mRightImg.setVisibility(INVISIBLE);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //
            if (hidePhoneNumber) {
                if (s.length() == 11) {
                    mEditText.removeTextChangedListener(this);  // 去掉监听器，否则会出现栈溢出
                    editText = s.toString();
                    String text = "****";
                    s.replace(3, 7, text);
                    Log.i("MyTest", "editText = " + editText + "," + s);
                    mEditText.addTextChangedListener(this);
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        if (!editEnabled) {
            return true;
        }
        return result;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        if (mLeftText != null) {
            mLeftText.setText(leftText);
        }
    }

    public Drawable getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(Drawable leftIcon) {
        this.leftIcon = leftIcon;
    }

    public Drawable getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(Drawable rightIcon) {
        this.rightIcon = rightIcon;
    }

    public Drawable getIconTextRight() {
        return iconTextRight;
    }

    public void setIconTextRight(Drawable iconTextRight) {
        this.iconTextRight = iconTextRight;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        if (mEditText != null) {
            mEditText.setHint(hintText);
        }
    }

    public boolean isLeftTextVisible() {
        return leftTextVisible;
    }

    public void setLeftTextVisible(boolean hasLeftText) {   //设置是否包含左边文字，此为初始化时加入组件中，在此处设置true/false无意义
        this.leftTextVisible = hasLeftText;
    }

    public boolean isRightImgVisible() {
        return rightImgVisible;
    }

    public void setRightImgVisible(boolean rightImgVisible) {   //只在初始化时使用，在此处设置true/false无意义
        this.rightImgVisible = rightImgVisible;
    }

    public boolean isEditCursorVisible() {
        return editCursorVisible;
    }

    public void setEditCursorVisible(boolean editCursorVisible) {   //只在初始化时使用，在此处设置true/false无意义
        this.editCursorVisible = editCursorVisible;
    }

    public int getImgMarginLeft() {
        return imgMarginLeft;
    }

    public void setImgMarginLeft(int imgMarginLeft) {
        this.imgMarginLeft = imgMarginLeft;
        invalidate();
    }

    public int getImgMarginTop() {
        return imgMarginTop;
    }

    public void setImgMarginTop(int imgMarginTop) {
        this.imgMarginTop = imgMarginTop;
    }

    public int getImgMarginRight() {
        return imgMarginRight;
    }

    public void setImgMarginRight(int imgMarginRight) {
        this.imgMarginRight = imgMarginRight;
    }

    public int getImgMarginBottom() {
        return imgMarginBottom;
    }

    public void setImgMarginBottom(int imgMarginBottom) {
        this.imgMarginBottom = imgMarginBottom;
    }

    public float getInnerTextSize() {
        return innerTextSize;
    }

    public void setInnerTextSize(float innerTextSize) {
        this.innerTextSize = innerTextSize;
        if (mEditText != null) {
            mEditText.setTextSize(innerTextSize);
        }
    }

    public String getEditText() {
        return editText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
        if (mEditText != null) {
            mEditText.setText(editText);
        }
    }

    public int getEditInputType() {
        return editInputType;
    }

    public void setEditInputType(int editInputType) {
        this.editInputType = editInputType;
        if (mEditText != null) {
            mEditText.setInputType(editInputType);
        }
    }
}
