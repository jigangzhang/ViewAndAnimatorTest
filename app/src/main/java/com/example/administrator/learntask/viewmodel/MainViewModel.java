package com.example.administrator.learntask.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.administrator.learntask.activity.MyApplication;
import com.example.administrator.learntask.entity.DaoSession;
import com.example.administrator.learntask.entity.User;
import com.example.administrator.learntask.entity.UserDao;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class MainViewModel extends ViewModel {
    MutableLiveData<List<User>> name;
    MutableLiveData<String> data;

    public LiveData<List<User>> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }

    public MutableLiveData<String> getData() {
        if (data == null)
            data = new MutableLiveData<>();
        return data;
    }

    public void getNetData() {

    }

    public void saveData(String name, int age) {
        //  greendao 数据库操作
        DaoSession session = MyApplication.getInstance().getSession();
        UserDao dao = session.getUserDao();
        User li = new User();
        li.setName(name);
        li.setAge(age);
        dao.save(li);     //增     数据库
    }

    public void queryData() {
        DaoSession session = MyApplication.getInstance().getSession();
        UserDao dao = session.getUserDao();
        User lee = dao.queryBuilder().where(UserDao.Properties.Name.eq("Lee")).build().unique();
        if (lee != null) {
            lee.setName("Blue");
//            dao.update(lee);       //改     数据库
        }
        List<User> users = dao.queryBuilder()
                .orderDesc(UserDao.Properties.Name)
                .list();            //查     数据库
        name.setValue(users);
    }

    public class MainFactory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel();
        }
    }
}
