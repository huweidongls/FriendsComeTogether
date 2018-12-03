package com.yiwo.friendscometogether.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.yiwo.friendscometogether.utils.NetUtil;

/**
 * 自定义检查手机网络状态是否切换的广播接受器
 *
 * @author cj
 *
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetEvevt evevt;

    public NetBroadcastReceiver() {
    }

    public NetBroadcastReceiver(NetEvevt evevt) {
        this.evevt = evevt;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }

    // 自定义接口
    public interface NetEvevt {
        public void onNetChange(int netMobile);
    }
}
