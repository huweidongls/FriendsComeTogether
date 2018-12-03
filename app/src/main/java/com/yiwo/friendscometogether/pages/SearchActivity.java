package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.czp.searchmlist.mSearchLayout;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.AllSearchModel;
import com.yiwo.friendscometogether.model.SearchListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchActivity extends BaseActivity {
    private Unbinder unbinder;
    @BindView(R.id.mSearch)
    mSearchLayout mSearch;
    //热门搜索数据
    String shareHotData = "日月潭,卢步坡,太阳岛";
    List skillHots = Arrays.asList(shareHotData.split(","));
    //历史记录
    String shareData = "";
    List skills;

    private String type;

    private List<String> list;

    private SpImp spImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        type = getIntent().getStringExtra("type");
        unbinder = ButterKnife.bind(this);
        spImp = new SpImp(SearchActivity.this);

        initData();

    }

    private void initData() {

        shareData = spImp.getSearch();
        if (TextUtils.isEmpty(shareData) || shareData.equals("")) {
            skills = new ArrayList();
        } else {
            skills = Arrays.asList(shareData.split(","));
        }

        ViseHttp.POST(NetConfig.allSearchUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.allSearchUrl))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                AllSearchModel model = gson.fromJson(data, AllSearchModel.class);
                                list = new ArrayList<>();
                                for (int i = 0; i < model.getObj().size(); i++) {
                                    list.add(model.getObj().get(i).getTitle());
                                }
                                mSearch.initData(skills, list, new mSearchLayout.setSearchCallBackListener() {
                                    @Override
                                    public void Search(String s) {
                                        Log.i("11111111", s);
                                        ViseHttp.POST(NetConfig.searchFriendTogetherUrl)
                                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.searchFriendTogetherUrl))
                                                .addParam("page", "1")
                                                .addParam("activity_name", s)
                                                .addParam("type", type)
                                                .request(new ACallback<String>() {
                                                    @Override
                                                    public void onSuccess(String data) {
                                                        SearchListModel model = new Gson().fromJson(data, SearchListModel.class);
                                                        if (model.getCode() == 200) {
                                                            if (model.getObj().size() == 0) {
                                                                toToast(SearchActivity.this, "暂无搜索结果");
                                                            } else {
                                                                Intent it = new Intent(SearchActivity.this, SearchListActivity.class);
                                                                it.putExtra("list", (Serializable) model.getObj());
                                                                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(it);
                                                                finish();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFail(int errCode, String errMsg) {
                                                        toToast(SearchActivity.this, errMsg);
                                                    }
                                                });
                                    }

                                    @Override
                                    public void Back() {//取消
                                        finish();
                                    }

                                    @Override
                                    public void ClearOldData() {
                                        spImp.setSearch("");
                                        shareData = "";
                                    }

                                    @Override
                                    public void SaveOldData(ArrayList<String> arrayList) {
                                        shareData = "";
                                        for (int i = 0; i < arrayList.size(); i++) {
                                            if (i == arrayList.size() - 1) {
                                                shareData = shareData + arrayList.get(i);
                                            } else {
                                                shareData = shareData + arrayList.get(i) + ",";
                                            }
                                        }
                                        Log.e("2333", shareData);
                                        spImp.setSearch(shareData);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }
}
