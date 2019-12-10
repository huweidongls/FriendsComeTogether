package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment;

import android.content.Context;
import android.text.ClipboardManager;
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

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.adapter.GiftAdapter;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.chatroom.helper.ChatRoomMemberCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftConstant;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftType;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.helper.GiftAnimation;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.helper.GiftCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.model.Gift;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.widget.PeriscopeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public LiveBottomBar(Context context, boolean isAudience, String roomId) {
        super(context);
        this.isAudience = isAudience;
        this.roomId = roomId;
        int resourceId = isAudience? R.layout.layout_live_audience_bottom_bar : R.layout.layout_live_captrue_bottom_bar;
        LayoutInflater.from(context).inflate(resourceId, this, true);
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
                giftPosition = -1;
            }
        });

        if(isAudience) {
            btn_send_gift = findView(R.id.send_gift_btn);
            btn_send_gift.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendGift();
                }
            });

            adapter = new GiftAdapter(getContext());
            giftView.setAdapter(adapter);

            giftView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    giftPosition = position;
                }
            });
        }else{
            adapter = new GiftAdapter(giftList, getContext());
            giftView.setAdapter(adapter);
            noGiftText = findView(R.id.no_gift_tip);
        }
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
    private boolean updateGiftCount(GiftType type) {
        for (Gift gift : giftList) {
            if (type == gift.getGiftType()) {
                gift.setCount(gift.getCount() + 1);
                return true;
            }
        }
        return false;
    }

    public void updateGiftList(GiftType type) {
        if (!updateGiftCount(type)) {
            giftList.add(new Gift(type, GiftConstant.titles[type.getValue()], 1, GiftConstant.images[type.getValue()]));
        }
        adapter.notifyDataSetChanged();
        GiftCache.getInstance().saveGift(roomId, type.getValue());
    }

    // 取出缓存的礼物
    private void loadGift() {
        Map gifts = GiftCache.getInstance().getGift(roomId);
        if (gifts == null) {
            return;
        }
        Iterator<Map.Entry<Integer, Integer>> it = gifts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            int type = entry.getKey();
            int count = entry.getValue();
            giftList.add(new Gift(GiftType.typeOfValue(type), GiftConstant.titles[type], count, GiftConstant.images[type]));
        }
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
        GiftAttachment attachment = new GiftAttachment(GiftType.typeOfValue(giftPosition), 1);
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
        giftAnimation.showGiftAnimation(message);
        if (this.sendGiftListen!=null){
            this.sendGiftListen.send(GiftType.typeOfValue(giftPosition));
        }
        giftPosition = -1; // 发送完毕，置空
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
