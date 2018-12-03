package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.LookPasswordDialog;
import com.yiwo.friendscometogether.model.HomeHotFriendsRememberModel;
import com.yiwo.friendscometogether.pages.DetailsOfFriendTogetherActivity;
import com.yiwo.friendscometogether.pages.DetailsOfFriendsActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class HomeHotAdapter extends RecyclerView.Adapter<HomeHotAdapter.ViewHolder> {
    private Context context;
    private List<HomeHotFriendsRememberModel.ObjBean.InfoBean> data;
    SpImp spImp;
    private OnFocusListener listener;

    public void setOnFocusListener(OnFocusListener listener) {
        this.listener = listener;
    }

    //    private List<HomeHotFriendsRememberModel.ObjBean.VideoBean> list;
    public HomeHotAdapter(List<HomeHotFriendsRememberModel.ObjBean.InfoBean> data) {
        this.data = data;
    }

    @Override
    public HomeHotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_hot, parent, false);
        this.context = parent.getContext();
        spImp = new SpImp(parent.getContext());
        ScreenAdapterTools.getInstance().loadView(view);
        HomeHotAdapter.ViewHolder holder = new HomeHotAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        if(data.get(position).getVideo().size()==0){
        Picasso.with(context).load(data.get(position).getFmpic()).into(holder.titleIv);
        holder.titleTv.setText(data.get(position).getFmtitle());
        Log.i("00000000", data.get(position).getFmcomment());
        if (!TextUtils.isEmpty(data.get(position).getUpicurl())) {
            Picasso.with(context).load(data.get(position).getUpicurl()).into(holder.headIv);
        }
        if (data.get(position).getFollow().equals("0")) {
            holder.btnFocus.setText("+关注");
        } else {
            holder.btnFocus.setText("已关注");
        }
        holder.tvLevel.setText("LV" + data.get(position).getUsergrade());
        holder.timeTv.setText(data.get(position).getFmtime());
        holder.viewsTv.setText(data.get(position).getFmlook());
        holder.messageTv.setText(data.get(position).getFmcomment());
        holder.nameTv.setText(data.get(position).getUsername());
        holder.childrenLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                if(TextUtils.isEmpty(data.get(position).getAccesspassword())){
                    intent.setClass(context, DetailsOfFriendsActivity.class);
                    intent.putExtra("fmid", data.get(position).getFmID());
                    context.startActivity(intent);
                }else {
                    LookPasswordDialog lookPasswordDialog = new LookPasswordDialog(context, new LookPasswordDialog.SetPasswordListener() {
                        @Override
                        public void setActivityText(String s) {
                            if(s.equals(data.get(position).getAccesspassword())){
                                intent.setClass(context, DetailsOfFriendsActivity.class);
                                intent.putExtra("fmid", data.get(position).getFmID());
                                context.startActivity(intent);
                            }
                        }
                    });
                    lookPasswordDialog.show();
                }
            }
        });
//        } else {
//            list = data.get(position).getVideo();
//            holder.childrenLl.setVisibility(View.GONE);
//            ((ViewGroup)videoPl.getParent()).removeView(videoPl);
//            holder.vesselLl.addView(videoPl);
//           videoPl.setAdapter(new PileLayoutVideoAdapter(context,data.get(position).getVideo()));
//        }
        holder.btnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spImp.getUID().equals("0")) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    listener.onFocus(position);
                }
            }
        });
        holder.headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("uid", data.get(position).getUserID());
                intent.setClass(context, OtherInformationActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView nameTv;
        private TextView viewsTv;
        private TextView messageTv;
        private TextView timeTv;
        private ImageView titleIv;
        private ImageView headIv;
        private LinearLayout vesselLl;
        private LinearLayout childrenLl;
        private Button btnFocus;
        private TextView tvLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            titleIv = itemView.findViewById(R.id.home_hot_itemIv);
            titleTv = itemView.findViewById(R.id.home_hot_title);
            headIv = itemView.findViewById(R.id.home_hot_itemHeadIv);
            nameTv = itemView.findViewById(R.id.home_hot_itemNameTv);
            timeTv = itemView.findViewById(R.id.home_hot_itemTimeTv);
            viewsTv = itemView.findViewById(R.id.home_hot_itemViewsTv);
            messageTv = itemView.findViewById(R.id.home_hot_itemMessageTv);
            vesselLl = itemView.findViewById(R.id.home_hot_vessel);
            childrenLl = itemView.findViewById(R.id.home_hot_children);
            btnFocus = itemView.findViewById(R.id.activity_details_of_friends_btn_focus);
            tvLevel = itemView.findViewById(R.id.tv_level);
        }
    }

    public interface OnFocusListener {
        void onFocus(int position);
    }

}
