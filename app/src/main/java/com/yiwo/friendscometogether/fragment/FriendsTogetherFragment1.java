package com.yiwo.friendscometogether.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.activecard.CardConfig;
import com.yiwo.friendscometogether.activecard.CardItemTouchHelperCallback;
import com.yiwo.friendscometogether.activecard.CardLayoutManager;
import com.yiwo.friendscometogether.activecard.OnSwipeListener;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newpage.YoujuShaixuanActivity;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/20.
 */

public class FriendsTogetherFragment1 extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    private CardAdapter adapter;
    private List<FriendsTogethermodel.ObjBean> mList;

    private SpImp spImp;

    private FriendsTogethermodel.ObjBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_together1, null);

        StatusBarUtils.setStatusBar(getActivity(), Color.parseColor("#FB473F"));
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);

        spImp = new SpImp(getContext());

        return view;
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    private void initData() {

        String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
        ViseHttp.POST(NetConfig.friendsTogetherUrl)
                .addParam("app_key", token)
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(final String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.e("222", data);
                                final FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                mList = model.getObj();
                                bean = mList.get(0);
                                adapter = new CardAdapter(mList);
                                rv.setItemAnimator(new DefaultItemAnimator());
                                rv.setAdapter(adapter);
                                CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(rv.getAdapter(), mList);
                                cardCallback.setOnSwipedListener(new OnSwipeListener() {
                                    @Override
                                    public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

                                    }

                                    @Override
                                    public void onSwiped(RecyclerView.ViewHolder viewHolder, FriendsTogethermodel.ObjBean o, int direction) {
                                        bean = o;
                                        if(direction == CardConfig.SWIPED_NONE){
                                            final Intent intent = new Intent();
                                            if (TextUtils.isEmpty(bean.getPfpwd())) {
                                                intent.setClass(getContext(), DetailsOfFriendTogetherActivity.class);
                                                intent.putExtra("pfID", bean.getPfID());
                                                startActivity(intent);
                                            } else {
                                                LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(getContext(), new LookPasswordDialog.SetPasswordListener() {
                                                    @Override
                                                    public void setActivityText(String s) {
                                                        if (s.equals(bean.getPfpwd())) {
                                                            intent.setClass(getContext(), DetailsOfFriendTogetherActivity.class);
                                                            intent.putExtra("pfID", bean.getPfID());
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                                lookPasswordDialog.show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onSwipedClear() {

                                    }
                                });
                                ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
                                CardLayoutManager cardLayoutManager = new CardLayoutManager(rv, touchHelper);
                                rv.setLayoutManager(cardLayoutManager);
                                touchHelper.attachToRecyclerView(rv);
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

    @OnClick({R.id.iv_shaixuan, R.id.iv_baoming})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv_shaixuan:
                intent.setClass(getContext(), YoujuShaixuanActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_baoming:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent1 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent1);
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
                                            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherDetailsUrl);
                                            ViseHttp.POST(NetConfig.friendsTogetherDetailsUrl)
                                                    .addParam("app_key", token)
                                                    .addParam("pfID", bean.getPfID())
                                                    .addParam("userID", spImp.getUID())
                                                    .request(new ACallback<String>() {
                                                        @Override
                                                        public void onSuccess(String data) {
                                                            Log.e("222", data);
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(data);
                                                                if (jsonObject.getInt("code") == 200) {
                                                                    FriendsTogetherDetailsModel model = new Gson().fromJson(data, FriendsTogetherDetailsModel.class);
                                                                    Intent it = new Intent(getContext(), ApplyActivity.class);
                                                                    it.putExtra("if_pay", model.getObj().getIf_pay());
                                                                    it.putExtra("title", model.getObj().getTitle());
                                                                    it.putExtra("begin_time", model.getObj().getBegin_time());
                                                                    it.putExtra("price", model.getObj().getPrice());
                                                                    it.putExtra("pfID", model.getObj().getPfID());
                                                                    it.putExtra("name", model.getObj().getTruename());
                                                                    it.putExtra("sex", model.getObj().getPeoplesex());
                                                                    it.putExtra("age", model.getObj().getAge());
                                                                    it.putExtra("pic", model.getObj().getShow_pic());
                                                                    it.putExtra("issingle", model.getObj().getMarry());
                                                                    it.putExtra("city", model.getObj().getCity());
                                                                    it.putExtra("tel", model.getObj().getUser_tel());
                                                                    startActivity(it);
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFail(int errCode, String errMsg) {

                                                        }
                                                    });
                                        } else if (models.getObj().getOk().equals("1")) {
                                            toToast(getContext(), "请于身份审核通过后报名");
                                        } else {
                                            startActivity(new Intent(getContext(), RealNameActivity.class));
                                        }
                                    } else {
                                        toToast(getContext(), models.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 2){
            String min = data.getStringExtra("min");
            String max = data.getStringExtra("max");
            String price = min+","+max;
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("price", price)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    bean = mList.get(0);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else if(resultCode == 3){
            String min = data.getStringExtra("min");
            String max = data.getStringExtra("max");
            String label = data.getStringExtra("label");
            String price = min+","+max;
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("price", price)
                    .addParam("sign", label)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    bean = mList.get(0);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else if(resultCode == 5){
            String label = data.getStringExtra("label");
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("sign", label)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Log.e("222", data);
                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                    mList.clear();
                                    mList.addAll(model.getObj());
                                    bean = mList.get(0);
                                    adapter.notifyDataSetChanged();
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
    }
}
