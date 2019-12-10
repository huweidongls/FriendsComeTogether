package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.viewholder;

import android.widget.TextView;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.chatroom.helper.ChatRoomNotificationHelper;

public class ChatRoomMsgViewHolderNotificationTB extends MsgViewHolderBase_TB {

    protected TextView notificationTextView;

    @Override
    protected int getContentResId() {
        return com.netease.nim.uikit.R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = (TextView) view.findViewById(com.netease.nim.uikit.R.id.message_item_notification_label);
    }

    @Override
    protected void bindContentView() {
        notificationTextView.setText(ChatRoomNotificationHelper.getNotificationText((ChatRoomNotificationAttachment) message.getAttachment()));
    }

    @Override
    protected boolean isMiddleItem() {
        return false;
    }

    @Override
    protected boolean isShowBubble() {
        return false;
    }

    @Override
    protected boolean isShowHeadImage() {
        return false;
    }
}

