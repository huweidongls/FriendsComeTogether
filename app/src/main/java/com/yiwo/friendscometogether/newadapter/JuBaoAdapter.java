package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.JuBaoModel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class JuBaoAdapter extends RecyclerView.Adapter<JuBaoAdapter.ViewHolder> {

    private Context context;
    private List<JuBaoModel> data;
    private OnSelectListener listener;

    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public JuBaoAdapter(List<JuBaoModel> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_jubao, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv.setText(data.get(position).getText());
        holder.cb.setChecked(data.get(position).isChoose());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelete(position,true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private CheckBox cb;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            cb  = itemView.findViewById(R.id.cb);
            ll  = itemView.findViewById(R.id.ll);
        }
    }

    public interface OnSelectListener{
        void onSelete(int i,boolean ischecked);
    }

}
