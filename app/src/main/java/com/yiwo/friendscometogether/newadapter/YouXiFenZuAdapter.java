package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.business.robot.parser.elements.group.LinearLayout;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.YouXiFenZuPersonModel;

import java.util.List;

/**
 * Created by ljc on 2019/9/19.
 */

public class YouXiFenZuAdapter extends RecyclerView.Adapter<YouXiFenZuAdapter.ViewHolder> {

    private List<List<YouXiFenZuPersonModel.ObjBean>> list ;
    private Context context;

    private OnAddListenner listenner;
    public YouXiFenZuAdapter ( List<List<YouXiFenZuPersonModel.ObjBean>> list ){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youxi_fenzu, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        YouXiFenZuAdapter.ViewHolder holder = new YouXiFenZuAdapter.ViewHolder(view);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText("第"+(position+1)+"组");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        YouXiFenZuPersonsAdapter adapter = new YouXiFenZuPersonsAdapter(list.get(position));
        adapter.setListener( new YouXiFenZuPersonsAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int postion) {

            }
        });
        adapter.showDeleteBtn();
        adapter.setDeleteListener(new YouXiFenZuPersonsAdapter.OnDeleteListener() {
            @Override
            public void delete(int postion1) {
                listenner.delteItemListen(position,postion1);
            }
        });
        holder.rv.setLayoutManager(manager);
        holder.rv.setAdapter(adapter);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onAddListen(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size()>0? list.size():0;
    }

    public void setListenner(OnAddListenner listenner) {
        this.listenner = listenner;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        RecyclerView rv;
        ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            rv = itemView.findViewById(R.id.rv);
            iv = itemView.findViewById(R.id.iv_add);
        }
    }
    public interface OnAddListenner{
        void onAddListen(int postion);
        void delteItemListen(int postion,int itemPostion);
    }
}
