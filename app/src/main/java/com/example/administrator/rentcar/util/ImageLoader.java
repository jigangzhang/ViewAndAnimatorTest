package com.example.administrator.rentcar.util;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/9/14 14:40
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.util
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/9/14 14:40
 * @description: <p>
 * <p>
 * </p>
 */

public class ImageLoader {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(new ColorDrawable(Color.MAGENTA))
                .error(new ColorDrawable(Color.GRAY))
                .fallback(new ColorDrawable(Color.RED))     //一般在url为空时使用
                .transform(new FitCenter(context))
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(100, 100)
                .into(imageView);
    }

    public static void clearImage(Activity activity) {
        Glide.with(activity)
                .onDestroy();
    }
}
