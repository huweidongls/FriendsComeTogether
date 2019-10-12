package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.webpages.RenWuWebActivity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenWuActivity extends BaseActivity {

    @BindView(R.id.tv_yi_xuan_huodong)
    TextView tvYiXuanZeHuoDong;
    @BindView(R.id.tv_yi_xuan_qishu)
    TextView tvYiXuanQiShu;
    private static final int REQUEST_CODE_XUAN_ZE_HUO_DONG =1;

    private DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDongModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_wu);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        yiXuanHuoDongModel = new DuiZhangXuanZeHuoDongModel.ObjBean();
        tvYiXuanZeHuoDong.setText("请选择活动");

    }
    @OnClick({R.id.activity_ren_wu_rl_back,R.id.ll_1,R.id.ll_2,R.id.ll_3,R.id.ll_4,R.id.ll_5,R.id.ll_6,R.id.ll_7,R.id.tv_yi_xuan_huodong})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_ren_wu_rl_back:
                onBackPressed();
                break;
            case R.id.ll_1:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_2:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_3:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_4:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_5:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.ll_6:
                startWeb("http://www.tongbanapp.com/action/ac_coupon/questionAnswerGame");//知识问答链接
                break;
            case R.id.ll_7:
                if (yiXuanHuoDongModel == null|| TextUtils.isEmpty(yiXuanHuoDongModel.getPfID())||yiXuanHuoDongModel.getPfID() == null){
                    Intent itHuoDong = new Intent(RenWuActivity.this, DuiZhangXuanZeHuoDongActivity.class);
                    startActivityForResult(itHuoDong, REQUEST_CODE_XUAN_ZE_HUO_DONG);
                    break;
                }
                youxi();
                break;
            case R.id.tv_yi_xuan_huodong:
                Intent itHuoDong = new Intent(RenWuActivity.this, DuiZhangXuanZeHuoDongActivity.class);
                startActivityForResult(itHuoDong, REQUEST_CODE_XUAN_ZE_HUO_DONG);
                break;
        }
    }

    private void youxi() {
        Intent intent = new Intent(RenWuActivity.this,YouXiFenZuActivity.class);
        intent.putExtra("yiXuanHuoDongModel",yiXuanHuoDongModel);
        startActivity(intent);

    }

    private void startWeb(String string){
        Intent intent = new Intent();
        intent.setClass(RenWuActivity.this,RenWuWebActivity.class);
        intent.putExtra("url",string);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_XUAN_ZE_HUO_DONG && resultCode == 1){
            yiXuanHuoDongModel = (DuiZhangXuanZeHuoDongModel.ObjBean) data.getSerializableExtra("xuanzehuodong");
            if (yiXuanHuoDongModel != null){
                tvYiXuanZeHuoDong.setText(yiXuanHuoDongModel.getPftitle());
                tvYiXuanQiShu.setText(yiXuanHuoDongModel.getPhase_num()+" ["+yiXuanHuoDongModel.getPhase_begin_time().substring(5,yiXuanHuoDongModel.getPhase_begin_time().length())+"] ");
            }
        }
    }
}
