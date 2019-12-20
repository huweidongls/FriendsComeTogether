package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment;

import android.content.Context;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.MyGiftsModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.adapter.GiftAdapter;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.chatroom.helper.ChatRoomMemberCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftConstant;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftType;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.helper.GiftAnimation;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.helper.GiftCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.model.Gift;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.widget.PeriscopeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.yiwo.friendscometogether.R2.id.count;
import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by zhukkun on 1/6/17.
 */
public class LiveBottomBar extends RelativeLayout {

    private View btn_msg;
    private View btn_gift;
    private View btn_capture;
    private View btn_share;

    private View btn_filter;
    private View btn_music;
    private View btn_like;
    private View btn_send_gift;

    /**
     * 滤镜相关控件
     */
    private LinearLayout layout_filter;
    private LinearLayout layout_filter_content;
    private View filter_layout_empty;
    private TextView btn_no_filter;
    private TextView btn_beauty_filter;

    private ViewGroup giftLayout; // 礼物布局
    private GridView giftView; // 礼物列表
    private LinearLayout llGiltNum;// 弹出、隐藏 礼物数量按钮
    private TextView tvGiftNum;// 已选择礼物数量
    private LinearLayout llChooseGiftNum;// 选择礼物数量布局
    private RelativeLayout rlNumOther,rlNum1314,rlNum520,rlNum188,rlNum66,rlNum30,rlNum10,rlNum1;
    private LinearLayout llNumKeyboard;
    private TextView tvNo0,tvNo1,tvNo2,tvNo3,tvNo4,tvNo5,tvNo6,tvNo7,tvNo8,tvNo9,tvNosure,tvNodel;
//    private TextView
    private RelativeLayout giftAnimationViewDown; // 礼物动画布局1
    private RelativeLayout giftAnimationViewUp; // 礼物动画布局2
    private GiftAdapter adapter;
    private GiftAnimation giftAnimation; // 礼物动画
    private TextView noGiftText;
    private List<Gift> giftList = new ArrayList<>(); // 礼物列表数据

    private PeriscopeLayout periscopeLayout; // 点赞爱心布局.

    //分享布局
    private RelativeLayout rl_share;
    private LinearLayout ll_share_content;
    private Button btn_http;
    private Button btn_hls;
    private Button btn_rtmp;
    private Button btn_share_cancel;

    // 发送礼物频率控制使用
    private long lastClickTime = 0;
    boolean isAudience;
    private String roomId;
    private int giftPosition = -1;

    private SendGiftListen sendGiftListen;
    private SpImp spImp;
    private ChatRoomInfo roomInfo;
    private Context context;
    public LiveBottomBar(Context context, boolean isAudience, String roomId) {
        super(context);
        this.isAudience = isAudience;
        this.roomId = roomId;
        this.context =context;
        spImp = new SpImp(context);
        int resourceId = isAudience? R.layout.layout_live_audience_bottom_bar : R.layout.layout_live_captrue_bottom_bar;
        LayoutInflater.from(context).inflate(resourceId, this, true);
        NIMClient.getService(ChatRoomService.class).fetchRoomInfo(roomId).setCallback(new RequestCallback<ChatRoomInfo>() {
            @Override
            public void onSuccess(ChatRoomInfo chatRoomInfo) {
                roomInfo = chatRoomInfo;
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        initView();
    }

    private void initView() {
        bindView();
        initGiftLayout();
        loadGift();
        clickView();
    }

    private void bindView() {
        // 点赞的爱心布局
        periscopeLayout = findView(R.id.periscope);
        rl_share = findView(R.id.share_layout);
        ll_share_content = findView(R.id.ll_share_content);
        btn_http = findView(R.id.btn_share_http);
        btn_hls  = findView(R.id.btn_share_hls);
        btn_rtmp = findView(R.id.btn_share_rtmp);
        btn_share_cancel = findView(R.id.btn_share_cancel);
        if(isAudience) {
            btn_msg = findView(R.id.audience_message);
            btn_gift = findView(R.id.audience_gift);
            btn_capture = findView(R.id.audience_screen_btn);
            btn_share = findView(R.id.audience_share);
            btn_like = findView(R.id.audience_like);
        }else{
            btn_gift = findView(R.id.live_gift);
            btn_msg = findView(R.id.live_message);
            btn_filter = findView(R.id.live_filter);
            btn_music = findView(R.id.live_music_btn);
            btn_capture = findView(R.id.live_screen_btn);
            btn_share = findView(R.id.live_share);

            layout_filter = findView(R.id.ll_filter_operate);
            layout_filter_content = findView(R.id.filter_layout_content);
            filter_layout_empty = findView(R.id.filter_layout_empty);
            btn_no_filter = findView(R.id.btn_no_filter);
            btn_beauty_filter = findView(R.id.btn_beauty_filter);

            btn_no_filter.setSelected(true);
        }
        btn_share.setVisibility(GONE);//隐藏分享
    }

    // 初始化礼物布局
    protected void initGiftLayout() {
        giftLayout = findView(R.id.gift_layout);
        giftView = findView(R.id.gift_grid_view);

        giftAnimationViewDown = findView(R.id.gift_animation_view);
        giftAnimationViewUp = findView(R.id.gift_animation_view_up);

        giftAnimation = new GiftAnimation(giftAnimationViewDown, giftAnimationViewUp);

        giftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                giftLayout.setVisibility(View.GONE);
                if (isAudience){
                    llChooseGiftNum.setVisibility(GONE);
                    llNumKeyboard.setVisibility(GONE);
                }
                giftPosition = -1;
            }
        });
        if(isAudience) {//送礼物有关视图
            bindSendGiftNum();
            btn_send_gift = findView(R.id.send_gift_btn);
            btn_send_gift.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (giftPosition == -1){
                        Toast.makeText(getContext(), "请选择礼物", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d("送礼物1：：",GiftType.typeOfValue(giftPosition)+"///"+GiftType.typeOfValue(giftPosition).getValue()+"//"+roomInfo.getCreator());

                    List<String> accounts = new ArrayList<>();
                    accounts.add(DemoCache.getAccount());
                    NIMClient.getService(ChatRoomService.class).fetchRoomMembersByIds(roomId,accounts).setCallback(new RequestCallback<List<ChatRoomMember>>() {
                        @Override
                        public void onSuccess(List<ChatRoomMember> chatRoomMembers) {
                            if (chatRoomMembers.get(0).isMuted()){
                                Toast.makeText(DemoCache.getContext(), "用户被禁言,无法发送礼物", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d("送礼物2：：",GiftType.typeOfValue(giftPosition)+"///"+GiftType.typeOfValue(giftPosition).getValue()+"//"+roomInfo.getCreator());
                            ViseHttp.POST(NetConfig.sendPresent)
                                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.sendPresent))
                                    .addParam("uid", spImp.getUID())
                                    .addParam("name",GiftType.typeOfValue(giftPosition).getValue()+"")
                                    .addParam("num",1+"")
                                    .addParam("touid",roomInfo.getCreator())
                                    .request(new ACallback<String>() {
                                        @Override
                                        public void onSuccess(String data) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                if (jsonObject.getInt("code") == 200){
                                                    sendGift();
                                                }else {
                                                    Toast.makeText(context,"赠送失败!",Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFail(int errCode, String errMsg) {
                                            Toast.makeText(context,"赠送失败!("+errMsg+")",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        @Override
                        public void onFailed(int i) {

                        }

                        @Override
                        public void onException(Throwable throwable) {

                        }
                    });
                }
            });
            adapter = new GiftAdapter(giftList, getContext());
//            adapter = new GiftAdapter(getContext()); //观众的礼物数量也从接口获取
            giftView.setAdapter(adapter);
            giftView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    giftPosition = position;
                    for(int i=0;i<giftList.size();i++){
                        View v=parent.getChildAt(i);
                        if (position == i) {//当前选中的Item改变背景颜色
                            giftList.get(position).setChoosed(true);
                        } else {
                            giftList.get(i).setChoosed(false);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }else{
            adapter = new GiftAdapter(giftList, getContext());
            giftView.setAdapter(adapter);
            noGiftText = findView(R.id.no_gift_tip);
        }
    }

    private void bindSendGiftNum() {
        tvGiftNum = findView(R.id.tv_gift_num);
        llGiltNum = findView(R.id.ll_gilt_num);
        llChooseGiftNum = findView(R.id.ll_choose_gift_num);
//        rlNumOther,rlNum1314,rlNum520,rlNum188,rlNum66,rlNum30,rlNum10,rlNum1;
        rlNumOther = findView(R.id.rl_num_other);
        rlNum1314 = findView(R.id.rl_num_1314);
        rlNum520 = findView(R.id.rl_num_520);
        rlNum188 = findView(R.id.rl_num_188);
        rlNum66 = findView(R.id.rl_num_66);
        rlNum30 = findView(R.id.rl_num_30);
        rlNum10 = findView(R.id.rl_num_10);
        rlNum1 = findView(R.id.rl_num_1);
//        tv_num0,tv_num1,tv_num2,tv_num3,tv_num4,tv_num5,tv_num6,tv_num7,tv_num8,tv_num9,tv_num_sure,tv_num_del;
        tvNo0 = findView(R.id.tv_no0);
        tvNo1 = findView(R.id.tv_no1);
        tvNo2 = findView(R.id.tv_no2);
        tvNo3 = findView(R.id.tv_no3);
        tvNo4 = findView(R.id.tv_no4);
        tvNo5 = findView(R.id.tv_no5);
        tvNo6 = findView(R.id.tv_no6);
        tvNo7 = findView(R.id.tv_no7);
        tvNo8 = findView(R.id.tv_no8);
        tvNo9 = findView(R.id.tv_no9);
        tvNodel = findView(R.id.tv_no_del);
        tvNosure = findView(R.id.tv_no_sure);

        llNumKeyboard = findView(R.id.ll_num_keyboard);

        llGiltNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llNumKeyboard.setVisibility(GONE);
                llChooseGiftNum.setVisibility(llChooseGiftNum.getVisibility() == VISIBLE?GONE:VISIBLE);
            }
        });
        rlNum1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(1+"");
            }
        });
        rlNum10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(10+"");
            }
        });
        rlNum30.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(30+"");
            }
        });
        rlNum66.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(66+"");
            }
        });
        rlNum188.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(188+"");
            }
        });
        rlNum520.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(520+"");
            }
        });
        rlNum1314.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(1314+"");
            }
        });
        rlNumOther.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGiftNum.setText(0+"");
                llChooseGiftNum.setVisibility(GONE);
                llNumKeyboard.setVisibility(llNumKeyboard.getVisibility() == VISIBLE?GONE:VISIBLE);
            }
        });
        tvNo0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(0+"");
                }else {
                    tvGiftNum.append(0+"");
                }
            }
        });
        tvNo1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(1+"");
                }else {
                    tvGiftNum.append(1+"");
                }
            }
        });
        tvNo2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(2+"");
                }else {
                    tvGiftNum.append(2+"");
                }
            }
        });
        tvNo3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(3+"");
                }else {
                    tvGiftNum.append(3+"");
                }
            }
        });
        tvNo4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(4+"");
                }else {
                    tvGiftNum.append(4+"");
                }
            }
        });
        tvNo5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(5+"");
                }else {
                    tvGiftNum.append(5+"");
                }
            }
        });
        tvNo6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(6+"");
                }else {
                    tvGiftNum.append(6+"");
                }
            }
        });
        tvNo7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(7+"");
                }else {
                    tvGiftNum.append(7+"");
                }
            }
        });
        tvNo8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(8+"");
                }else {
                    tvGiftNum.append(8+"");
                }
            }
        });
        tvNo9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvGiftNum.getText().length()>=4){
                    tvGiftNum.setText(9999+"");
                    Toast.makeText(context,"土豪，一次最多只能送出9999个呦～",Toast.LENGTH_SHORT).show();
                }else if (tvGiftNum.getText().length() == 1 && tvGiftNum.getText().toString().equals("0")){
                    tvGiftNum.setText(9+"");
                }else {
                    tvGiftNum.append(9+"");
                }
            }
        });
        tvNosure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llChooseGiftNum.setVisibility(GONE);
                llNumKeyboard.setVisibility(GONE);
            }
        });
        tvNodel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_num = tvGiftNum.getText().toString();
                if (str_num.length()>1){
                    tvGiftNum.setText(tvGiftNum.getText().subSequence(0,str_num.length()-1));
                }else {
                    tvGiftNum.setText(0+"");
                }
            }
        });
    }

    private void clickView() {
        btn_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_share.setVisibility(VISIBLE);
            }
        });

        rl_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_share.setVisibility(GONE);
            }
        });

        ll_share_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法 用于拦截点击事件
            }
        });

        btn_http.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUrl(DemoCache.getRoomInfoEntity().getHttpPullUrl());
                rl_share.setVisibility(GONE);
            }
        });

        btn_hls.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUrl(DemoCache.getRoomInfoEntity().getHlsPullUrl());
                rl_share.setVisibility(GONE);
            }
        });

        btn_rtmp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUrl(DemoCache.getRoomInfoEntity().getRtmpPullUrl());
                rl_share.setVisibility(GONE);
            }
        });

        btn_share_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_share.setVisibility(GONE);
            }
        });

        btn_gift.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showGiftLayout();
            }
        });

        if(isAudience){
            btn_like.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isFastClick()) {
                        periscopeLayout.addHeart();
                        sendLike();
                    }
                }
            });
        }else{
            btn_filter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_filter.setVisibility(VISIBLE);
                }
            });

            filter_layout_empty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_filter.setVisibility(View.GONE);
                }
            });

            layout_filter_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // empty
                }
            });
        }
    }

    private void shareUrl(String url) {
        try {
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(url);
            Toast.makeText(getContext(), getContext().getString(R.string.share_tip), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 更新收到礼物的数量
    private int updateGiftCount(GiftType type,boolean isAdd) {
        if (isAdd) {//如果为收到礼物
            for (Gift gift : giftList) {//遍历礼物列表
                if (type == gift.getGiftType()) {//如果含有此类型礼物
                    gift.setCount(gift.getCount() + 1);//数量加一
                    return 0;//接收到已收到过的的礼物类型
                }
            }
            return 1;//没有收到过此类型礼物
        }else {//如果为送出礼物
            for (Gift gift : giftList) {//遍历礼物列表
                if (type == gift.getGiftType()&&gift.getCount()>0) {//如果有此类型礼物
                    gift.setCount(gift.getCount() - 1);//礼物数量减一
                    return 2;//正常送出 礼物数量减一
                }
            }
            return 3;//不能送出此类型礼物  因为礼物列表中没有此类型礼物
        }

    }

    public void updateGiftList(GiftType type,Boolean isAdd) {
        if (isAdd){//收到礼物
            if (updateGiftCount(type,isAdd) == 1) { //没有收到过此类型礼物
                giftList.add(new Gift(type, GiftConstant.titles[type.getValue()], 1, GiftConstant.images[type.getValue()],GiftConstant.integrals[type.getValue()]));//礼物列表新增类型 数量1
            }
            adapter.notifyDataSetChanged();
            GiftCache.getInstance().saveGift(roomId, type.getValue());
        }else {
            if (updateGiftCount(type,isAdd) == 2){;//送出礼物成功

            }
            adapter.notifyDataSetChanged();
            GiftCache.getInstance().rmove1Gift(roomId,type.getValue());
        }

    }

    // 取出缓存的礼物
    private void loadGift() {
//        Map gifts = GiftCache.getInstance().getGift(roomId);
//        if (gifts == null) {
//            return;
//        }
//        Iterator<Map.Entry<Integer, Integer>> it = gifts.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<Integer, Integer> entry = it.next();
//            int type = entry.getKey();
//            int count = entry.getValue();
//            giftList.add(new Gift(GiftType.typeOfValue(type), GiftConstant.titles[type], count, GiftConstant.images[type]));
//        }
        ViseHttp.POST(NetConfig.presentList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.presentList))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                MyGiftsModel myGiftsModel = gson.fromJson(data,MyGiftsModel.class);
                                for (MyGiftsModel.ObjBean bean :myGiftsModel.getObj()){
                                    giftList.add(new Gift(GiftType.typeOfValue(Integer.parseInt(bean.getState())), GiftConstant.titles[Integer.parseInt(bean.getState())], Integer.parseInt(bean.getNum()),
                                            GiftConstant.images[Integer.parseInt(bean.getState())], GiftConstant.integrals[Integer.parseInt(bean.getState())]));
                                    Collections.sort(giftList, new Comparator<Gift>() {
                                        @Override
                                        public int compare(Gift o1, Gift o2) {
                                            int diff = o1.getGiftType().getValue() - o2.getGiftType().getValue();
                                            if (diff>0){
                                                return 1;
                                            }else if (diff<0){
                                                return -1;
                                            }else {
                                                return 0;
                                            }
                                        }
                                    });
                                }
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

    // 显示礼物列表
    private void showGiftLayout() {
        giftLayout.setVisibility(View.VISIBLE);
        if(!isAudience){
            if(adapter.getCount() == 0){
                // 暂无礼物
                noGiftText.setVisibility(View.VISIBLE);
            }else{
                noGiftText.setVisibility(View.GONE);
            }
        }
    }

    // 发送礼物
    private void sendGift() {
        if (giftPosition == -1) {
            Toast.makeText(getContext(), "请选择礼物", Toast.LENGTH_SHORT).show();
            return;
        }
        giftLayout.setVisibility(View.GONE);
        final GiftAttachment attachment = new GiftAttachment(GiftType.typeOfValue(giftPosition), 1);
        final ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomCustomMessage(roomId, attachment);
        setMemberType(message);
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        Log.d("asdasdasdasd","ddddddaaaaaa");
                        updateGiftList(attachment.getGiftType(),false);
                        giftAnimation.showGiftAnimation(message);
                        if (sendGiftListen!=null){
                            sendGiftListen.send(attachment.getGiftType());
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言,无法发送礼物", Toast.LENGTH_SHORT).show();
                        } else if (code == ResponseCode.RES_CHATROOM_ROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "全体禁言,无法发送礼物", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                    }
                });

//        giftPosition = -1; // 发送完毕，置空

//        if (giftPosition<4){//暂时开启四个礼物真实发送
//
//        }

    }

    // 显示礼物动画
    public void showGiftAnimation(ChatRoomMessage msg) {
        giftAnimation.showGiftAnimation(msg);
    }

    public void setMsgClickListener(OnClickListener onClickListener) {
        btn_msg.setOnClickListener(onClickListener);
    }

    public void setCaptureClickListener(OnClickListener onClickListener) {
        btn_capture.setOnClickListener(onClickListener);
    }

    public void setMusicClickListener(OnClickListener onClickListener) {
        if(!isAudience){
            btn_music.setOnClickListener(onClickListener);
        }
    }

    /*************************
     * 点赞爱心
     ********************************/

    // 发送点赞爱心
    private void sendLike() {
        LikeAttachment attachment = new LikeAttachment();
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomCustomMessage(roomId, attachment);
        setMemberType(message);
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言,无法点赞", Toast.LENGTH_SHORT).show();
                        } else if (code == ResponseCode.RES_CHATROOM_ROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "全体禁言,无法点赞", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // 发送爱心频率控制
    private boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime;
        if (time > 0 && time < 1000) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    private void setMemberType(ChatRoomMessage message) {
        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }
    }

    public void addHeart() {
        if(periscopeLayout!=null){
            periscopeLayout.addHeart();
        }
    }

    public View getFilterView(){
        return btn_filter;
    }

    public View getBeautyFilterBtn() {
        return btn_beauty_filter;
    }

    public View getNoFilterBtn(){
        return btn_no_filter;
    }

    public View getCaptureView(){
        return btn_capture;
    }

    protected <T extends View> T findView(int id){
        return (T)findViewById(id);
    }

    public void setSendGiftListen(SendGiftListen sendGiftListen) {
        this.sendGiftListen = sendGiftListen;
    }

    /**
     * 送礼物监听 用于更新礼物大动画
     */
    public interface SendGiftListen{
        void send(GiftType type);
    }
}
