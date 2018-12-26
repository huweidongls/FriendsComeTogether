package com.yiwo.friendscometogether.newpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYePicsAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJiAdapter;
import com.yiwo.friendscometogether.newadapter.TaRenZhuYeYouJuAdapter;
import com.yiwo.friendscometogether.pages.EditorFriendTogetherSubTitleContentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherMainActivity extends BaseActivity {

    @BindView(R.id.recycler_view_pics)
    RecyclerView recyclerView_pics;
    @BindView(R.id.recycler_view_youji)
    RecyclerView recyclerView_youji;
    @BindView(R.id.recycler_view_youju)
    RecyclerView recyclerView_youju;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_main);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(OtherMainActivity.this);
        
        initData();
    }
    private void initData() {
        GridLayoutManager manager = new GridLayoutManager(OtherMainActivity.this, 3);
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
        recyclerView_pics.setAdapter(new TaRenZhuYePicsAdapter(list));
        LinearLayoutManager manager1 = new LinearLayoutManager(OtherMainActivity.this);
        List<String> list1 = new ArrayList<>();
        list1.add("");
        list1.add("");
        recyclerView_youji.setLayoutManager(manager1);
        recyclerView_youji.setAdapter(new TaRenZhuYeYouJiAdapter(list1));
        LinearLayoutManager manager2 = new LinearLayoutManager(OtherMainActivity.this);
        List<String> list2 = new ArrayList<>();
        list2.add("");
        list2.add("");
        recyclerView_youju.setLayoutManager(manager2);
        recyclerView_youju.setAdapter(new TaRenZhuYeYouJuAdapter(list2));
    }
    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
