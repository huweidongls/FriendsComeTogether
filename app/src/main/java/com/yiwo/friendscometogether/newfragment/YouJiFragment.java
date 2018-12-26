package com.yiwo.friendscometogether.newfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.sp.SpImp;

import butterknife.ButterKnife;

/**
 * Created by ljc on 2018/12/26.
 */

public class YouJiFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apply_huodong,null);
        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);

        return view;
    }
}
