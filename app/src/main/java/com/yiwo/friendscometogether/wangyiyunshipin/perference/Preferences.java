package com.yiwo.friendscometogether.wangyiyunshipin.perference;

import android.content.Context;
import android.content.SharedPreferences;

import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;

/**
 * Created by hzxuwen on 2015/4/13.
 */
public class Preferences {
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_USER_SID = "sid";
    private static final String KEY_REMEMBER_ACCOUNT_TOKEN = "remember_account_token";
    private static final String KEY_LOGIN_STATE = "login_state";
    private static final String KEY_VOD_TOKEN = "vodtoken";

    public static void saveVodToken(String vodtoken) {
        saveString(KEY_VOD_TOKEN, vodtoken);
    }

    public static String getVodToken() {
        return getString(KEY_VOD_TOKEN);
    }

    public static void saveLoginState(boolean loginState) {
        saveBoolean(KEY_LOGIN_STATE, loginState);
    }

    public static boolean getLoginState() {
        return getBoolean(KEY_LOGIN_STATE);
    }

    public static void saveRememberAccountToken(Boolean needRember) {
        saveBoolean(KEY_REMEMBER_ACCOUNT_TOKEN, needRember);
    }

    public static boolean getRememberAccountToken() {
        return getBoolean(KEY_REMEMBER_ACCOUNT_TOKEN);
    }

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    public static void saveUserSid(String sid){
        saveString(KEY_USER_SID, sid);
    }

    public static String getUserSid(){
        return getString(KEY_USER_SID);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    static SharedPreferences getSharedPreferences() {
        return DemoCache.getContext().getSharedPreferences("Demo", Context.MODE_PRIVATE);
    }
}
