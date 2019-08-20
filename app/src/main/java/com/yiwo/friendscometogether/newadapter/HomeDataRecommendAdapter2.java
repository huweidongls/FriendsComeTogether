package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeDataModel1;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.ArticleCommentActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/12/27.
 */

public class HomeDataRecommendAdapter2 extends RecyclerView.Adapter<HomeDataRecommendAdapter2.ViewHolder> {

    private Context context;
    private List<HomeDataModel1.ObjBean.ActivityBean> data;
    private SpImp spImp;
    private String uid = "";

    public HomeDataRecommendAdapter2(List<HomeDataModel1.ObjBean.ActivityBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_home_rv_tuijian_huodong, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tvName.setText(data.get(position).getPftitle());
            String slab1 = "";
            String slab2 = "";
            if (data.get(position).getActivity_label().size()>=2){
                if (data.get(position).getActivity_label().get(0).getName().length()<3){
                    slab1 = " "+data.get(position).getActivity_label().get(0).getName()+" ";
                }else {
                    slab1 = ""+data.get(position).getActivity_label().get(0).getName()+"";
                }
                if (data.get(position).getActivity_label().get(1).getName().length()<3){
                    slab2 = " "+data.get(position).getActivity_label().get(1).getName()+" ";
                }else {
                    slab2 = ""+data.get(position).getActivity_label().get(1).getName()+"";
                }
                holder.tvLab1.setText(slab1);
                holder.tvLab2.setText(slab2);
            }else if (data.get(position).getActivity_label().size()>=1){
                if (data.get(position).getActivity_label().get(0).getName().length()<3){
                    slab1 = " "+data.get(position).getActivity_label().get(0).getName()+" ";
                }else {
                    slab1 = ""+data.get(position).getActivity_label().get(0).getName()+"";
                }
                holder.tvLab1.setText(slab1);
                holder.tvLab2.setVisibility(View.GONE);
            }else {
                holder.tvLab1.setVisibility(View.GONE);
                holder.tvLab2.setVisibility(View.GONE);
            }
            Glide.with(context).load(data.get(position).getPfpic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        ImageView iv;
        TextView tvLab1;
        TextView tvLab2;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLab1 = itemView.findViewById(R.id.tv_tab_1);
            tvLab2 = itemView.findViewById(R.id.tv_tab_2);
            iv = itemView.findViewById(R.id.iv);
        }
    }

}
