package com.yiwo.friendscometogether.vas_sonic;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceResponse;

import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicSessionClient;
import com.tencent.sonic.sdk.SonicUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by ljc on 2019/10/23.
 * SonicRuntime类主要提供sonic运行时环境，包括Context、用户UA、ID(用户唯一标识，存放数据时唯一标识对应用户)等等信息。以下代码展示了SonicRuntime的几个方法。
 */

public class TBSonicRuntime extends com.tencent.sonic.sdk.SonicRuntime {
    public TBSonicRuntime(Context context) {
        super(context);
    }

    @Override
    public void log(String tag, int level, String message) {
        switch (level) {
            case Log.ERROR:
                Log.e(tag, message);
                break;
            case Log.INFO:
                Log.i(tag, message);
                break;
            default:
                Log.d(tag, message);
        }
    }

    @Override
    public String getCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    @Override
    public boolean setCookie(String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size() > 0) {
            CookieManager cookieManager = CookieManager.getInstance();
            for (String cookie : cookies) {
                cookieManager.setCookie(url, cookie);
            }
            return true;
        }
        return false;
    }

    @Override
    public String makeSessionId(String url, boolean isAccountRelated) {
        if (isSonicUrl(url)) {
//            StringBuilder sessionIdBuilder = new StringBuilder();
//            try {
//                Uri uri = Uri.parse(url);
//                sessionIdBuilder.append(uri.getAuthority()).append(uri.getPath());
//                if (uri.isHierarchical()) {
//                    String sonicRemainParams = uri.getQueryParameter(SonicConstants.SONIC_REMAIN_PARAMETER_NAMES);
//                    TreeSet<String> remainParamTreeSet = new TreeSet<String>();
//                    if (!TextUtils.isEmpty(sonicRemainParams)) {
//                        Collections.addAll(remainParamTreeSet, sonicRemainParams.split(SonicConstants.SONIC_REMAIN_PARAMETER_SPLIT_CHAR));
//                    }
//
//                    TreeSet<String> parameterNamesTreeSet = new TreeSet<String>(getQueryParameterNames(uri));
//                    if (!remainParamTreeSet.isEmpty()) {
//                        parameterNamesTreeSet.remove(SonicConstants.SONIC_REMAIN_PARAMETER_NAMES);
//                    }
//
//                    for (String parameterName : parameterNamesTreeSet) {
//                        if (!TextUtils.isEmpty(parameterName) && (parameterName.startsWith(SonicConstants.SONIC_PARAMETER_NAME_PREFIX) || remainParamTreeSet.contains(parameterName))) {
//                            sessionIdBuilder.append(parameterName).append(uri.getQueryParameter(parameterName));
//                        }
//                    }
//                }
//            } catch (Throwable e) {
//                log("makeSonicID", Log.ERROR, "makeSessionId error:" + e.getMessage() + ", url=" + url);
//                sessionIdBuilder.setLength(0);
//                sessionIdBuilder.append(url);
//            }
            String sessionId;
            if (isAccountRelated) {
                sessionId = getCurrentUserAccount() + "_" + SonicUtils.getMD5(url);
            } else {
                sessionId = SonicUtils.getMD5(url);
            }
            Log.d("TongBanSonicSessionID：",sessionId);
            return sessionId;
        }
        return null;
    }

    @Override
    public String getUserAgent() {
        return "TongBanUserAgent";
    }

    @Override
    public String getCurrentUserAccount() {
        return "TongBan";
    }

    @Override
    public boolean isSonicUrl(String url) {
        return true;
    }

    @Override
    public Object createWebResourceResponse(String mimeType, String encoding, InputStream data, Map<String, String> headers) {
        WebResourceResponse resourceResponse =  new WebResourceResponse(mimeType, encoding, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.setResponseHeaders(headers);
        }
        return resourceResponse;
    }

    @Override
    public boolean isNetworkValid() {
        return true;
    }

    @Override
    public void showToast(CharSequence text, int duration) {

    }

    @Override
    public void postTaskToThread(Runnable task, long delayMillis) {
        Thread thread = new Thread(task, "SonicThread");
        thread.start();
    }

    @Override
    public void notifyError(SonicSessionClient client, String url, int errorCode) {

    }

    @Override
    public File getSonicCacheDir() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator         + "tbSonic/";
        File file = new File(path.trim());
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }

    @Override
    public void postTaskToSessionThread(Runnable task) {
        TBSonicSessionThreadPool.postTask(task);
    }

    @Override
    public File getSonicResourceCacheDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "/tbSonicResource/");
        if (!file.exists() && !file.mkdir()) {
            notifyError(null, file.getAbsolutePath(), SonicConstants.ERROR_CODE_MAKE_DIR_ERROR);
        }
        return file;
    }
}
