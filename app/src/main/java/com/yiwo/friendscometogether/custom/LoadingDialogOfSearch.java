package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

/**
 * Created by ljc on 2019/2/26.
 */

public class LoadingDialogOfSearch extends Dialog{
    private  Context context;
    private ImageView ivLoading;
    public LoadingDialogOfSearch(@NonNull Context context) {
        super(context);
        this.context =context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }
    @Override
    protected void onStop() {
        super.onStop();
        ivLoading.clearAnimation();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_of_search, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(params);
        ivLoading = view.findViewById(R.id.iv_loading);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.sayhi_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(interpolator);
        ivLoading.startAnimation(rotateAnimation);
    }
}
