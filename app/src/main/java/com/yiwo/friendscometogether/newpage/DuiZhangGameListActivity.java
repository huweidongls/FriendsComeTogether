package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.DuiZhangGameListAdapter;
import com.yiwo.friendscometogether.newmodel.DuiZhangPicGameModel;
import com.yiwo.friendscometogether.newmodel.MyGroupListModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.choose_pics_view.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DuiZhangGameListActivity extends BaseActivity {


    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private SpImp spImp;
    private DuiZhangGameListAdapter adapter;
    private Dialog dialog;
    private List<DuiZhangPicGameModel.ObjBean> list = new ArrayList<>();
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dui_zhang_game_list);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(DuiZhangGameListActivity.this);
        spImp = new SpImp(DuiZhangGameListActivity.this);
        initdata();
    }

    private void initdata() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(DuiZhangGameListActivity.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(DuiZhangGameListActivity.this));
        adapter = new DuiZhangGameListAdapter(list);
        adapter.setShareListener(new DuiZhangGameListAdapter.ShareListener() {
            @Override
            public void onShareListen(String chooseText,String strQ, String strA, Bitmap bitmap) {
                if (chooseText.equals("1")){
                    shareImageToGroup(bitmap,strQ,strA);
                }else {
                    shareImageToGroup(bitmap,"","");
                }
            }
        });
        adapter.setChooseShareText(new DuiZhangGameListAdapter.ChooseShareText() {
            @Override
            public void onChooseShareText(int position) {
                list.get(position).setShareText(list.get(position).getShareText().equals("1") ? "0" : "1");
                adapter.notifyDataSetChanged();
            }
        });
        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
//        LinearLayoutManager manager = new LinearLayoutManager(DuiZhangGameListActivity.this){
//            @Override
//            public boolean canScrollVertically() {
//                return true;
//            }
//        };
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        ViseHttp.POST(NetConfig.pictureGame)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.pictureGame))
                .addParam("page", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                DuiZhangPicGameModel model = gson.fromJson(data,DuiZhangPicGameModel.class);
                                list.clear();
                                list.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                page = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
                refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                        ViseHttp.POST(NetConfig.pictureGame)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.pictureGame))
                                .addParam("page", "1")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                DuiZhangPicGameModel model = gson.fromJson(data,DuiZhangPicGameModel.class);
                                                list.clear();
                                                list.addAll(model.getObj());
                                                adapter.notifyDataSetChanged();
                                                page = 2;
                                            }
                                            refreshLayout.finishRefresh(1000);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            refreshLayout.finishRefresh(1000);
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishRefresh(1000);
                                    }
                                });
                    }
                });
                refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                        ViseHttp.POST(NetConfig.pictureGame)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.pictureGame))
                                .addParam("page", page+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                DuiZhangPicGameModel model = gson.fromJson(data,DuiZhangPicGameModel.class);
                                                list.addAll(model.getObj());
                                                adapter.notifyDataSetChanged();
                                                page++;
                                            }
                                            refreshLayout.finishLoadMore(1000);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            refreshLayout.finishLoadMore(1000);
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                    }
                });
    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
    private void shareImageToGroup(final Bitmap bitmap, final String strQ, String strA) {
        dialog = WeiboDialogUtils.createLoadingDialog(DuiZhangGameListActivity.this,"加载中");
        ViseHttp.POST(NetConfig.groupList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.groupList))
                .addParam("userID", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                final MyGroupListModel model = gson.fromJson(data,MyGroupListModel.class);
                                String[] strs = new String[model.getObj().size()];
                                for (int i = 0;i<model.getObj().size();i++){
                                    strs[i] = model.getObj().get(i).getName();
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(DuiZhangGameListActivity.this);
                                builder.setTitle("请选择要分享的群");
                                builder.setItems(strs, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, final int which00) {
                                        SessionTypeEnum sessionType = SessionTypeEnum.Team;
                                        if (!TextUtils.isEmpty(strQ)){
                                            IMMessage messageQuestion = MessageBuilder.createTextMessage(model.getObj().get(which00).getGroupid(),sessionType,strQ);
                                            NIMClient.getService(MsgService.class).sendMessage(messageQuestion, false);
                                        }
                                        String imgPath = "/storage/emulated/0/Pictures/"+"tb_duizhang_share"+System.currentTimeMillis() + ".jpg";
                                        BitmapUtils.saveBitmap(bitmap,imgPath);
                                        // 示例图片，需要开发者在相应目录下有图片
                                        File file = new File(imgPath);
                                        // 创建一个图片消息
                                        IMMessage message = MessageBuilder.createImageMessage(model.getObj().get(which00).getGroupid(), sessionType, file, file.getName());
                                        // 发送给对方
                                        NIMClient.getService(MsgService.class).sendMessage(message, false);
                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DuiZhangGameListActivity.this);
                                        builder1.setMessage("分享成功，是否跳转至群聊")
                                                .setPositiveButton("留在本页", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .setNegativeButton("去群聊", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //跳转至群聊
                                                        NimUIKit.startTeamSession(DuiZhangGameListActivity.this, model.getObj().get(which00).getGroupid());
                                                    }
                                                }).show();
                                    }
                                });
                                WeiboDialogUtils.closeDialog(dialog);
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                });
    }
}
