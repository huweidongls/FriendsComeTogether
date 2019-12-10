package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.viewholder;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.yiwo.friendscometogether.R;

/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderUnknownTB extends MsgViewHolderBase_TB {
    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_unknown;
    }

    @Override
    protected boolean isShowHeadImage() {
        if (message.getSessionType() == SessionTypeEnum.ChatRoom) {
            return false;
        }
        return true;
    }

    @Override
    protected void inflateContentView() {
    }

    @Override
    protected void bindContentView() {
    }
}
