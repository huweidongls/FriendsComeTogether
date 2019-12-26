package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.BaseFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.adapter.MemberAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by zhukkun on 1/9/17.
 */
public class LiveRoomInfoFragment extends BaseFragment {

    public static final String EXTRA_IS_AUDIENCE = "is_audence";

    public static LiveRoomInfoFragment getInstance(boolean isAudience){
        LiveRoomInfoFragment fragment = new LiveRoomInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_IS_AUDIENCE, isAudience);
        fragment.setArguments(bundle);
        return fragment;
    }

    boolean isAudience;
    TextView tvOnlineCount;
    TextView tvRoomName;
    TextView tvMasterName;
    ImageView masterHead;
    RecyclerView recyclerView;
    MemberAdapter memberAdapter;

    TextView tvMyTbNum;
    int onlineCount;

    SpImp spImp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isAudience = getArguments().getBoolean(EXTRA_IS_AUDIENCE, true);
        spImp = new SpImp(getContext());
        return inflater.inflate(isAudience ? R.layout.layout_live_audience_room_info : R.layout.layout_live_captrue_room_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        tvOnlineCount = findView(R.id.online_count_text);
        tvRoomName = findView(R.id.room_name);
        recyclerView = findView(R.id.rv_room_member);
        initRecycleView();
        if(isAudience){
            tvMasterName = findView(R.id.master_name);
            masterHead = (ImageView) findViewById(R.id.master_head);
        }else {
            tvMyTbNum = (TextView) findViewById(R.id.tv_my_tb_num);
        }
    }

    private void initRecycleView() {
        memberAdapter = new MemberAdapter(getContext());
        recyclerView.setAdapter(memberAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        memberAdapter.setOnItemClickListener(new MemberAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ChatRoomMember member) {
                ((LiveRoomActivity)getActivity()).onMemberOperate(member);
            }
        });
    }

    public void updateMember(List<ChatRoomMember> members){
        memberAdapter.updateMember(members);
        onlineCount = members.size();
        tvOnlineCount.setText(onlineCount + "人");
    }

    public void refreshRoomInfo(ChatRoomInfo roomInfo) {
        onlineCount = roomInfo.getOnlineUserCount();
        tvOnlineCount.setText(onlineCount+"人");
        tvRoomName.setText(roomInfo.getRoomId());
        if(isAudience) {
            tvMasterName.setText(roomInfo.getCreator());
            List<String> accounts = new ArrayList<>();
            accounts.add(roomInfo.getCreator());
            NIMClient.getService(ChatRoomService.class).fetchRoomMembersByIds(roomInfo.getRoomId(), accounts).setCallback(new RequestCallback<List<ChatRoomMember>>() {
                @Override
                public void onSuccess(List<ChatRoomMember> chatRoomMembers) {
                    if (chatRoomMembers.size()>0){
                        tvMasterName.setText(chatRoomMembers.get(0).getNick());
                        Glide.with(getContext()).load(chatRoomMembers.get(0).getAvatar()).into(masterHead);
                    }
                }

                @Override
                public void onFailed(int i) {

                }

                @Override
                public void onException(Throwable throwable) {

                }
            });
        }else {
            refreshZhiBoTongBi();
        }
    }

    public void onReceivedNotification(ChatRoomNotificationAttachment attachment) {

        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.setAccount(attachment.getTargets().get(0));
        chatRoomMember.setNick(attachment.getTargetNicks().get(0));

        if(!isAudience && chatRoomMember.getAccount().equals(DemoCache.getAccount())){
            //主播的通知(主播进入房间,主播离开房间)不做处理,
            return;
        }

        switch (attachment.getType()) {
            case ChatRoomMemberIn:
                if(memberAdapter.addMember(chatRoomMember)) {
                    tvOnlineCount.setText(++onlineCount + "人");
                }
                break;
            case ChatRoomMemberExit:
            case ChatRoomMemberKicked:
                memberAdapter.removeMember(chatRoomMember);
                tvOnlineCount.setText(--onlineCount + "人");
                break;
            case ChatRoomMemberMuteAdd:
                chatRoomMember.setMuted(true);
                memberAdapter.updateSingleMember(chatRoomMember);
                break;
            case ChatRoomMemberMuteRemove:
                chatRoomMember.setMuted(false);
                memberAdapter.updateSingleMember(chatRoomMember);
                break;
        }
    }

    public void updateMemberState(ChatRoomMember current_operate_member) {
        memberAdapter.updateSingleMember(current_operate_member);
    }

    public ChatRoomMember getMember(String fromAccount) {
        return memberAdapter.getMember(fromAccount);
    }
    public void refreshZhiBoTongBi(){
        if (isAudience){
            return;
        }
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
}
