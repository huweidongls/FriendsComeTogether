package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class DuiZhangXuanZeHuoDongListAdapter extends RecyclerView.Adapter<DuiZhangXuanZeHuoDongListAdapter.ViewHolder> {

    private Context context;
    private List<DuiZhangXuanZeHuoDongModel.ObjBean> data;
    private ItemClickListionner listionner;
    public DuiZhangXuanZeHuoDongListAdapter(List<DuiZhangXuanZeHuoDongModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_duizhang_xuanze_shuo_huo_dong, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DuiZhangXuanZeHuoDongModel.ObjBean bean = data.get(position);
        holder.tvTitle.setText(bean.getPhase_num()+" ["+bean.getPhase_begin_time().substring(5,bean.getPhase_begin_time().length())+"] "+bean.getPftitle());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listionner.onClick(position);
//                ac.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setListionner(ItemClickListionner listionner) {
        this.listionner = listionner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface ItemClickListionner{
        void onClick(int postion);
    }
}
