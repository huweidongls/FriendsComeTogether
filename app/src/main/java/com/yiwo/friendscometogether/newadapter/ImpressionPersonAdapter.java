package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.newmodel.UserPersonImpressionModel;
import com.yiwo.friendscometogether.newpage.PersonImpressionActivity;
import com.yiwo.friendscometogether.newpage.PersonsOfTheLabelActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class ImpressionPersonAdapter extends RecyclerView.Adapter<ImpressionPersonAdapter.ViewHolder> {

    private Context context;
    private String person_id;
    private List<UserPersonImpressionModel.ObjBean> data;
    public ImpressionPersonAdapter(List<UserPersonImpressionModel.ObjBean> data,String person_id) {
        this.data = data;
        this.person_id = person_id;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_impression, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv.setText(data.get(position).getLabel_name()+"("+data.get(position).getNum()+")");
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,PersonsOfTheLabelActivity.class);
                intent.putExtra("label_id",data.get(position).getLabelID());
                intent.putExtra("person_id",person_id);
                intent.putExtra("title",data.get(position).getLabel_name().toString()+"("+data.get(position).getNum()+")");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
