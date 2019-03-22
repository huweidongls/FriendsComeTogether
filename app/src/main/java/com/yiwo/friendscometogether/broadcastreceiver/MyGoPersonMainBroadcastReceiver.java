package com.yiwo.friendscometogether.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yiwo.friendscometogether.newpage.PersonMainActivity;

/**
 * Created by ljc on 2019/3/22.
 */

public class MyGoPersonMainBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        intent.putExtra("person_id",);
//        intent.putExtra("status","1");
        intent.setClass(context, PersonMainActivity.class);
        context.startActivity(intent);
    }
}
