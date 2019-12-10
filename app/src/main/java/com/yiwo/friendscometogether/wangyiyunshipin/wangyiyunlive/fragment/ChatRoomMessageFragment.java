package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.business.session.module.Container;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.action.GuessAction;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.chatroom.helper.ChatRoomMemberCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天室直播互动fragment
 * Created by hzxuwen on 2015/12/16.
 */
public class ChatRoomMessageFragment extends TFragment implements ModuleProxy {
    private View rootView;
    // modules
    protected ChatRoomInputPanel inputPanel;
    protected ChatRoomMsgListPanel messageListPanel;

    private String roomId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_room_message_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (inputPanel != null) {
            inputPanel.onPause();
        }
        if (messageListPanel != null) {
            messageListPanel.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (messageListPanel != null) {
            messageListPanel.onResume();
        }
    }

    public boolean onBackPressed() {
        if (inputPanel != null && inputPanel.collapse(true)) {
            return true;
        }

        if (messageListPanel != null && messageListPanel.onBackPressed()) {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerObservers(false);

        if (messageListPanel != null) {
            messageListPanel.onDestroy();
        }
    }

    public void onLeave() {
        if (inputPanel != null) {
            inputPanel.collapse(false);
        }
    }

    public void init(String roomId) {
        this.roomId = roomId;
        registerObservers(true);
        findViews();
        inputPanel.collapse(false);
        inputPanel.setVisible(false);
    }

    private void findViews() {
        Container container = new Container(getActivity(), roomId, SessionTypeEnum.ChatRoom, this);
        if (messageListPanel == null) {
            messageListPanel = new ChatRoomMsgListPanel(container, rootView);
        }

        if (inputPanel == null) {
            inputPanel = new ChatRoomInputPanel(container, rootView, getActionList(), false);
        } else {
            inputPanel.reload(container, null);
        }
    }

    private void registerObservers(boolean register) {
        NIMClient.getService(ChatRoomServiceObserver.class).observeReceiveMessage(incomingChatRoomMsg, register);
    }

    Observer<List<ChatRoomMessage>> incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
        @Override
        public void onEvent(List<ChatRoomMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }

            messageListPanel.onIncomingMessage(messages);
        }
    };

    /************************** Module proxy ***************************/

    @Override
    public boolean sendMessage(IMMessage msg) {
        ChatRoomMessage message = (ChatRoomMessage) msg;

        Map<String, Object> ext = new HashMap<>();
        ChatRoomMember chatRoomMember = ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount());
        if (chatRoomMember != null && chatRoomMember.getMemberType() != null) {
            ext.put("type", chatRoomMember.getMemberType().getValue());
            message.setRemoteExtension(ext);
        }

        NIMClient.getService(ChatRoomService.class).sendMessage(message, false)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == ResponseCode.RES_CHATROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "用户被禁言", Toast.LENGTH_SHORT).show();
                        } else if (code == ResponseCode.RES_CHATROOM_ROOM_MUTED) {
                            Toast.makeText(DemoCache.getContext(), "全体禁言", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), "消息发送失败：code:" + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(DemoCache.getContext(), "消息发送失败！", Toast.LENGTH_SHORT).show();
                    }
                });
        messageListPanel.onMsgSend(msg);
        return true;
    }

    @Override
    public void onInputPanelExpand() {
        inputPanel.setVisible(true);
        messageListPanel.scrollToBottom();
    }

    @Override
    public void shouldCollapseInputPanel() {
        inputPanel.collapse(false);
        inputPanel.setVisible(false);
    }

    @Override
    public boolean isLongClickEnabled() {
        return !inputPanel.isRecording();
    }

    @Override
    public void onItemFooterClick(IMMessage message) {

    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        actions.add(new GuessAction());
        return actions;
    }

    public void setMsgExtraDelegate(ChatRoomMsgListPanel.ExtraDelegate ls){
        messageListPanel.setExtraDelegate(ls);
    }

    public void onTextMessageSendButtonPressed(String text) {
        inputPanel.onTextMessageSendButtonPressed(text);
    }
}
