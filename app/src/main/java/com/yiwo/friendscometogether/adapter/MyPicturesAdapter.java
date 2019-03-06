package com.yiwo.friendscometogether.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.MyPicListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2018/7/24.
 */

public class MyPicturesAdapter extends RecyclerView.Adapter<MyPicturesAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private SpImp spImp;
    private Context context;
    private List<MyPicListModel.ObjBean> data;

    private onItemClickListener listener;
    private onItemLongClickListener longClickListener;

    public MyPicturesAdapter(List<MyPicListModel.ObjBean> data) {
        this.data = data;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnItemLongClickListener(onItemLongClickListener listener){
        this.longClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_pictures, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        spImp = new SpImp(context);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            holder.rlAdd.setVisibility(View.VISIBLE);
            holder.rlIv.setVisibility(View.GONE);
        } else {
            holder.rlAdd.setVisibility(View.GONE);
            holder.rlIv.setVisibility(View.VISIBLE);
            Picasso.with(context).load(data.get(position - 1).getUpicurl()).into(holder.iv);
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> urlList = new ArrayList<>();
                    for (int i = 0; i<data.size(); i++){
                        urlList.add(data.get(i).getUpicurl());
                    }
                    Intent intent = new Intent(context, ImagePreviewActivity.class);
                    intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                    intent.putExtra(Consts.START_ITEM_POSITION, position - 1);
                    intent.putExtra(Consts.START_IAMGE_POSITION, position - 1);
                    context.startActivity(intent);
                }
            });
            holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onLongClick(position);
                    return false;
                }
            });
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(2, position);
                }
            });
        }
        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(1, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlAdd;
        private ImageView iv;
        private RelativeLayout rlIv;
        private ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            rlAdd = itemView.findViewById(R.id.activity_my_pictures_rv_rl_add);
            iv = itemView.findViewById(R.id.activity_my_pictures_rv_iv);
            rlIv = itemView.findViewById(R.id.activity_my_pictures_rv_rl_iv);
            ivDelete = itemView.findViewById(R.id.activity_my_pictures_rv_iv_delete);
        }
    }

    public interface onItemClickListener {
        void onClick(int type, int position);
    }
    public interface onItemLongClickListener{
        void onLongClick(int position);
    }

}
