package com.yiwo.friendscometogether.imagepreview;

import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

public class ImagePreviewActivity extends AppCompatActivity {

    private int itemPosition;
    private List<String> imageList;
    private CustomViewPager viewPager;
    private LinearLayout main_linear;
    private boolean mIsReturning;
    private int mStartPosition;
    private int mCurrentPosition;
    private ImagePreviewAdapter adapter;
    private RelativeLayout rlSetHeadIcon;
    private SpImp spImp;

    private Boolean isFromMyPics;
    private List<String> listPicId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        StatusBarUtils.setStatusBarTransparent(ImagePreviewActivity.this);
        spImp = new SpImp(ImagePreviewActivity.this);
//        initShareElement();
        getIntentData();
        initView();
        renderView();
        getData();
        setListener();
    }
    //    private void initShareElement() {
//        setEnterSharedElementCallback(mCallback);
//    }

    private void setListener() {
        main_linear.getChildAt(mCurrentPosition).setEnabled(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideAllIndicator(position);
                main_linear.getChildAt(position).setEnabled(true);
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                final float normalizedposition = Math.abs(Math.abs(position) - 1);
//                page.setScaleX(normalizedposition / 2 + 0.5f);
//                page.setScaleY(normalizedposition / 2 + 0.5f);
//            }
//        });
        rlSetHeadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImagePreviewActivity.this);
                builder.setMessage("确定设置为头像？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("listPicId_",listPicId.get(mCurrentPosition));
                                ViseHttp.POST(NetConfig.setupHeaderFromPics)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.setupHeaderFromPics))
                                        .addParam("listid", listPicId.get(mCurrentPosition))
                                        .addParam("uid",spImp.getUID())
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                Toast.makeText(ImagePreviewActivity.this,"修改头像成功！",Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFail(int errCode, String errMsg) {

                                            }
                                        });
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    private void hideAllIndicator(int position) {
        for (int i = 0; i < imageList.size(); i++) {
            if (i != position) {
                main_linear.getChildAt(i).setEnabled(false);
            }
        }
    }

    private void initView() {
        viewPager = (CustomViewPager) findViewById(R.id.imageBrowseViewPager);
        main_linear = (LinearLayout) findViewById(R.id.main_linear);
        rlSetHeadIcon = findViewById(R.id.rl_set_head_icon);
    }

    private void renderView() {
        if (imageList == null) return;
        if (imageList.size() == 1) {
            main_linear.setVisibility(View.GONE);
        } else {
            main_linear.setVisibility(View.VISIBLE);
        }
        adapter = new ImagePreviewAdapter(this, imageList, itemPosition);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mCurrentPosition);
        if (isFromMyPics){
            rlSetHeadIcon.setVisibility(View.VISIBLE);
        }else {
            rlSetHeadIcon.setVisibility(View.GONE);
        }
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mStartPosition = getIntent().getIntExtra(Consts.START_IAMGE_POSITION, 0);
            mCurrentPosition = mStartPosition;
            itemPosition = getIntent().getIntExtra(Consts.START_ITEM_POSITION, 0);
            imageList = getIntent().getStringArrayListExtra("imageList");
            isFromMyPics = getIntent().getBooleanExtra("fromMyPics",false);
            if (isFromMyPics){
                listPicId = getIntent().getStringArrayListExtra("picListIDList");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, R.anim.photoview_close);
    }

    /**
     * 获取数据
     */
    private void getData() {

        View view;
        for (String pic : imageList) {

            //创建底部指示器(小圆点)
            view = new View(ImagePreviewActivity.this);
            view.setBackgroundResource(R.drawable.indicator);
            view.setEnabled(false);
            //设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            //设置间隔
//            if (!pic.equals(imageList.get(0))) {
            layoutParams.rightMargin = 20;
//            }
            //添加到LinearLayout
            main_linear.addView(view, layoutParams);
        }
    }


//    @Override
//    public void finishAfterTransition() {
//        mIsReturning = true;
//        Intent data = new Intent();
//        data.putExtra(Consts.START_IAMGE_POSITION, mStartPosition);
//        data.putExtra(Consts.CURRENT_IAMGE_POSITION, mCurrentPosition);
//        data.putExtra(Consts.CURRENT_ITEM_POSITION, itemPosition);
//        setResult(RESULT_OK, data);
//        super.finishAfterTransition();
//    }


//    private final SharedElementCallback mCallback = new SharedElementCallback() {
//
//        @Override
//        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//            if (mIsReturning) {
//                ImageView sharedElement = adapter.getPhotoView();
//                if (sharedElement == null) {
//                    names.clear();
//                    sharedElements.clear();
//                } else if (mStartPosition != mCurrentPosition) {
//                    names.clear();
//                    names.add(sharedElement.getTransitionName());
//                    sharedElements.clear();
//                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
//                }
//            }
//        }
//    };
}
