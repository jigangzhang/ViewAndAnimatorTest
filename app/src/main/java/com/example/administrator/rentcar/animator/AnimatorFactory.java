package com.example.administrator.rentcar.animator;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/8/11 9:13
 */

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AnimatorRes;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.administrator.rentcar.view.CircleProgressView;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.animator
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/8/11 9:13
 * @description: <p>
 * <p>
 * </p>
 */

public class AnimatorFactory {

    public ValueAnimator CreateValueAnimator(final View view, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(duration * 1000).setTarget(view);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (view instanceof TextView)
                    ((TextView) view).setText(value + "%");
                if (view instanceof CircleProgressView) {
                    ((CircleProgressView) view).setPercent((int) value);
                }
            }
        });
        valueAnimator.start();
        return valueAnimator;
    }

    public ObjectAnimator createObjectAnimator(View view, String animatorType, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, animatorType, 0, 100);
        objectAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        objectAnimator.setDuration(duration);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        objectAnimator.start();
        return objectAnimator;
    }

    public void createAnimatorFromXml(Context context, View view, @AnimatorRes int resId, long duration) {
        Animator animator = AnimatorInflater.loadAnimator(context, resId);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setTarget(view);
        animator.start();
    }
}
