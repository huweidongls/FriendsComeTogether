package com.yiwo.friendscometogether.emoji;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;


/**
 * Created by zejian
 * Time  16/1/8 下午5:05
 * Email shinezejian@163.com
 * Description:点击表情的全局监听管理类
 */
public class GlobalOnItemClickManagerUtils {

    private static GlobalOnItemClickManagerUtils instance;
    private EditText mEditText;//输入框
    private static Context mContext;

    public static GlobalOnItemClickManagerUtils getInstance(Context context) {
        mContext=context;
        if (instance == null) {
            synchronized (GlobalOnItemClickManagerUtils.class) {
                if(instance == null) {
                    instance = new GlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emotion_map_type) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAdapter = parent.getAdapter();

                if (itemAdapter instanceof EmotionGridViewAdapter1) {
                    // 点击的是表情
                    EmotionGridViewAdapter1 emotionGvAdapter = (EmotionGridViewAdapter1) itemAdapter;

                    if (position == emotionGvAdapter.getCount() - 1) {
                        // 如果点击了最后一个回退按钮,则调用删除键事件
                        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                        mEditText.dispatchKeyEvent(event);
                    } else {
                        // 如果点击了表情,则添加到输入框中
                        Emojicon emotionName = emotionGvAdapter.getItem(position);

                        // 获取当前光标位置,在指定位置上添加表情图片文本
                        if (mEditText == null || emotionName == null) {
                            return;
                        }

                        int start = mEditText.getSelectionStart();
                        int end = mEditText.getSelectionEnd();
                        if (start < 0) {
                            mEditText.append(emotionName.getEmoji());
                        } else {
                            mEditText.getText().replace(Math.min(start, end), Math.max(start, end), emotionName.getEmoji(), 0, emotionName.getEmoji().length());
                        }
                    }

                }
            }
        };
    }

}
