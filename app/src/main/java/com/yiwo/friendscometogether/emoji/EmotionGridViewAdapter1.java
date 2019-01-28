package com.yiwo.friendscometogether.emoji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.yiwo.friendscometogether.R;

import java.util.List;

/**
 * Created by zejian
 * Time  16/1/7 下午4:46
 * Email shinezejian@163.com
 * Description:
 */
public class EmotionGridViewAdapter1 extends BaseAdapter {

	private Context context;
	private List<Emojicon> emotionNames;
	private int itemWidth;
    private int emotion_map_type;

	public EmotionGridViewAdapter1(Context context, List<Emojicon> emotionNames, int itemWidth, int emotion_map_type) {
		this.context = context;
		this.emotionNames = emotionNames;
		this.itemWidth = itemWidth;
		this.emotion_map_type=emotion_map_type;
	}

	@Override
	public int getCount() {
		// +1 最后一个为删除按钮
		return emotionNames.size() + 1;
	}

	@Override
	public Emojicon getItem(int position) {
		return emotionNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv_emotion = new ImageView(context);
		EmojiconTextView emojiconTextView = new EmojiconTextView(context);
		// 设置内边距
		iv_emotion.setPadding(itemWidth/8, itemWidth/8, itemWidth/8, itemWidth/8);
		LayoutParams params = new LayoutParams(itemWidth, itemWidth);
		iv_emotion.setLayoutParams(params);
		emojiconTextView.setPadding(itemWidth/8, itemWidth/8, itemWidth/8, itemWidth/8);
		LayoutParams params1 = new LayoutParams(itemWidth, itemWidth);
		emojiconTextView.setLayoutParams(params1);
		emojiconTextView.setEmojiconSize(80);
		//判断是否为最后一个item
		if(position == getCount() - 1) {
			iv_emotion.setImageResource(R.drawable.compose_emotion_delete);
			return iv_emotion;
		} else {
			Emojicon emoji = getItem(position);
			emojiconTextView.setText(emoji.getEmoji());
			return emojiconTextView;
		}

	}

}
