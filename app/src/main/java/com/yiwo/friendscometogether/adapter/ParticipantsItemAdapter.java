package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity;
import com.yiwo.friendscometogether.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class ParticipantsItemAdapter extends RecyclerView.Adapter<ParticipantsItemAdapter.ViewHolder>{
    private Context context;
    private List<FriendsTogetherDetailsModel.ObjBean.PhaseBean.PhaseListBean> data;
    private int noname_num = 0;
    public ParticipantsItemAdapter(List<FriendsTogetherDetailsModel.ObjBean.PhaseBean.PhaseListBean> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participants, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(data.size()!=0){
            if (position == 0){//当从第一位初始化数据时将匿名人数归为0
                noname_num = 0;
            }
            if (data.get(position).getNoname().equals("1")){// 是否匿名 0否 1是
                noname_num= noname_num+Integer.parseInt(data.get(position).getUjall());
                holder.rl_has_name.setVisibility(View.GONE);
            }else {
                holder.rl_has_name.setVisibility(View.VISIBLE);
                if (Integer.parseInt(data.get(position).getUjall())>1){
                    holder.tvBaoMingNum.setText(data.get(position).getUjall());
//                    holder.tvBaoMingNum.setText("99");
                }else {
                    holder.rlBaoMingNum.setVisibility(View.GONE);
                }
                Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.headIv);
                holder.nicknameTv.setText(data.get(position).getUsername());
            }
            if (position!=data.size()-1){//不是最后一条数据时，如果为匿名则不显示，计匿名数
                holder.rl_no_name.setVisibility(View.GONE);//只有最后一条数据显示匿名头像

            }else {
                if (noname_num>0){//匿名人数大于0时
                    holder.rlBaoMingNumNM.setVisibility(View.VISIBLE);
                    holder.rl_no_name.setVisibility(View.VISIBLE);
                    holder.tvBaoMingNumNM.setText(""+noname_num);
                }else {
                    holder.rlBaoMingNumNM.setVisibility(View.GONE);
                    holder.rl_no_name.setVisibility(View.GONE);
                }
            }

            holder.personRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.get(position).getNoname().equals("1")){
                        Toast.makeText(context, "无法查看匿名信息", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent it = new Intent(context, PersonMainActivity.class);
                        it.putExtra("person_id",data.get(position).getUserID());
                        context.startActivity(it);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView headIv;
        private TextView nicknameTv;
        private RelativeLayout personRl;
        private RelativeLayout rl_has_name;
        private RelativeLayout rl_no_name;
        private RelativeLayout rlBaoMingNum;
        private TextView tvBaoMingNum;
        private RelativeLayout rlBaoMingNumNM;
        private TextView tvBaoMingNumNM;
        public ViewHolder(View itemView) {
            super(itemView);
            headIv = (itemView).findViewById(R.id.item_head);
            nicknameTv = (itemView).findViewById(R.id.item_nickname);
            personRl = (itemView).findViewById(R.id.item_onclick_rl);
            rl_has_name = itemView.findViewById(R.id.rl_has_name);
            rl_no_name = itemView.findViewById(R.id.rl_no_name);
            rlBaoMingNum = itemView.findViewById(R.id.rl_num);
            tvBaoMingNum = itemView.findViewById(R.id.tv_num);
            rlBaoMingNumNM = itemView.findViewById(R.id.rl_num_nm);
            tvBaoMingNumNM = itemView.findViewById(R.id.tv_num_nm);
        }
    }

}
