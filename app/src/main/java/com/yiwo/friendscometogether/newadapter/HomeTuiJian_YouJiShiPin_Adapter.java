package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ViewUtil;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeTuiJian_YouJiShiPin_Adapter extends RecyclerView.Adapter<HomeTuiJian_YouJiShiPin_Adapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private SpImp spImp;
    public HomeTuiJian_YouJiShiPin_Adapter(List<String> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youji_shipin_0326, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        HomeTuiJian_YouJiShiPin_Adapter.ViewHolder holder = new HomeTuiJian_YouJiShiPin_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_small_1;
        ImageView iv_small_2;
        ImageView iv_small_3;
        ImageView iv_head;
        int smallIvWidth;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_small_1 = itemView.findViewById(R.id.iv_small_1);
            final ViewGroup.LayoutParams layoutParams = iv_small_1.getLayoutParams();
            ViewUtil.getViewWidth(iv_small_1, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = width;
                    smallIvWidth = width;
                    Log.d("iv_small_1",layoutParams.width+"////"+layoutParams.height);
                    iv_small_1.setLayoutParams(layoutParams);
                }
            });
            iv_small_2 = itemView.findViewById(R.id.iv_small_2);
            ViewUtil.getViewWidth(iv_small_2, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = width;
                    Log.d("iv_small_2",layoutParams.width+"////"+layoutParams.height);
                    iv_small_2.setLayoutParams(layoutParams);
                }
            });
            iv_small_3 = itemView.findViewById(R.id.iv_small_3);
            ViewUtil.getViewWidth(iv_small_3, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = width;
                    Log.d("iv_small_2",layoutParams.width+"////"+layoutParams.height);
                    iv_small_3.setLayoutParams(layoutParams);
                }
            });
            iv_head = itemView.findViewById(R.id.iv_head);
        }
    }
}
