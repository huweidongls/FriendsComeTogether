package com.yiwo.friendscometogether.webpages;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.MuLuItemYouJiAdapter;
import com.yiwo.friendscometogether.newadapter.MuLuItemYouJuAdapter;
import com.yiwo.friendscometogether.newmodel.YouJuWebModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailsOfFriendTogetherWebActivity extends BaseWebActivity {

    @BindView(R.id.activity_details_of_friends_together_iv_focus_on)
    ImageView focusOnIv;
    @BindView(R.id.wv)
    WebView webView;
    @BindView(R.id.rl_mulu)
    RelativeLayout rl_mulu;
    private Unbinder unbinder;
    SpImp spImp;
    private String uid;
    private String pfID;
    private String url;
    private int chooseDateIndex = 0;
    private YouJuWebModel model;
    private List<YouJuWebModel.ObjBean.TitleBean> listMuLu = new ArrayList<>();
    private PopupWindow popupWindow;
    private MuLuItemYouJuAdapter muLuItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_friend_together_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        spImp = new SpImp(DetailsOfFriendTogetherWebActivity.this);
        uid = spImp.getUID();
        pfID = getIntent().getStringExtra("pfID");
        url = NetConfig.BaseUrl+"action/ac_activity/youJuWeb?pfID="+pfID+"&uid="+uid;
        initWebView(webView,url);
        webView.addJavascriptInterface(new DetailsOfFriendTogetherWebActivity.AndroidInterface(),"android");//交互
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getActivityInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getActivityInfo))
                .addParam("id",pfID)
                .addParam("uid",uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code")==200){
                                Gson gson = new Gson();
                                model = gson.fromJson(data,YouJuWebModel.class);
                                listMuLu.clear();
                                listMuLu.addAll(model.getObj().getTitle());

                                if (listMuLu.size()>0){
                                    rl_mulu.setVisibility(View.VISIBLE);
                                }else {
                                    rl_mulu.setVisibility(View.GONE);
                                }
                                //底部按钮
                                //收藏
                                focusOnIv.setImageResource(model.getObj().getCollect().equals("0") ? R.mipmap.heart_red_border : R.mipmap.heart_red);
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
    @OnClick({R.id.activity_details_of_friends_together_rl_back,R.id.details_applyTv,R.id.activity_details_of_friends_together_ll_share,
                    R.id.activity_details_of_friends_together_ll_focus_on,R.id.consult_leaderLl,R.id.rl_mulu})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.activity_details_of_friends_together_rl_back:
                finish();
                break;
            case R.id.details_applyTv:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent = new Intent(DetailsOfFriendTogetherWebActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ViseHttp.POST(NetConfig.isRealNameUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.isRealNameUrl))
                            .addParam("userID", spImp.getUID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    Log.i("123321", data);
                                    IsRealNameModel models = new Gson().fromJson(data, IsRealNameModel.class);
                                    if (models.getCode() == 200) {
                                        if (models.getObj().getOk().equals("2")) {
                                            Intent it = new Intent(DetailsOfFriendTogetherWebActivity.this, ApplyActivity.class);
                                            it.putExtra("if_pay", model.getObj().getInfo().getPfspendtype());
                                            it.putExtra("title", model.getObj().getInfo().getPftitle());
                                            it.putExtra("pfID", model.getObj().getInfo().getPfID());
                                            it.putExtra("name", model.getObj().getInfo().getTruename());
                                            it.putExtra("sex", model.getObj().getInfo().getPeoplesex());
                                            it.putExtra("age", model.getObj().getInfo().getAge());
                                            it.putExtra("pic", model.getObj().getInfo().getPfpic());
                                            it.putExtra("issingle", model.getObj().getInfo().getMarry());
                                            it.putExtra("city", model.getObj().getInfo().getAddress());
                                            it.putExtra("tel", model.getObj().getInfo().getTel());
                                            it.putExtra("choose_date_intex",chooseDateIndex);
                                            it.putExtra("Pfexplain",model.getObj().getInfo().getOthers());
                                            startActivity(it);
                                        } else if (models.getObj().getOk().equals("1")) {
                                            toToast(DetailsOfFriendTogetherWebActivity.this, "请于身份审核通过后报名");
                                        } else {
                                            startActivity(new Intent(DetailsOfFriendTogetherWebActivity.this, RealNameActivity.class));
                                        }
                                    } else {
                                        toToast(DetailsOfFriendTogetherWebActivity.this, models.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }
                break;
            case R.id.activity_details_of_friends_together_ll_share:

                ViseHttp.POST(NetConfig.activeShareUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeShareUrl))
                        .addParam("id", pfID)
                        .addParam("type", "0")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        final ActiveShareModel shareModel = gson.fromJson(data, ActiveShareModel.class);
                                        new ShareAction(DetailsOfFriendTogetherWebActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                                .setShareboardclickCallback(new ShareBoardlistener() {
                                                    @Override
                                                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                        ShareUtils.shareWeb(DetailsOfFriendTogetherWebActivity.this, shareModel.getObj().getUrl(), shareModel.getObj().getTitle(),
                                                                shareModel.getObj().getDesc(), shareModel.getObj().getImages(), share_media);
                                                    }
                                                }).open();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });

                break;
            case R.id.activity_details_of_friends_together_ll_focus_on:
                String userID = spImp.getUID();
                if (spImp.getUID().equals("0") || TextUtils.isEmpty(spImp.getUID())) {
                    Intent intent1 = new Intent(DetailsOfFriendTogetherWebActivity.this, LoginActivity.class);
                    startActivity(intent1);
                } else {
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", userID)
                            .addParam("pfID", pfID)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getInt("code") == 200){
                                            FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                            if (model.getCode() == 200) {
                                                if (model.getObj().equals("1")) {
                                                    focusOnIv.setImageResource(R.mipmap.heart_red);
                                                    toToast(DetailsOfFriendTogetherWebActivity.this, "关注成功");
                                                } else {
                                                    focusOnIv.setImageResource(R.mipmap.heart_red_border);
                                                    toToast(DetailsOfFriendTogetherWebActivity.this, "取消成功");
                                                }
                                            }
                                        }else if(jsonObject.getInt("code") == 400) {
                                            toToast(DetailsOfFriendTogetherWebActivity.this, jsonObject.getString("message"));
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
                break;
            case R.id.consult_leaderLl:
                String uid = spImp.getUID();
                if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(DetailsOfFriendTogetherWebActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    liaotian(model.getObj().getInfo().getWy_accid());
                }
                break;
            case R.id.rl_mulu:
                showMuLuPopupwindow(rl_mulu);
                break;
        }
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void lookpostpicture(String json,String index){
//            [{"id":"958","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad155365614111.jpg","desc":""},
//              {"id":"959","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141238.jpg","desc":""},
//              {"id":"960","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141899.jpg","desc":""}]
            String strJson = new String(Base64.decode(json,Base64.DEFAULT));
            Log.d("交互了","json::"+json+"////\r\nindex::"+index);
            int position = Integer.parseInt(index);
            Log.d("交互解码了","json::"+strJson+"////\r\nindex::"+index);
            List<String> listPics = new ArrayList<>();
            List<String> listContent = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listPics.add(jsonObject.getString("imgurl"));
                    listContent.add(jsonObject.getString("desc"));
                }
                Intent intent1 = new Intent();
                intent1.setClass(DetailsOfFriendTogetherWebActivity.this, ImagePreviewActivity.class);
                intent1.putStringArrayListExtra("imageList", (ArrayList<String>) listPics);
                intent1.putExtra("hasImageContent",true);
                intent1.putStringArrayListExtra("imageContenList", (ArrayList<String>) listContent);
                intent1.putExtra(Consts.START_ITEM_POSITION, position);
                intent1.putExtra(Consts.START_IAMGE_POSITION, position);
                startActivity(intent1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @JavascriptInterface
        public void userinfo(String uid){
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendTogetherWebActivity.this, PersonMainActivity.class);
            intent.putExtra("person_id", uid);
            startActivity(intent);
        }
        @JavascriptInterface
        public void clickphase(String index){
            chooseDateIndex = Integer.parseInt(index);
        }
    }
    private void showMuLuPopupwindow(View p_view) {

        View view = LayoutInflater.from(DetailsOfFriendTogetherWebActivity.this).inflate(R.layout.popupwindow_web_mulu, null);
        ScreenAdapterTools.getInstance().loadView(view);
        RecyclerView recyclerView = view.findViewById(R.id.rv_mulu);
        LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendTogetherWebActivity.this);
        recyclerView.setLayoutManager(manager);
        muLuItemAdapter= new MuLuItemYouJuAdapter(listMuLu);
        muLuItemAdapter.setListener(new MuLuItemYouJuAdapter.ChooseListen() {
            @Override
            public void chooseItemId(String ID) {
                webView.loadUrl("javascript:jumptitle('"+ID+"')");
                popupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(muLuItemAdapter);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(p_view,0,0);
//        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.popwindow_anim_left_in_out);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1f;
        getWindow().setAttributes(params);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(DetailsOfFriendTogetherWebActivity.this, liaotianAccount);
    }
}
