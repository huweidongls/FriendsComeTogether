package com.yiwo.friendscometogether.newadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.api.NimUIKit;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeDataModel1;
import com.yiwo.friendscometogether.newmodel.HuoDongListModel;
import com.yiwo.friendscometogether.pages.OrderCommentActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.ViewUtil;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class HomeFragmentFirstBannerAdapter extends RecyclerView.Adapter<HomeFragmentFirstBannerAdapter.ViewHolder> {

    private Context context;
    private List<HomeDataModel1.ObjBean.ActivityBean> data;
    private SpImp spImp;
    private int currentPositon = 0;
    public HomeFragmentFirstBannerAdapter(List<HomeDataModel1.ObjBean.ActivityBean> data) {
        this.data = data;
    }
    public int getPosition(){
        return currentPositon;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_banner_layout, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        currentPositon = position;
        final  HomeDataModel1.ObjBean.ActivityBean bean = data.get(position);
        Glide.with(context).load(bean.getPfpic().get(0)).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv_first_tuijian);
        holder.tv_first_tuijian_title.setText(bean.getPftitle());
        holder.tv_first_tuijian_content.setText(bean.getPfcontent());
        holder.tv_youji_look_num.setText(bean.getPflook());
        if (bean.getActivity_label().size()>0){
            holder.tv_tuyijian_first_tab_1.setText(bean.getActivity_label().get(0).getName());
            if (bean.getActivity_label().size()>1){
                holder.tv_tuyijian_first_tab_2.setText(bean.getActivity_label().get(1).getName());
            }
        }
        holder.ll_tuijian_huodong_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                if (TextUtils.isEmpty(bean.getPfpwd())) {
                    intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                    intent.putExtra("pfID", bean.getPfID());
                    context.startActivity(intent);
                } else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public boolean setActivityText(String s) {
                            if (s.equals(bean.getPfpwd())) {
                                intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                                intent.putExtra("pfID", bean.getPfID());
                                context.startActivity(intent);
                                return true;
                            }else {
                                Toast.makeText(context,"密码错误",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                    });
                    lookPasswordDialog.show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_first_tuijian;
        private TextView tv_first_tuijian_title,tv_first_tuijian_content,tv_youji_look_num;
        private TextView tv_tuyijian_first_tab_1,tv_tuyijian_first_tab_2;
        LinearLayout ll_tuijian_huodong_first;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_first_tuijian = itemView.findViewById(R.id.iv_tuijian_first);
            final ViewGroup.LayoutParams layoutParams = iv_first_tuijian.getLayoutParams();
            ViewUtil.getViewWidth(iv_first_tuijian, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = (int) (width * 0.7);
                    Log.d("asdasdasd2",layoutParams.width+"////"+layoutParams.height);
                    iv_first_tuijian.setLayoutParams(layoutParams);
                }
            });
            ll_tuijian_huodong_first = itemView.findViewById(R.id.ll_tuijian_huodong_first);
            tv_first_tuijian_title = itemView.findViewById(R.id.tv_first_tuijian_title);
            tv_first_tuijian_content = itemView.findViewById(R.id.tv_first_tuijian_content);
            tv_youji_look_num = itemView.findViewById(R.id.tv_youji_look_num);
            tv_tuyijian_first_tab_1 = itemView.findViewById(R.id.tv_tuyijian_first_tab_1);
            tv_tuyijian_first_tab_2 = itemView.findViewById(R.id.tv_tuyijian_first_tab_2);
        }
    }

}
