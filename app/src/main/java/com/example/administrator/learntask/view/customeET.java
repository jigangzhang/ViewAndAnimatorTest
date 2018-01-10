package com.example.administrator.learntask.view;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/28 15:17
 */

import android.content.Context;
import android.util.AttributeSet;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.learntask.view
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/28 15:17
 * @description: <p>
 * <p>
 * </p>
 */

public class customeET extends android.support.v7.widget.AppCompatEditText {
    public customeET(Context context) {
        super(context);
    }

    public customeET(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customeET(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean getDefaultEditable() {
        return false;
    }


}
