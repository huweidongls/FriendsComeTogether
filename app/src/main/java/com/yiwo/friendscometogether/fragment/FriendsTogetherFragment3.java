package com.yiwo.friendscometogether.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.library.banner.BannerLayout;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.activecard.CardAdapter;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.model.FriendsTogethermodel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.FriendsTogetherFragmentLabelTopAdapter;
import com.yiwo.friendscometogether.newadapter.LabelAdapter;
import com.yiwo.friendscometogether.newadapter.SwipeFIingViewAdapter;
import com.yiwo.friendscometogether.newmodel.HuoDongShaiXuanMode;
import com.yiwo.friendscometogether.newmodel.YouJuTopLabelModel;
import com.yiwo.friendscometogether.newpage.YoujuShaixuanActivity;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.swipecard.SwipeFlingView;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/20.
 */

public class FriendsTogetherFragment3 extends BaseFragment {

    @BindView(R.id.recyclerbanner)
    BannerLayout recyclerbanner;
    @BindView(R.id.iv_youju_focus)
    ImageView ivFocus;
    @BindView(R.id.rv_label_1)
    RecyclerView rv_label_1;
    @BindView(R.id.rv_label_2)
    RecyclerView rv_label_2;
    @BindView(R.id.rl_label2)
    RelativeLayout rl_label2;
    private List<FriendsTogethermodel.ObjBean> mList = new ArrayList<>() ;
    private CardAdapter adapter;

    private SpImp spImp;
    private String uid = "";
    // 筛选价格区间、标签、商家名称
    private String min = "";
    private String max = "";
    private String price = "";
    private String label = "";

    private FriendsTogethermodel.ObjBean bean;
    private Dialog dialog;

    private List<UserLabelModel.ObjBean> labelList = new ArrayList<>();//所有label 的list
    private List<YouJuTopLabelModel> labelList_1 = new ArrayList<>();//第一行label 的list
    private List<YouJuTopLabelModel> labelList_2 = new ArrayList<>();//第二行label 的list
    private FriendsTogetherFragmentLabelTopAdapter labelTopAdapter1 = new FriendsTogetherFragmentLabelTopAdapter(labelList_1);
    private FriendsTogetherFragmentLabelTopAdapter labelTopAdapter2 = new FriendsTogetherFragmentLabelTopAdapter(labelList_2);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_together3, null);
        ButterKnife.bind(this, view);
        ScreenAdapterTools.getInstance().loadView(view);
        //第一种方式获取屏幕的像素宽高在Activity里面可使用
        //DisplayMetrics metric = new DisplayMetrics();
        //Activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        //第二种方式获取屏幕的像素宽高
        //获取屏幕的像素高度PIX
        double screenHeight = getResources().getDisplayMetrics().heightPixels;
        //获取屏幕的像素宽度PIX
        double screenWidth = getResources().getDisplayMetrics().widthPixels;
        BigDecimal b1 = new BigDecimal(Double.valueOf(screenHeight));
        BigDecimal b2 = new BigDecimal(Double.valueOf(screenWidth));
        Log.d("asdasd","screenHeight:"+screenHeight+"///screenWidth::"+screenWidth+"///"+(b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue()));
        spImp = new SpImp(getContext());
        initLabelTop();
        initData();
        return view;
    }

    private void initLabelTop() {
        /**
         * 判断屏幕比例 决定显示几排标签
         */
        boolean isChangPing = false;
        //获取屏幕的像素高度PIX
        double screenHeight = getResources().getDisplayMetrics().heightPixels;
        //获取屏幕的像素宽度PIX
        double screenWidth = getResources().getDisplayMetrics().widthPixels;
        BigDecimal b1 = new BigDecimal(Double.valueOf(screenHeight));
        BigDecimal b2 = new BigDecimal(Double.valueOf(screenWidth));
        if (b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue()>1.7778){
            Log.d("asdasd","长坪");
            isChangPing = true;
        }

        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_label_1.setLayoutManager(manager1);
        labelTopAdapter1.setOnSelelectedListionner(new FriendsTogetherFragmentLabelTopAdapter.OnSelelectedListionner() {
            @Override
            public void onSelect(int position) {
                for (YouJuTopLabelModel model:labelList_1){
                    model.setIstSelected(false);
                }
                labelList_1.get(position).setIstSelected(true);
                for (YouJuTopLabelModel model:labelList_2){
                    model.setIstSelected(false);
                }
                labelTopAdapter1.notifyDataSetChanged();
                labelTopAdapter2.notifyDataSetChanged();
                clickTopLabelChangeData(labelList_1.get(position).getId());
            }
        });
        rv_label_1.setAdapter(labelTopAdapter1);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_label_2.setLayoutManager(manager2);
        labelTopAdapter2.setOnSelelectedListionner(new FriendsTogetherFragmentLabelTopAdapter.OnSelelectedListionner() {
            @Override
            public void onSelect(int position) {
                for (YouJuTopLabelModel model:labelList_2){
                    model.setIstSelected(false);
                }
                labelList_2.get(position).setIstSelected(true);
                for (YouJuTopLabelModel model:labelList_1){
                    model.setIstSelected(false);
                }
                labelTopAdapter1.notifyDataSetChanged();
                labelTopAdapter2.notifyDataSetChanged();
                clickTopLabelChangeData(labelList_2.get(position).getId());
            }
        });
        rv_label_2.setAdapter(labelTopAdapter2);

        final boolean finalIsChangPing = isChangPing;
        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);
                                labelList = userLabelModel.getObj();
                                labelList_1.clear();
                                labelList_2.clear();
                                YouJuTopLabelModel modelFir = new YouJuTopLabelModel();
                                modelFir.setId("");
                                modelFir.setName("推荐");
                                modelFir.setIstSelected(true);
                                labelList_1.add(modelFir);
                                if (finalIsChangPing){
                                    for (int i =0;i<labelList.size()/2;i++){
                                        YouJuTopLabelModel model = new YouJuTopLabelModel();
                                        model.setId(labelList.get(i).getLID());
                                        model.setName(labelList.get(i).getLname());
                                        labelList_1.add(model);
                                    }
                                    for (int i =labelList.size()/2;i<labelList.size();i++){
                                        YouJuTopLabelModel model = new YouJuTopLabelModel();
                                        model.setId(labelList.get(i).getLID());
                                        model.setName(labelList.get(i).getLname());
                                        labelList_2.add(model);
                                    }
                                    labelTopAdapter2.notifyDataSetChanged();
                                    rl_label2.setVisibility(View.VISIBLE);
                                }else {
                                    for (int i =0;i<labelList.size();i++){
                                        YouJuTopLabelModel model = new YouJuTopLabelModel();
                                        model.setId(labelList.get(i).getLID());
                                        model.setName(labelList.get(i).getLname());
                                        labelList_1.add(model);
                                    }
                                    rl_label2.setVisibility(View.GONE);
                                }
                                labelTopAdapter1.notifyDataSetChanged();
                                labelTopAdapter2.notifyDataSetChanged();
//                                GridLayoutManager manager = new GridLayoutManager(getContext(), 3){
//                                    @Override
//                                    public boolean canScrollVertically() {
//                                        return false;
//                                    }
//                                };
//                                recyclerView.setLayoutManager(manager);
//                                adapter = new LabelAdapter(mList);
//                                recyclerView.setAdapter(adapter);
//                                adapter.setListener(new LabelAdapter.OnSelectLabelListener() {
//                                    @Override
//                                    public void onSelete(int i) {
//                                        labelList.add(mList.get(i).getLID());
//                                    }
//                                });
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

    private void initRecyclerBanner(){
        adapter = new CardAdapter(mList);
        adapter.setOnBottomButtonClickListionner(new CardAdapter.OnBottomButtonClickListionner() {
            @Override
            public void OnGuanZhuClick(final int postion) {
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        return;
                    }
                    ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                            .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                            .addParam("userID", uid)
                            .addParam("pfID", mList.get(postion).getPfID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getInt("code") == 200) {
                                            FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                            if (model.getCode() == 200) {
                                                if (model.getObj().equals("1")) {
                                                    mList.get(postion).setFocusOn("1");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                                    Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    mList.get(postion).setFocusOn("0");
                                                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                                    Toast.makeText(getContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                                }
                                                adapter.notifyDataSetChanged();
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
                    Intent intent = new Intent();
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void OnBaoMingClick(final int postion) {
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    Intent intent1 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent1);
                } else {
                    if (mList.size()<=0){
                        toToast(getContext(),"当前无活动");
                        return;
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
                                                    .addParam("pfID", mList.get(postion).getPfID())
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
            }

            @Override
            public void OnFenXiangClick(final int postion) {
                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(getActivity(), mList.get(postion).getShare_url(), mList.get(postion).getPftitle(),
                                        mList.get(postion).getPfcontent(), mList.get(postion).getPfpic(), share_media);
                            }
                        }).open();
            }
        });
        recyclerbanner.setAdapter(adapter);
        recyclerbanner.setMoveSpeed(1f);
        recyclerbanner.setCenterScale(1f);
        recyclerbanner.setAutoPlaying(false);
        recyclerbanner.setShowIndicator(false);
        recyclerbanner.setOnCurrentChangeListenner(new BannerLayout.OnCurrentChangeListenner() {
            @Override
            public void onCurrentChange(int position) {
                if (position>=0&&position<mList.size())
                    bean = mList.get(position);
                if (bean.getFocusOn().equals("0")) {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                } else {
                    Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                }
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
                                initRecyclerBanner();
//                                tList.clear();
//                                tList.addAll(model.getObj());
//                                while (mList.size()>0 &&mList.size()<=4){//如果活动列表长度大于0，并且小于4时添加至四张以上
//                                    mList.addAll(tList);
//                                }
                                if (mList.size() > 0) {
                                    bean = mList.get(0);
                                    if (bean.getFocusOn().equals("0")) {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_g).into(ivFocus);
                                    } else {
                                        Glide.with(getContext()).load(R.mipmap.guanzhu128_r).into(ivFocus);
                                    }
                                }
//                                updateListView();
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

    @OnClick({R.id.iv_shaixuan, R.id.iv_baoming, R.id.iv_youju_focus,R.id.rl_shaixuan})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
//            case R.id.iv_shaixuan:
//                intent.setClass(getContext(), YoujuShaixuanActivity.class);
//                startActivityForResult(intent, 1);
//                break;
            case R.id.rl_shaixuan:
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
//           //fragment关注按钮，已隐藏，在item中实现该功能
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
//            case R.id.rl_shuaxin:
////                String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
////                ViseHttp.POST(NetConfig.friendsTogetherUrl)
////                        .addParam("app_key", token)
////                        .addParam("userID", spImp.getUID())
////                        .request(new ACallback<String>() {
////                            @Override
////                            public void onSuccess(String data) {
////                                try {
////                                    JSONObject jsonObject = new JSONObject(data);
////                                    if (jsonObject.getInt("code") == 200) {
////                                        Log.e("222", data);
////                                        FriendsTogethermodel model = new Gson().fromJson(data, FriendsTogethermodel.class);
////                                        mList.clear();
////                                        mList.addAll(model.getObj());
////                                        if(mList.size()>0){
////                                            bean = mList.get(0);
////                                            if (bean.getFocusOn().equals("0")) {
////                                                Glide.with(getContext()).load(R.mipmap.youju_heart_kong).into(ivFocus);
////                                            } else {
////                                                Glide.with(getContext()).load(R.mipmap.my_focus).into(ivFocus);
////                                            }
////                                        }
////                                        updateListView(mList);
////                                    }
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////
////                            @Override
////                            public void onFail(int errCode, String errMsg) {
////
////                            }
////                        });
//                initData();
//                break;
        }
    }
    private void clickTopLabelChangeData(String biaoqianID){
        String token = getToken(NetConfig.BaseUrl + NetConfig.friendsTogetherUrl);
        ViseHttp.POST(NetConfig.friendsTogetherUrl)
                .addParam("app_key", token)
                .addParam("page", "1")
                .addParam("userID", spImp.getUID())
                .addParam("sign", biaoqianID)
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
                                initRecyclerBanner();
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
////                                    while (mList.size()>0 && mList.size()<=4){
////                                        mList.addAll(tList);
////                                    }
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
//                                    updateListView();
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
                    .addParam("activity_name",mode.getKeyWord())//关键字
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
                                    initRecyclerBanner();
//                                    tList.clear();
//                                    tList.addAll(model.getObj());
////                                    while (mList.size()>0 && mList.size()<=4){
////                                        mList.addAll(tList);
////                                    }
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
//                                    updateListView();
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
//        sfv.refresh_view();//必须使用此方法  更新view
        adapter.notifyDataSetChanged();
    }
}
