package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.YouJiWebModel;
import com.yiwo.friendscometogether.newmodel.YouJuWebModel;

import java.util.List;

/**
 * Created by ljc on 2019/3/28.
 */

public class MuLuItemYouJuAdapter extends RecyclerView.Adapter<MuLuItemYouJuAdapter.ViewHolder> {
    private List<YouJuWebModel.ObjBean.TitleBean> data;
    private Context context;
    private ChooseListen listen;
    public void setListener(ChooseListen listener){
        this.listen = listener;
    }
    public MuLuItemYouJuAdapter(List<YouJuWebModel.ObjBean.TitleBean> data){
        this.data =data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mulu_item, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        MuLuItemYouJuAdapter.ViewHolder holder = new MuLuItemYouJuAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFftitle());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.chooseItemId(data.get(position).getFfID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rl = itemView.findViewById(R.id.rl);
        }
    }
    public interface ChooseListen{
        void chooseItemId(String ID);
    }
}
