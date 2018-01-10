package com.example.administrator.learntask.livedata;

import android.arch.lifecycle.LiveData;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/1/10.
 * <p>
 * 可以使用singleton模式来扩展LiveData对象，
 * 以包装系统服务，以便它们可以在您的应用程序中共享。LiveData对象连接到系统服务一次，然后任何需要该资源的观察者都可以看到LiveData对象
 */

public class MainLiveData extends LiveData<BigDecimal> {

}
