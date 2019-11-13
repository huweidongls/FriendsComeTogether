package com.yiwo.friendscometogether.newadapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.newmodel.DuiZhangPicGameModel;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;
import com.yiwo.friendscometogether.widget.choose_pics_view.BitmapUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2019/11/8.
 */

public class DuiZhangGameListAdapter extends RecyclerView.Adapter<DuiZhangGameListAdapter.ViewHolder>  {

    private List<DuiZhangPicGameModel.ObjBean> data ;
//    private String[] imgsArr = new String[]{
//            "http://img2.imgtn.bdimg.com/it/u=2436940115,2560273015&fm=26&gp=0.jpg",
//            "http://img0.imgtn.bdimg.com/it/u=503342368,1120849718&fm=26&gp=0.jpg",
//            "http://img1.imgtn.bdimg.com/it/u=1060746327,3729898448&fm=26&gp=0.jpg",
//            "http://img1.imgtn.bdimg.com/it/u=3608155120,1630233151&fm=26&gp=0.jpg",
//            "http://img4.imgtn.bdimg.com/it/u=2174293934,106207130&fm=26&gp=0.jpg"
//    };
//    private String [] strArr = new String[]{
//            "第一题",
//            "第二题",
//            "第三题",
//            "",
//            "第五题"
//    };
//    private String [] strArrAnswer = new String[]{
//            "答案：一",
//            "答案：2",
//            "答案：3",
//            "答案：4",
//            "答案：5"
//    };
    private Context context;
    private ShareListener shareListener;
    private ChooseShareText chooseShareText;
    public DuiZhangGameListAdapter (List<DuiZhangPicGameModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_duizhang_game_list,parent,false);
        ScreenAdapterTools.getInstance().loadView(view);
        DuiZhangGameListAdapter.ViewHolder holder = new DuiZhangGameListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvQuestion.setText("题目："+data.get(position).getTitle());
        holder.tvAnswer.setText("答案："+data.get(position).getAnswer());
        Glide.with(context).load(data.get(position).getPicUrl()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.imageView);
        if (data.get(position).getShareText().equals("1")){
            holder.ivChooseWenzi.setImageResource(R.mipmap.red_choose_chceked);
        }else {
            holder.ivChooseWenzi.setImageResource(R.mipmap.red_choose_none);
        }
        holder.llShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseShareText!=null){
                    chooseShareText.onChooseShareText(position);
                }
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listPics = new ArrayList<>();
                List<String> listContent = new ArrayList<>();
                for (int i = 0;i<data.size();i++){
                    listPics.add(data.get(i).getPicUrl());
                    listContent.add(data.get(i).getTitle());
                }
                Intent intent1 = new Intent();
                intent1.setClass(context, ImagePreviewActivity.class);
                intent1.putStringArrayListExtra("imageList", (ArrayList<String>) listPics);
//                isHasImageContent = getIntent().getBooleanExtra("hasImageContent",false);
//                if (isHasImageContent){
//                    contentList = getIntent().getStringArrayListExtra("imageContenList");
//                }
                intent1.putExtra("hasImageContent",true);
                intent1.putStringArrayListExtra("imageContenList", (ArrayList<String>) listContent);
                intent1.putExtra(Consts.START_ITEM_POSITION, position);
                intent1.putExtra(Consts.START_IAMGE_POSITION, position);
                context.startActivity(intent1);
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareListener!=null){
                    shareListener.onShareListen(data.get(position).getShareText(),data.get(position).getTitle(),data.get(position).getAnswer(),((BitmapDrawable)holder.imageView.getDrawable()).getBitmap());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size()>0?data.size():0;
    }

    public void setShareListener(ShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public void setChooseShareText(ChooseShareText chooseShareText) {
        this.chooseShareText = chooseShareText;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvQuestion;
        TextView tvAnswer;
        TextView btnShare;
        LinearLayout llShareText;
        ImageView ivChooseWenzi;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
            tvAnswer = itemView.findViewById(R.id.tv_answer);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            btnShare = itemView.findViewById(R.id.btn_share);
            llShareText = itemView.findViewById(R.id.ll_share_text);
            ivChooseWenzi = itemView.findViewById(R.id.iv_choose_wenzi);
        }
    }
    public interface ShareListener{
        void onShareListen(String chooseText,String strQ, String strA, Bitmap bitmap);
    }
    public interface ChooseShareText{
        void onChooseShareText(int position);
    }
}
