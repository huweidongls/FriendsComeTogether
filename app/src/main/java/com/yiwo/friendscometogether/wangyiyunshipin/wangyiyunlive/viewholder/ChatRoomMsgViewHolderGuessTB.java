package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.viewholder;


import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.GuessAttachment;

/**
 * Created by hzxuwen on 2016/1/20.
 */
public class ChatRoomMsgViewHolderGuessTB extends ChatRoomViewHolderTextTB {

    @Override
    protected String getDisplayText() {
        GuessAttachment attachment = (GuessAttachment) message.getAttachment();

        return attachment.getValue().getDesc() + "!";
    }
}
