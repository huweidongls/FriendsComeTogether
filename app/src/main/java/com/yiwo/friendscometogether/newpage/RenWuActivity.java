package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.newmodel.MyGroupListModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.EnterLiveActivity;
import com.yiwo.friendscometogether.webpages.RenWuWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

public class RenWuActivity extends BaseActivity {

    @BindView(R.id.tv_yi_xuan_huodong)
    TextView tvYiXuanZeHuoDong;
    @BindView(R.id.tv_yi_xuan_qishu)
    TextView tvYiXuanQiShu;
    private static final int REQUEST_CODE_XUAN_ZE_HUO_DONG =1;

    private DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDongModel;
    private SpImp spImp;
    private Dialog dialog;

    private UMShareAPI mShareAPI;
    private UMAuthListener umAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_wu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        yiXuanHuoDongModel = new DuiZhangXuanZeHuoDongModel.ObjBean();
        tvYiXuanZeHuoDong.setText("请选择活动");
        spImp = new SpImp(RenWuActivity.this);
        initUM();
    }
    @OnClick({R.id.activity_ren_wu_rl_back,R.id.ll_1,R.id.ll_2,R.id.ll_3,R.id.ll_4,R.id.ll_5,R.id.ll_6,R.id.ll_7,R.id.ll_8,
            R.id.tv_yi_xuan_huodong,R.id.ll_go_live,R.id.ll_set_live,R.id.ll_bind_wx})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_ren_wu_rl_back:
                onBackPressed();
                break;
            case R.id.ll_1:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_2:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_3:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_4:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_5:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_6:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/questionAnswerGame");//知识问答链接
                break;
            case R.id.ll_8:
                Intent intent = new Intent();
                intent.setClass(RenWuActivity.this,DuiZhangGameListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_7:
                if (yiXuanHuoDongModel == null|| TextUtils.isEmpty(yiXuanHuoDongModel.getPfID())||yiXuanHuoDongModel.getPfID() == null){
                    Intent itHuoDong = new Intent(RenWuActivity.this, DuiZhangXuanZeHuoDongActivity.class);
                    startActivityForResult(itHuoDong, REQUEST_CODE_XUAN_ZE_HUO_DONG);
                    break;
                }
                youxi();
                break;
            case R.id.tv_yi_xuan_huodong:
                Intent itHuoDong = new Intent(RenWuActivity.this, DuiZhangXuanZeHuoDongActivity.class);
                startActivityForResult(itHuoDong, REQUEST_CODE_XUAN_ZE_HUO_DONG);
                break;
            case R.id.ll_go_live:
                EnterLiveActivity.start(RenWuActivity.this);
                break;
            case R.id.ll_set_live:
                Intent intentLive = new Intent();
                intentLive.setClass(RenWuActivity.this,SetLiveActivity.class);
                startActivity(intentLive);
                break;
            case R.id.ll_bind_wx:
                mShareAPI.getPlatformInfo(RenWuActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);//绑定微信
//                mShareAPI.deleteOauth(RenWuActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);//撤销微信授权
                break;
        }
    }

    private void youxi() {
        Intent intent = new Intent(RenWuActivity.this,YouXiFenZuActivity.class);
        intent.putExtra("yiXuanHuoDongModel",yiXuanHuoDongModel);
        startActivity(intent);

    }

    private void startWeb(String string){
        Intent intent = new Intent();
        intent.setClass(RenWuActivity.this,RenWuWebActivity.class);
        intent.putExtra("url",string);
        startActivity(intent);
    }
    private void initUM() {
        mShareAPI = UMShareAPI.get(RenWuActivity.this);
        umAuthListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {}
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                final String wx_unionid ;
                if (data!=null && data.size()>0){
                    wx_unionid = data.get("unionid");
                    for (Map.Entry<String,String> entry : data.entrySet()){
                        Log.d("weixindenglu::://KEY:",entry.getKey()+"||Value:"+entry.getValue());
                    }
                    Log.d("weixindenglu:UNIONID",wx_unionid);
                    ViseHttp.POST(NetConfig.addWXUnionid)
                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.addWXUnionid))
                            .addParam("unionid",wx_unionid)
                            .addParam("uid",spImp.getUID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
                                            toToast(RenWuActivity.this,"绑定成功！");
                                            if (TextUtils.isEmpty(spImp.getWXUnionID())){
                                                spImp.setWXUnionID(wx_unionid);
                                            }
                                            Log.d("weixindenglu:UNSPIMP:",spImp.getWXUnionID());
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
                // Logger.e("openid: " + data.get("uid"));
                // Logger.e("昵称: " + data.get("name"));
                // Logger.e("头像: " + data.get("iconurl"));
                // Logger.e("性别: " + data.get("gender"));
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        };

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(RenWuActivity.this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_XUAN_ZE_HUO_DONG && resultCode == 1){
            yiXuanHuoDongModel = (DuiZhangXuanZeHuoDongModel.ObjBean) data.getSerializableExtra("xuanzehuodong");
            if (yiXuanHuoDongModel != null){
                tvYiXuanZeHuoDong.setText(yiXuanHuoDongModel.getPftitle());
                tvYiXuanQiShu.setText(yiXuanHuoDongModel.getPhase_num()+" ["+yiXuanHuoDongModel.getPhase_begin_time().substring(5,yiXuanHuoDongModel.getPhase_begin_time().length())+"] ");
            }
        }
    }
}
