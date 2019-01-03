package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYePicsAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJiAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJuAdapter;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.pages.OtherInformationActivity;
import com.yiwo.friendscometogether.pages.OtherPicActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonMainActivity extends BaseActivity {

    @BindView(R.id.recycler_view_pics)
    RecyclerView recyclerView_pics;
    @BindView(R.id.recycler_view_youji)
    RecyclerView recyclerView_youji;
    @BindView(R.id.recycler_view_youju)
    RecyclerView recyclerView_youju;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    // 他人主页 或 我的主页 有区别的控件；
    @BindView(R.id.tv_title_wode_or_tade)
    TextView tv_title_wode_or_tade;
    @BindView(R.id.tv_pics_wode_or_tade)
    TextView tv_pics_wode_or_tade;
    @BindView(R.id.tv_youji_wode_or_tade)
    TextView tv_youji_wode_or_tade;
    @BindView(R.id.tv_youju_wode_or_tade)
    TextView tv_youju_wode_or_tade;
    //头像右侧控件
    @BindView(R.id.rl_algin_right_tade)
    RelativeLayout rl_algin_right_tade;
    @BindView(R.id.rl_algin_right_wode)
    RelativeLayout rl_algin_right_wode;

    //查看全部
    @BindView(R.id.ll_person_all_pics)
    LinearLayout ll_person_all_pics;

    private SpImp spImp;
    private int type_tade_or_wode = 0;//0 为他的 1 为我的
    private String person_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonMainActivity.this);
        spImp = new SpImp(PersonMainActivity.this);
        type_tade_or_wode = getIntent().getIntExtra("type_tade_or_wode",0);
        person_id = getIntent().getStringExtra("person_id");
        initData();
    }
    private void initData() {

        if (type_tade_or_wode==0){
            tv_title_wode_or_tade.setText("他的主页");
            tv_pics_wode_or_tade.setText("他的照片");
            tv_youji_wode_or_tade.setText("他的友记");
            tv_youju_wode_or_tade.setText("他的友聚");
            rl_algin_right_tade.setVisibility(View.VISIBLE);
            rl_algin_right_wode.setVisibility(View.GONE);
        }else if (type_tade_or_wode == 1){
            tv_title_wode_or_tade.setText("我的主页");
            tv_pics_wode_or_tade.setText("我的照片");
            tv_youji_wode_or_tade.setText("我的友记");
            tv_youju_wode_or_tade.setText("我的友聚");
            rl_algin_right_tade.setVisibility(View.GONE);
            rl_algin_right_wode.setVisibility(View.VISIBLE);
        }
        GridLayoutManager manager = new GridLayoutManager(PersonMainActivity.this, 3);
        recyclerView_pics.setLayoutManager(manager);
        List<String> list = new ArrayList<>();
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        list.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3585167714,172627266&fm=173&app=49&f=JPEG?w=640&h=497&s=1E8E136D4E4A74559805DDA20300F009");
        recyclerView_pics.setAdapter(new TaRenZhuYePicsAdapter(list,person_id));
        LinearLayoutManager manager1 = new LinearLayoutManager(PersonMainActivity.this);
        List<String> list1 = new ArrayList<>();
        list1.add("");
        list1.add("");
        recyclerView_youji.setLayoutManager(manager1);
        recyclerView_youji.setAdapter(new TaRenZhuYeYouJiAdapter(list1));
        LinearLayoutManager manager2 = new LinearLayoutManager(PersonMainActivity.this);
        List<String> list2 = new ArrayList<>();
        list2.add("");
        list2.add("");
        recyclerView_youju.setLayoutManager(manager2);
        recyclerView_youju.setAdapter(new TaRenZhuYeYouJuAdapter(list2));
    }
    @OnClick({R.id.rl_back,R.id.ll_person_all_pics})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_person_all_pics:
                if (type_tade_or_wode == 1){
                    intent.setClass(PersonMainActivity.this, MyPicturesActivity.class);
                }else if (type_tade_or_wode == 0){
                    intent.putExtra("otheruid",person_id);
                    intent.setClass(PersonMainActivity.this, OtherPicActivity.class);
                }
                startActivity(intent);
                break;
        }
    }
}
