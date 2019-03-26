package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.newadapter.EditorLabelSaveAdapter;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;
import com.yiwo.friendscometogether.widget.NestedRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonLabelActivity extends BaseActivity {


    @BindView(R.id.rl0)
    RelativeLayout rl0;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.rl6)
    RelativeLayout rl6;
    @BindView(R.id.rl7)
    RelativeLayout rl7;

    @BindView(R.id.rv0)
    NestedRecyclerView rv0;
    @BindView(R.id.rv1)
    NestedRecyclerView rv1;
    @BindView(R.id.rv2)
    NestedRecyclerView rv2;
    @BindView(R.id.rv3)
    NestedRecyclerView rv3;
    @BindView(R.id.rv4)
    NestedRecyclerView rv4;
    @BindView(R.id.rv5)
    NestedRecyclerView rv5;
    @BindView(R.id.rv6)
    NestedRecyclerView rv6;
    @BindView(R.id.rv7)
    NestedRecyclerView rv7;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;

    @BindView(R.id.tv_text_label)
    TextView tv_text_label;
    @BindView(R.id.tv_text_hobby)
    TextView tv_text_hobby;
//    private EditorLabelSaveAdapter adapter0;
    private EditorLabelSaveAdapter adapter1;
    private EditorLabelSaveAdapter adapter2;
    private EditorLabelSaveAdapter adapter3;
    private EditorLabelSaveAdapter adapter4;
    private EditorLabelSaveAdapter adapter5;
    private EditorLabelSaveAdapter adapter6;
    private EditorLabelSaveAdapter adapter7;

    private Context context = PersonLabelActivity.this;
    private SpImp spImp;
    private String userName;
    private String userSex;
    private PersonMainModel.ObjBean.UsertagBean model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_label);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonLabelActivity.this);
        spImp = new SpImp(context);
        FlowLayoutManager manager0 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv0.setLayoutManager(manager0);
        FlowLayoutManager manager1 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv1.setLayoutManager(manager1);
        FlowLayoutManager manager2 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv2.setLayoutManager(manager2);
        FlowLayoutManager manager3 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv3.setLayoutManager(manager3);
        FlowLayoutManager manager4 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv4.setLayoutManager(manager4);
        FlowLayoutManager manager5 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv5.setLayoutManager(manager5);
        FlowLayoutManager manager6 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv6.setLayoutManager(manager6);
        FlowLayoutManager manager7 = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv7.setLayoutManager(manager7);
        initData();
    }

    private void initData() {
//        intent.putExtra("userName",model.getObj().getInfo().getUsername());
//        intent.putExtra("userSex",ta);
//        intent.putExtra("labelData",model.getObj().getUsertag());
        userName = getIntent().getStringExtra("userName");
        userSex = getIntent().getStringExtra("userSex");
        model = (PersonMainModel.ObjBean.UsertagBean) getIntent().getSerializableExtra("labelData");
        tvTitle.setText(userSex+"的标签");
//        tv0.setText("");
        tv1.setText(userSex+"还没填写个性标签");
        tv2.setText(userSex+"未填写喜欢的运动");
        tv3.setText(userSex+"未填写喜欢的音乐");
        tv4.setText(userSex+"未填写喜欢的食物");
        tv5.setText(userSex+"未填写喜欢的电影");
        tv6.setText(userSex+"未填写喜欢的书和动漫");
        tv7.setText(userSex+"未填写旅行足迹");
        tv_text_hobby.setText(userSex+"的兴趣");
        tv_text_label.setText(userSex+"的标签");

//        adapter0 = new EditorLabelSaveAdapter(s0, 0);
//        rv0.setAdapter(adapter0);
//        if(!TextUtils.isEmpty(model.getPersonality())){
//            tv0.setVisibility(View.GONE);
//            rv0.setVisibility(View.VISIBLE);
//        }else {
//            tv0.setVisibility(View.VISIBLE);
//            rv0.setVisibility(View.GONE);
//        }

        String[] s1 = model.getPersonality().split(",");
        adapter1 = new EditorLabelSaveAdapter(s1, 1);
        rv1.setAdapter(adapter1);
        if(!TextUtils.isEmpty(model.getPersonality())){
            tv1.setVisibility(View.GONE);
            rv1.setVisibility(View.VISIBLE);
        }else {
            tv1.setVisibility(View.VISIBLE);
            rv1.setVisibility(View.GONE);
        }
        tv2.setVisibility(View.VISIBLE);
        rv2.setVisibility(View.VISIBLE);
        tv3.setVisibility(View.VISIBLE);
        rv3.setVisibility(View.VISIBLE);
        tv4.setVisibility(View.VISIBLE);
        rv4.setVisibility(View.VISIBLE);
        tv5.setVisibility(View.VISIBLE);
        rv5.setVisibility(View.VISIBLE);
        tv6.setVisibility(View.VISIBLE);
        rv6.setVisibility(View.VISIBLE);
        tv7.setVisibility(View.VISIBLE);
        rv7.setVisibility(View.VISIBLE);

        String[] s2 = model.getMotion().split(",");
        if(TextUtils.isEmpty(model.getMotion())){
            tv2.setText(userSex+"未填写喜欢的运动");
            rv2.setVisibility(View.GONE);
        }else {
            tv2.setText(userSex+"喜欢的运动");
        }
        adapter2 = new EditorLabelSaveAdapter(s2, 2);
        rv2.setAdapter(adapter2);

        String[] s3 = model.getMusic().split(",");
        if(TextUtils.isEmpty(model.getMusic())){
            tv3.setText(userSex+"未填写喜欢的音乐");
            rv3.setVisibility(View.GONE);
        }else {
            tv3.setText(userSex+"喜欢的音乐");
        }
        adapter3 = new EditorLabelSaveAdapter(s3, 3);
        rv3.setAdapter(adapter3);

        String[] s4 = model.getDelicious().split(",");
        if(TextUtils.isEmpty(model.getDelicious())){
            tv4.setText(userSex+"未填写喜欢的食物");
            rv4.setVisibility(View.GONE);
        }else {
            tv4.setText(userSex+"喜欢的食物");
        }
        adapter4 = new EditorLabelSaveAdapter(s4, 4);
        rv4.setAdapter(adapter4);

        String[] s5 = model.getFilm().split(",");
        if(TextUtils.isEmpty(model.getFilm())){
            tv5.setText(userSex+"未填写喜欢的电影");
            rv5.setVisibility(View.GONE);
        }else {
            tv5.setText(userSex+"喜欢的电影");
        }
        adapter5 = new EditorLabelSaveAdapter(s5, 5);
        rv5.setAdapter(adapter5);

        String[] s6 = model.getBook().split(",");
        if(TextUtils.isEmpty(model.getBook())){
            tv6.setText(userSex+"未填写喜欢的书和动漫");
            rv6.setVisibility(View.GONE);
        }else {
            tv6.setText(userSex+"喜欢的书和动漫");
        }
        adapter6 = new EditorLabelSaveAdapter(s6, 6);
        rv6.setAdapter(adapter6);

        String[] s7 = model.getTravel().split(",");
        if(TextUtils.isEmpty(model.getTravel())){
            tv7.setText(userSex+"未填写旅行足迹");
            rv7.setVisibility(View.GONE);
        }else {
            tv7.setText(userSex+"的旅行足迹");
        }

        adapter7 = new EditorLabelSaveAdapter(s7, 7);
        rv7.setAdapter(adapter7);

    }
    @OnClick({R.id.rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
