package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.OpenLoveTiaoJianModel;
import com.yiwo.friendscometogether.newadapter.OpenLoveDialogRvAdapter;

/**
 * Created by ljc on 2019/8/22.
 */

public class OpenLoveDialog extends Dialog {

    private TextView tvTitle,tvContent;
    private RecyclerView rv;
    private Button btnOpen,btnCancel;

    private Context context;
    private OpenLoveTiaoJianModel model;
    private Listenner listenner;
    private OpenLoveDialogRvAdapter adapter;
    public OpenLoveDialog(@NonNull Context context, OpenLoveTiaoJianModel model,Listenner listenner) {
        super(context);
        this.context = context;
        this.model = model;
        this.listenner = listenner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_open_love,null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        tvTitle = view.findViewById(R.id.tv_title);
        tvContent = view.findViewById(R.id.tv_content);

        rv =  view.findViewById(R.id.rv);
        btnOpen = view.findViewById(R.id.btn_open);
        btnCancel =view.findViewById(R.id.btn_cancel);
        tvTitle.setText(model.getObj().getTitle());
        tvContent.setText(model.getObj().getInfo());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        adapter = new OpenLoveDialogRvAdapter(model.getObj().getItem());
        adapter.setListionner(new OpenLoveDialogRvAdapter.OnClickJumpListionner() {
            @Override
            public void startShiMing() {
                dismiss();
            }

            @Override
            public void startBiaoqian() {
                dismiss();
            }
        });
        rv.setAdapter(adapter);

        if (model.getObj().getStart().equals("0")){
            btnOpen.setVisibility(View.GONE);
        }
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onOpen(OpenLoveDialog.this);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onCanel(OpenLoveDialog.this);
            }
        });
    }
    public interface Listenner{
        void onCanel(OpenLoveDialog dialog);
        void onOpen(OpenLoveDialog dialog);
    }
}
