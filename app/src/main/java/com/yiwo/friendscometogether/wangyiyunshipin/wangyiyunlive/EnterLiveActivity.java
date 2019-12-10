package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.BaseActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming.PublishParam;
import com.yiwo.friendscometogether.wangyiyunshipin.server.DemoServerHttpClient;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.RoomInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.model.ZhiBoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by zhukkun on 1/13/17.
 */
public class EnterLiveActivity extends BaseActivity {

    public static final String QUALITY_HD = "HD";
    public static final String QUALITY_SD = "SD";
    public static final String QUALITY_LD = "LD";

    private SwitchCompat switch_audio;
    private SwitchCompat switch_video;

    private View item_quality;
    private TextView tv_quality;

    private LinearLayout ll_quality_select;
    private View tv_hd, tv_sd, tv_ld, tv_cancel;
    private TextView tv_title;
    private View back;
    private Button btn_enter;

    private MsgReceiver msgReceiver;

    private String quality = QUALITY_SD;
    private String roomId;
    private String push_url;
    private boolean open_audio = true;
    private boolean open_video = true;
    private boolean useFilter = true; //默认开启滤镜
    private boolean faceBeauty = false; //默认关闭美颜

    private boolean stopLiveFinished = true;
    private boolean cancelEnterRoom = false;
    private SpImp spImp;
    /**   6.0权限处理     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EnterLiveActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EnterLiveActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EnterLiveActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EnterLiveActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(EnterLiveActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }
    }

    public static void start(Context context){
        Intent intent = new Intent(context, EnterLiveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //用户可能刚设置完权限回到Activity,  此时再次检查权限
        if(!bPermission) {
            bPermission = checkPublishPermission();
        }
    }

    @Override
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_enter_live;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LiveStreamingStopFinished");
        registerReceiver(msgReceiver, intentFilter);
        spImp = new SpImp(EnterLiveActivity.this);
        bPermission = checkPublishPermission();
    }

    @Override
    protected void initView() {
        bindView();
        clickView();

        //设置开关的颜色
        switch_audio.setThumbResource(R.drawable.switch_thumb);
        switch_audio.setTrackResource(R.drawable.switch_track);
        switch_video.setThumbResource(R.drawable.switch_thumb);
        switch_video.setTrackResource(R.drawable.switch_track);

        tv_title.setText("直播设置");

        setQualitySelected();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setQualitySelected() {
        switch (quality){
            case QUALITY_HD:
                tv_quality.setText("高清 >");
                break;
            case QUALITY_SD:
                tv_quality.setText("标清 >");
                break;
            case QUALITY_LD:
                tv_quality.setText("流畅 >");
                break;
        }
    }

    private void clickView() {
        switch_audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                open_audio = isChecked;
                checkBtnState();
            }
        });

        switch_video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                open_video = isChecked;
                checkBtnState();
            }
        });

        item_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_quality_select.setVisibility(View.VISIBLE);
            }
        });

        ll_quality_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_quality_select.setVisibility(View.GONE);
            }
        });

        tv_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality = QUALITY_HD;
                setQualitySelected();
                ll_quality_select.setVisibility(View.GONE);
            }
        });

        tv_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality = QUALITY_SD;
                setQualitySelected();
                ll_quality_select.setVisibility(View.GONE);
            }
        });

        tv_ld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality = QUALITY_LD;
                setQualitySelected();
                ll_quality_select.setVisibility(View.GONE);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_quality_select.setVisibility(View.GONE);
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLiveRoom();
            }
        });
    }

    private void createLiveRoom() {
        if(!open_video && !open_audio){
            showToast("需至少开启音频或视频中的一项");
            return;
        }
        //检测相机与录音权限
        if(!bPermission){
            showToast("请先允许app所需要的权限");
            AskForPermission();
            return;
        }
        cancelEnterRoom = false;
        DialogMaker.showProgressDialog(EnterLiveActivity.this, null, "创建房间中", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelEnterRoom = true;
            }
        }).setCanceledOnTouchOutside(false);
        ViseHttp.POST(NetConfig.zhiBo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.zhiBo))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ZhiBoModel model = gson.fromJson(data,ZhiBoModel.class);
                                roomId = model.getObj().getRoomId();
                                push_url = model.getObj().getPushUrl();
                                LogUtil.i(TAG, "live stream url:" + push_url);
                                RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
                                roomInfoEntity.setCid(model.getObj().getCid());
                                roomInfoEntity.setOwner(model.getObj().getCreator());
                                roomInfoEntity.setHlsPullUrl(model.getObj().getHlsPullUrl());
                                roomInfoEntity.setHttpPullUrl(model.getObj().getHttpPullUrl());
                                roomInfoEntity.setRtmpPullUrl(model.getObj().getRtmpPullUrl());
                                roomInfoEntity.setPushUrl(model.getObj().getPushUrl());
                                roomInfoEntity.setRoomid(Integer.parseInt(model.getObj().getRoomId()));

                                DemoCache.setRoomInfoEntity(roomInfoEntity);

                                PublishParam publishParam = new PublishParam();
                                publishParam.pushUrl = push_url;
                                publishParam.definition = quality;
                                publishParam.openVideo = open_video;
                                publishParam.openAudio = open_audio;
                                publishParam.useFilter = useFilter;
                                publishParam.faceBeauty = faceBeauty;
                                if(!cancelEnterRoom) {
                                    LiveRoomActivity.startLive(EnterLiveActivity.this, roomId, publishParam);
                                }
                                DialogMaker.dismissProgressDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        showToast(errMsg);
                        DialogMaker.dismissProgressDialog();
                    }
                });

//        DemoServerHttpClient.getInstance().createRoom(EnterLiveActivity.this, new DemoServerHttpClient.DemoServerHttpCallback<RoomInfoEntity>() {
//            @Override
//            public void onSuccess(RoomInfoEntity roomInfoEntity) {
//                roomId = roomInfoEntity.getRoomid()+"";
//                push_url = roomInfoEntity.getPushUrl();
//                LogUtil.i(TAG, "live stream url:" + push_url);
//                DemoCache.setRoomInfoEntity(roomInfoEntity);
//
//                PublishParam publishParam = new PublishParam();
//                publishParam.pushUrl = push_url;
//                publishParam.definition = quality;
//                publishParam.openVideo = open_video;
//                publishParam.openAudio = open_audio;
//                publishParam.useFilter = useFilter;
//                publishParam.faceBeauty = faceBeauty;
//                if(!cancelEnterRoom) {
//                    LiveRoomActivity.startLive(EnterLiveActivity.this, roomId, publishParam);
//                }
//                DialogMaker.dismissProgressDialog();
//            }
//
//            @Override
//            public void onFailed(int code, String errorMsg) {
//                showToast(errorMsg);
//                DialogMaker.dismissProgressDialog();
//            }
//        });
    }

    private void AskForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限设置");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }


    private void checkBtnState() {
        if(!open_audio && !open_video){
            btn_enter.setEnabled(false);
        }else if(stopLiveFinished){
            btn_enter.setEnabled(true);
        }
    }

    private void bindView() {
        switch_audio = findView(R.id.switch_audio);
        switch_video = findView(R.id.switch_video);
        item_quality = findView(R.id.item_quality);
        tv_quality = findView(R.id.tv_quality);

        ll_quality_select = findView(R.id.ll_quality_select);
        tv_hd = findView(R.id.tv_quality_hd);
        tv_sd = findView(R.id.tv_quality_sd);
        tv_ld = findView(R.id.tv_quality_ld);
        tv_cancel = findView(R.id.tv_quality_cancle);
        btn_enter= findView(R.id.btn_enter_room);
        tv_title = findView(R.id.tv_title);
        back = findView(R.id.iv_back);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);
        super.onDestroy();
    }

    //用于接收Service发送的消息
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int value = intent.getIntExtra("LiveStreamingStopFinished", 0);
            if(value == 1)//finished
            {
                stopLiveFinished = true;
                if(open_video||open_audio) {
                    btn_enter.setEnabled(true);
                }
                btn_enter.setText("确定");
            }
            else//not yet finished
            {
                stopLiveFinished = false;
                btn_enter.setEnabled(false);
                btn_enter.setText("直播停止中...");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(ll_quality_select.getVisibility()== View.VISIBLE) {
            ll_quality_select.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }
}
