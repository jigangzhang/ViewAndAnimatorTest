package com.example.administrator.rentcar.view;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/26 11:36
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.view
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/26 11:36
 * @description: <p>
 * <p>
 * </p>
 */

public class CircleProgressView extends View {
    private int size;
    private int percent;
    private String text;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //    setWillNotDraw(false);
    }

    private Paint getPaint(int color, float strokeWidth, Paint.Style style) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(style);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(color);
        return mPaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        size = width < height ? width : height;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(getPaint(Color.DKGRAY, 10, Paint.Style.STROKE));
        float paintWidth = paint.getStrokeWidth();
        float radius = size / 2 - paint.getStrokeWidth() / 2;
        canvas.drawCircle(size / 2, size / 2, radius, paint);

        float startAngle = percent * 360 / 100;
        RectF rectF = new RectF(paintWidth / 2, paintWidth / 2, size - paintWidth / 2, size - paintWidth / 2);
        paint.setColor(Color.GREEN);
        canvas.drawArc(rectF, -90, startAngle, false, paint);

        text = percent + "%";
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(48);
        float textWidth = paint.measureText(text, 0, text.length());
        float x = getWidth() / 2 - textWidth / 2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        float y = getHeight() / 2 + dy;
        canvas.drawText(text, x, y, paint);

    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
        invalidate();
    }
}
