package com.yiwo.friendscometogether.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.activecard.CardConfig;
import com.yiwo.friendscometogether.activecard.OnSwipeListener;
import com.yiwo.friendscometogether.activecard.OverLayCardLayoutManager;
import com.yiwo.friendscometogether.activecard.RenRenCallback;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.SwipeFIingViewAdapter;
import com.yiwo.friendscometogether.newmodel.HuoDongShaiXuanMode;
import com.yiwo.friendscometogether.newpage.YoujuShaixuanActivity;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.swipecard.SwipeFlingView;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/20.
 */

public class FriendsTogetherFragment2 extends BaseFragment {

    @BindView(R.id.swipe_view)
    SwipeFlingView sfv;
    @BindView(R.id.iv_youju_focus)
    ImageView ivFocus;
    private List<FriendsTogethermodel.ObjBean> mList = new ArrayList<>() ;
    private SwipeFIingViewAdapter adapter;

    private SpImp spImp;
    private String uid = "";
    // 筛选价格区间、标签、商家名称
    private String min = "";
    private String max = "";
    private String price = "";
    private String label = "";

    private FriendsTogethermodel.ObjBean bean;
    private Dialog dialog;

    private List<FriendsTogethermodel.ObjBean> tList = new ArrayList<>();// 临时list
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_together2, null);

        StatusBarUtils.setStatusBar(getActivity(), Color.parseColor("#d84c37"));
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);

        spImp = new SpImp(getContext());
        initSwipe();
        initData();
        return view;
    }
    private void initSwipe() {
        adapter = new SwipeFIingViewAdapter(getContext(),mList);
        sfv.setAdapter(adapter);
        sfv.refresh_view();
        sfv.setOnSwipeFlingListener(new SwipeFlingView.OnSwipeFlingListener() {
            @Override
            public void onStartDragCard() {

            }

            @Override
            public boolean canLeftCardExit() {
                return mList.size()>1?true:false;
            }

            @Override
            public boolean canRightCardExit() {
                return mList.size()>1?true:false;
            }

            @Override
            public void onPreCardExit() {

            }

            @Override
            public void onLeftCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
                int i =(int)dataObject;
                Log.d("mlist的Size",mList.size()+"");

                if (i>=0&&i<mList.size())
                bean = mList.get(i);
                if (bean.getFocusOn().equals("0")) {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                } else {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                }
//                if (i>0) {
//                    FriendsTogethermodel.ObjBean model = mList.remove(i-1);//划出的数据删除掉
//                    mList.add(mList.size()-1,model);
//                    adapter.notifyDataSetChanged();
//                }

            }

            @Override
            public void onRightCardExit(View view, Object dataObject, boolean triggerByTouchMove) {
                int i =(int)dataObject;
                Log.d("mlist的Size",mList.size()+"//"+i);
                if (i>=0&&i<mList.size())
                bean = mList.get(i);
                if (bean.getFocusOn().equals("0")) {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                } else {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                }
//              if (i>0) {
//                FriendsTogethermodel.ObjBean model = mList.remove(i-1);//划出的数据删除掉
//                mList.add(mList.size()-1,model);
//                adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onSuperLike(View view, Object dataObject, boolean triggerByTouchMove) {

            }

            @Override
            public void onTopCardViewFinish() {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//                List<FriendsTogethermodel.ObjBean> tList = new ArrayList<>();
//                tList.addAll(mList);
//                mList.clear();
//                mList.addAll(tList);
//                if (mList.size()<20){
//                    mList.addAll(tList);
//                    adapter.notifyDataSetChanged();
//                }else {
//                    initData();
//                }
                try {
                    mList.addAll(tList);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    initData();
                }

            }

            @Override
            public void onAdapterEmpty() {

            }

            @Override
            public void onScroll(View selectedView, float scrollProgressPercent) {

            }

            @Override
            public void onEndDragCard() {

            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
//            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
//            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    private void initData() {
         min = "";
         max = "";
         label = "";
        dialog = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
        String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
        ViseHttp.POST(NetConfig.friendsTogetherUrl)
                .addParam("app_key", token)
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(final String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Log.e("222", data);
                                final FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                tList.clear();
                                tList.addAll(model.getObj());
                                while (mList.size()>0 &&mList.size()<=4){//如果活动列表长度大于0，并且小于4时添加至四张以上
                                    mList.addAll(tList);
                                }
                                if (mList.size() > 0) {
                                    bean = mList.get(0);
                                    if (bean.getFocusOn().equals("0")) {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                    }
                                }
                                updateListView();
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        } catch (JSONException e) {
                            WeiboDialogUtils.closeDialog(dialog);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                });

    }

    @OnClick({R.id.iv_shaixuan, R.id.iv_baoming, R.id.iv_youju_focus, R.id.rl_shuaxin,R.id.rl_fanhui})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_fanhui:
                sfv.selectComeBackCard(true);
                Log.d("sadasdsadsa",sfv.getCurPositon()+"");
                if (sfv.getCurPositon()>=0&&sfv.getCurPositon()<mList.size()){
                    bean = mList.get(sfv.getCurPositon());
                    if (bean.getFocusOn().equals("0")) {
                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                    } else {
                        Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                    }
                }

                break;
            case R.id.iv_shaixuan:
                intent.setClass(getContext(), YoujuShaixuanActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_baoming:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent1 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent1);
                } else {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        break;
                    }
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
                                                                    it.putExtra("Pfexplain",model.getObj().getPfexplain());
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
            case R.id.iv_youju_focus:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        break;
                    }
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", uid)
                            .addParam("pfID", bean.getPfID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getInt("code") == 200) {
                                            FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                            if (model.getCode() == 200) {
                                                if (model.getObj().equals("1")) {
                                                    bean.setFocusOn("1");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                                    Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    bean.setFocusOn("0");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                                    Toast.makeText(getContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else if (jsonObject.getInt("code") == 400) {
                                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_shuaxin:
//                String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
//                ViseHttp.POST(NetConfig.friendsTogetherUrl)
//                        .addParam("app_key", token)
//                        .addParam("userID", spImp.getUID())
//                        .request(new ACallback<String>() {
//                            @Override
//                            public void onSuccess(String data) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(data);
//                                    if (jsonObject.getInt("code") == 200) {
//                                        Log.e("222", data);
//                                        FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
//                                        mList.clear();
//                                        mList.addAll(model.getObj());
//                                        if(mList.size()>0){
//                                            bean = mList.get(0);
//                                            if (bean.getFocusOn().equals("0")) {
//                                                Glide.with(getContext()).load(R.mipmap.youju_heart_kong).into(ivFocus);
//                                            } else {
//                                                Glide.with(getContext()).load(R.mipmap.my_focus).into(ivFocus);
//                                            }
//                                        }
//                                        updateListView(mList);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onFail(int errCode, String errMsg) {
//
//                            }
//                        });
                initData();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            HuoDongShaiXuanMode mode = (HuoDongShaiXuanMode) data.getSerializableExtra("shaiXuanMode");
            if (mode == null) return;
            dialog = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
            ViseHttp.POST(NetConfig.friendsTogetherUrl)
                    .addParam("app_key", token)
                    .addParam("page", "1")
                    .addParam("userID", spImp.getUID())
                    .addParam("price", mode.getJiaGe())
                    .addParam("sign", mode.getBiaoQian())
                    .addParam("shop_name", mode.getShangJiaName())
                    .addParam("shop_recommend",mode.getShangJiaTuiJian())
                    .addParam("city",mode.getCity())
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
                                    tList.clear();
                                    tList.addAll(model.getObj());
                                    while (mList.size()>0 && mList.size()<=4){
                                        mList.addAll(tList);
                                    }
                                    if (mList.size() > 0) {
                                        bean = mList.get(0);
                                        if (bean.getFocusOn().equals("0")) {
                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                        } else {
                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                        }
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    }
                                    updateListView();
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            } catch (JSONException e) {
                                WeiboDialogUtils.closeDialog(dialog);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    });
        }
//        min = data.getStringExtra("min");
//        max = data.getStringExtra("max");
//        label = data.getStringExtra("label");
//        price = min + "," + max;
//        if (resultCode == 2) {//筛选条件 有价格、无分类
//            min = data.getStringExtra("min");
//            max = data.getStringExtra("max");
//            price = min + "," + max;
//            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
//            ViseHttp.POST(NetConfig.friendsTogetherUrl)
//                    .addParam("app_key", token)
//                    .addParam("page", "1")
//                    .addParam("userID", spImp.getUID())
//                    .addParam("price", price)
//                    .request(new ACallback<String>() {
//                        @Override
//                        public void onSuccess(String data) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(data);
//                                if (jsonObject.getInt("code") == 200) {
//                                    Log.e("222", data);
//                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
//                                    mList.clear();
//                                    mList.addAll(model.getObj());
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
//                                    if(mList.size()>0){
//                                        bean = mList.get(0);
//                                        if (bean.getFocusOn().equals("0")) {
//                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
//                                        } else {
//                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
//                                        }
//                                    }
//                                    updateListView();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(int errCode, String errMsg) {
//
//                        }
//                    });
//        } else if (resultCode == 3) {//筛选条件 有价格、有分类
//            min = data.getStringExtra("min");
//            max = data.getStringExtra("max");
//            label = data.getStringExtra("label");
//            price = min + "," + max;
//            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
//            ViseHttp.POST(NetConfig.friendsTogetherUrl)
//                    .addParam("app_key", token)
//                    .addParam("page", "1")
//                    .addParam("userID", spImp.getUID())
//                    .addParam("price", price)
//                    .addParam("sign", label)
//                    .request(new ACallback<String>() {
//                        @Override
//                        public void onSuccess(String data) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(data);
//                                if (jsonObject.getInt("code") == 200) {
//                                    Log.e("222", data);
//                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
//                                    mList.clear();
//                                    mList.addAll(model.getObj());
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
//                                    if(mList.size()>0){
//                                        bean = mList.get(0);
//                                        if (bean.getFocusOn().equals("0")) {
//                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
//                                        } else {
//                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
//                                        }
//                                    }
//                                    updateListView();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(int errCode, String errMsg) {
//
//                        }
//                    });
//        } else if (resultCode == 5) {//筛选条件 无价格、有分类
//            label = data.getStringExtra("label");
//            String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
//            ViseHttp.POST(NetConfig.friendsTogetherUrl)
//                    .addParam("app_key", token)
//                    .addParam("page", "1")
//                    .addParam("userID", spImp.getUID())
//                    .addParam("sign", label)
//                    .request(new ACallback<String>() {
//                        @Override
//                        public void onSuccess(String data) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(data);
//                                if (jsonObject.getInt("code") == 200) {
//                                    Log.e("222", data);
//                                    FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
//                                    mList.clear();
//                                    mList.addAll(model.getObj());
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
//                                    if(mList.size()>0){
//                                        bean = mList.get(0);
//                                        if (bean.getFocusOn().equals("0")) {
//                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
//                                        } else {
//                                            Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
//                                        }
//                                    }
//                                    updateListView();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(int errCode, String errMsg) {
//
//                        }
//                    });
//        }else if (resultCode == 7){//没有选择任何条件
//            initData();
//        }
    }
    private void updateListView() {
//        adapter.data = mList;
        sfv.refresh_view();//必须使用此方法  更新view
        adapter.notifyDataSetChanged();
        initSwipe();
    }
}
