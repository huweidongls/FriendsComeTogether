package com.yiwo.friendscometogether.broadcastreceiver;

/**
 * Created by ljc on 2019/4/4.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;

/**
 *
 * 以下这些方法运行在非 UI 线程中, 与华为 SDK 的 PushReceiver 方法一一对应。
 * 当开发者自身也接入华为推送，则应将继承 PushReceiver 改为继承 HWPushMessageReceiver，其他不变
 */

public class HWPushMessageReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(Context context, Intent intent) {
    }

    public void onToken(Context context, String token, Bundle extras) {
    }

    public boolean onPushMsg(Context context, byte[] raw, Bundle bundle) {
        return false;
    }

    public void onEvent(Context context, PushReceiver.Event event, Bundle extras) {
    }

    public void onPushState(Context context, boolean pushState) {
    }
}