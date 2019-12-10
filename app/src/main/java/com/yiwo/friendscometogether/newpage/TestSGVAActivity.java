package com.yiwo.friendscometogether.newpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestSGVAActivity extends BaseActivity {

    @BindView(R.id.svga)
    SVGAImageView imageView;
    @BindView(R.id.btn)
    Button button;
    @BindView(R.id.tv_info)
    TextView tv_info;
    private SVGAParser parser;
//    private List<String> list = new ArrayList<>();
//    private boolean aBoolean = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sgva);
        ButterKnife.bind(this, TestSGVAActivity.this);
//        for (int i =0;i<5;i++){
//            list.add("svga_"+i+".svga");
//        }
//        SVGAImageView imageView = new SVGAImageView(this);
        imageView.setLoops(1);
        imageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
//                aBoolean = false;
//                tv_info.setText("当前list中 含有"+list.size()+"个！！");
//                if (list.size()>0){
//                    startSVGA(list.get(0));
//                    list.remove(0);
//                }
            }

            @Override
            public void onRepeat() {

            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        parser = new SVGAParser(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int aa = (int) Math.floor(Math.random() * 5);
//                list.add("svga_"+aa+".svga");
//                tv_info.setText("当前list中 含有"+list.size()+"个！！");
//                if (!aBoolean){
//                    startSVGA(list.get(0));
//                }
            }
        });
    }

    private void startSVGA(String s) {
//        aBoolean = true;
        parser.parse(s, new SVGAParser.ParseCompletion() {

            @Override
            public void onError() {

            }

            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
//                dynamicEntity.setClickArea("img_10");
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity, dynamicEntity);
                imageView.setImageDrawable(drawable);
                imageView.startAnimation();
            }
        });
    }
}
