package com.yiwo.friendscometogether.wangyiyunshipin.upload.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by zhukkun on 2/22/17.
 */
public abstract class VcloudRecyclerViewAdapter<T> extends RecyclerView.Adapter<VcloudRecyclerViewAdapter.VcloudViewHolder> {

    List<T> datas;

    @Override
    public int getItemCount() {
        return datas==null? 0 :datas.size();
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
    }

    public List<T> getDatas(){
        return datas;
    }

    public T getDataAtPos(int position){
        return datas.get(position);
    }

    public static class VcloudViewHolder extends RecyclerView.ViewHolder{

        public VcloudViewHolder(View itemView) {
            super(itemView);
        }
    }

    public <V extends View> V findView(View parent, int id){
        return (V)parent.findViewById(id);
    }

}
