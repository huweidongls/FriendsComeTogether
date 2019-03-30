package com.yiwo.friendscometogether.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ljc on 2019/3/30.
 */

public class MyShenQingJinQunBroadcastReceiver  extends BroadcastReceiver{
    private SpImp spImp;
    @Override
    public void onReceive(final Context context, Intent intent) {
        spImp = new SpImp(context);
        final String content = intent.getStringExtra("content");
        final String group_id = intent.getStringExtra("group_id");

        ViseHttp.POST(NetConfig.inGroupInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.inGroupInfo))
                .addParam("uid", spImp.getUID())
                .addParam("group_id",group_id)
                .addParam("info",content)
                .request(new ACallback<String>() {

                    @Override
                    public void onSuccess(String data) {
                        Log.d("12212",data+"///"+group_id+"///"+content);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Toast.makeText(context,"已申请",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }
    public String getToken(String url){
        String token = StringUtils.stringToMD5(url);
        String tokens = StringUtils.stringToMD5(token);
        return tokens;
    }
}
