package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

import java.util.ArrayList;

/**
 * Created by ljc on 2019/9/20.
 */

public class RecyclerViewDialog extends Dialog {
    private String titleText,btnText;
    private RecyclerView.Adapter adapter;
    private Context context;

    private TextView tvTitle;
    private Button btn;
    private RecyclerView recyclerView;
    private Listenner listenner;
    public RecyclerViewDialog(@NonNull Context context, String titleText, String btnText, RecyclerView.Adapter adapter,Listenner listenner) {
        super(context);
        this.context = context;
        this.titleText =titleText;
        this.btnText = btnText;
        this.adapter = adapter;
        this.listenner = listenner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recyclerview, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
        tvTitle = view.findViewById(R.id.tv_title);
        btn = view.findViewById(R.id.btn);
        recyclerView = view.findViewById(R.id.rv);
        tvTitle.setText(titleText);
        btn.setText(btnText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onBtnClickListen(RecyclerViewDialog.this);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(context,5);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public interface Listenner{
        void onBtnClickListen(Dialog dialog);
    }
}
