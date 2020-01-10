package com.yiwo.friendscometogether.newfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.Paymodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.network.UMConfig;
import com.yiwo.friendscometogether.newadapter.TongBiPayRankAdapter;
import com.yiwo.friendscometogether.newadapter.TongBiPriceAdapter;
import com.yiwo.friendscometogether.newmodel.PayRankTongBiModel;
import com.yiwo.friendscometogether.newmodel.TongBiPriceModel;
import com.yiwo.friendscometogether.newpage.PayRankActivity;
import com.yiwo.friendscometogether.pages.UserAgreementActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by ljc on 2019/12/31.
 */

public class PayRankFragment extends BaseFragment {


    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout ;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_my_tb_num)
    TextView tvMyTbNum;
    @BindView(R.id.ll_go_chongzhi)
    LinearLayout ll_go_chongzhi;
    private List<PayRankTongBiModel.ObjBean> mList;
    private TongBiPayRankAdapter adapter;
    private SpImp spImp;
    private int page = 1;

    private int paytype = 0;
    private PopupWindow PopupWindowPay;//支付pop
    private PopupWindow popupWindowPrice;//价格pop
    private List<TongBiPriceModel.ObjBean> tongBiPriceModelList = new ArrayList<>();
    //    private TongBiPriceModel [] tongBiPriceModels = new TongBiPriceModel[]{
//            new TongBiPriceModel("10","1.00"),
//            new TongBiPriceModel("300","30.00"),
//            new TongBiPriceModel("1080","1080.00"),
//            new TongBiPriceModel("5180","518.00"),
//            new TongBiPriceModel("10000","1000.00"),
//            new TongBiPriceModel("50000","5000.00")
//    };
    RecyclerView recyclerViewChongZhiPrice;
    GridLayoutManager managerChongZhiPrice;
    TongBiPriceAdapter adapterChongZhiPrice;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI api;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank_pay,null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        api = WXAPIFactory.createWXAPI(getContext(), null);
        initData();
        return view;
    }
    private void initData() {
        mList = new ArrayList<>();

        adapter = new TongBiPayRankAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        ViseHttp.POST(NetConfig.getUserIntegral)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.getUserIntegral))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("obj");
                                tvMyTbNum.setText(jsonObject1.getString("integral"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        ViseHttp.POST(NetConfig.buyIntegralList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.buyIntegralList))
                .addParam("page", "1")
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PayRankTongBiModel model = gson.fromJson(data,PayRankTongBiModel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                page = 2 ;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshMyIntegral();
                ViseHttp.POST(NetConfig.buyIntegralList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.buyIntegralList))
                        .addParam("page", "1")
                        .addParam("uid",spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PayRankTongBiModel model = gson.fromJson(data,PayRankTongBiModel.class);
                                        mList.clear();
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page = 2 ;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refreshLayout.finishRefresh(1000);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.buyIntegralList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.buyIntegralList))
                        .addParam("page", ""+page)
                        .addParam("uid",spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        PayRankTongBiModel model = gson.fromJson(data,PayRankTongBiModel.class);
                                        mList.addAll(model.getObj());
                                        adapter.notifyDataSetChanged();
                                        page ++ ;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                refreshLayout.finishLoadMore(1000);
                            }
                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishLoadMore(1000);
                            }
                        });
            }
        });
    }
    @OnClick({R.id.ll_go_chongzhi})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.ll_go_chongzhi:
                showPopupWindowTongBiPrice(tvMyTbNum.getText().toString());
                break;
        }
    }
    private void showPopupWindowTongBiPrice(String myTongBiNum){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_buy_tongbi_price,null);
        ScreenAdapterTools.getInstance().loadView(view);
        recyclerViewChongZhiPrice = view.findViewById(R.id.rv_tongbi_price);
        managerChongZhiPrice = new GridLayoutManager(getContext(),3);
        adapterChongZhiPrice = new TongBiPriceAdapter(tongBiPriceModelList, new TongBiPriceAdapter.ItemClickListen() {
            @Override
            public void onItemClick(int pos) {
                showPopupWindowTongBiPay(tongBiPriceModelList.get(pos));
            }
        });
        recyclerViewChongZhiPrice.setLayoutManager(managerChongZhiPrice);
        recyclerViewChongZhiPrice.setAdapter(adapterChongZhiPrice);
       ViseHttp.POST(NetConfig.payIntegralList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.payIntegralList))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                TongBiPriceModel model = gson.fromJson(data,TongBiPriceModel.class);
                                tongBiPriceModelList.clear();
                                tongBiPriceModelList.addAll(model.getObj());
                                adapterChongZhiPrice.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        TextView tv_pop_my_tongbi_num = view.findViewById(R.id.tv_pop_my_tongbi_num);
        tv_pop_my_tongbi_num.setText(myTongBiNum);
        TextView tv_chongzhi_xieyi = view.findViewById(R.id.tv_chongzhixieyi);
        String str = "充值代表阅读并同意<font color = '#d84c37'>瞳伴充值协议</font>";
        tv_chongzhi_xieyi.setText(Html.fromHtml(str));
        tv_chongzhi_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itA = new Intent(getContext(), UserAgreementActivity.class);
                itA.putExtra("title", "充值协议");
                itA.putExtra("url", NetConfig.chongZhiXieYiUrl);
                startActivity(itA);
            }
        });
        popupWindowPrice = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindowPrice.setTouchable(true);
        popupWindowPrice.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindowPrice.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindowPrice.setOutsideTouchable(true);
        popupWindowPrice.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindowPrice.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        popupWindowPrice.update();

        popupWindowPrice.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
    }
    private void showPopupWindowTongBiPay(final TongBiPriceModel.ObjBean bean){
        paytype = 0;
        popupWindowPrice.dismiss();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_buy_tongbi_pay,null);
        ScreenAdapterTools.getInstance().loadView(view);
        TextView tv_pop_chongzhi_pay_money,tv_pop_chongzhi_pay_tongbi_num;
        tv_pop_chongzhi_pay_money = view.findViewById(R.id.tv_pop_chongzhi_pay_money);
        tv_pop_chongzhi_pay_tongbi_num = view.findViewById(R.id.tv_pop_chongzhi_pay_tongbi_num);
        tv_pop_chongzhi_pay_money.setText("¥ "+bean.getMoney());
        tv_pop_chongzhi_pay_tongbi_num.setText(bean.getIntegral()+" 瞳币");
        RelativeLayout rl_close = view.findViewById(R.id.rl_close);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowPay.dismiss();
            }
        });
        Button btn_sure_pay = view.findViewById(R.id.btn_sure_pay);
        btn_sure_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPay(bean.getId(),paytype);
            }
        });
        final ImageView iv_wechat,iv_alipay;
        iv_wechat = view.findViewById(R.id.iv_check_wechat);
        iv_alipay = view.findViewById(R.id.iv_check_alipay);
        RelativeLayout rl_wechat = view.findViewById(R.id.rl_wechat);
        rl_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_wechat.setImageResource(R.mipmap.apply_true);
                iv_alipay.setImageResource(R.mipmap.apply_false);
                paytype = 0;
            }
        });
        RelativeLayout rl_alipay = view.findViewById(R.id.rl_alipay);
        rl_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_wechat.setImageResource(R.mipmap.apply_false);
                iv_alipay.setImageResource(R.mipmap.apply_true);
                paytype = 1;
            }
        });

        PopupWindowPay = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        PopupWindowPay.setTouchable(true);
        PopupWindowPay.setFocusable(true);
        // 设置点击窗口外边窗口消失
        PopupWindowPay.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PopupWindowPay.setOutsideTouchable(true);
        PopupWindowPay.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        PopupWindowPay.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        PopupWindowPay.update();

        PopupWindowPay.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });

    }
    private void goPay(String priceId,int type){
        Log.d("adasdasdas",priceId);
        ViseHttp.POST(NetConfig.buyIntegral)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.buyIntegral))
                .addParam("paytype",type+"")
                .addParam("uid",spImp.getUID())
                .addParam("id",priceId)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Paymodel paymodel = new Gson().fromJson(data, Paymodel.class);
                                toToast(getContext(), "微信支付");
                                wxPay(paymodel.getObj());
                            }else if(jsonObject.getInt("code") == 300){
                                toToast(getContext(), "支付宝支付");
                                Log.e("123123", jsonObject.optString("obj"));
                                aliPay(jsonObject.optString("obj"));
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
    public void wxPay(Paymodel.ObjBean model) {
        api.registerApp(UMConfig.WECHAT_APPID);
        PayReq req = new PayReq();
        req.appId = model.getAppId();
        req.partnerId = model.getPartnerId();
        req.prepayId = model.getPrepayId();
        req.nonceStr = model.getNonceStr();
        req.timeStamp = model.getTimestamp() + "";
        req.packageValue = model.getPackageX();
        req.sign = model.getSign();
        req.extData = "tongbi_chongzhi";
        api.sendReq(req);
        refreshMyIntegral();
    }

    private void refreshMyIntegral() {
        ViseHttp.POST(NetConfig.getUserIntegral)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.getUserIntegral))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("obj");
                                tvMyTbNum.setText(jsonObject1.getString("integral"));
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

    public void aliPay(String info) {
        final String orderInfo = info;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandlerPay.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandlerPay = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;

                    for (Map.Entry<String, String> entry : result.entrySet()) {
                        Log.d("dadaadad",entry.getKey() + ":" + entry.getValue());
                    }

                    if(result.get("resultStatus").equals("9000")){
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                        refreshMyIntegral();
//                        getActivity().finish();
                    }
                    break;
            }
        }

    };
}
