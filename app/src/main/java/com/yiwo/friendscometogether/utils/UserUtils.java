package com.yiwo.friendscometogether.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yiwo.friendscometogether.model.CityModel;

/**
 * Created by Administrator on 2018/7/27.
 */

public class UserUtils {
    //保存定位城市
    public static void saveCity(Context context, CityModel model){
        if(context!=null&&model!=null){
            SharedPreferences sp = context.getSharedPreferences("cityData", Context.MODE_PRIVATE);
            SharedPreferences.Editor et = sp.edit();
            et.putString("id",model.getId());
            et.putString("name",model.getName());
            et.commit();
        }
    }
    //读取定位城市
    public static CityModel readCity(Context context){
        SharedPreferences sp = context.getSharedPreferences("cityData", Context.MODE_PRIVATE);
        CityModel model = new CityModel();
        model.setId(sp.getString("id","-1"));
        model.setName(sp.getString("name","北京"));
        return model;
    }
}
