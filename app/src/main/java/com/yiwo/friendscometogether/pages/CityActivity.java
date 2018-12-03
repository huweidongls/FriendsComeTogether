package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.CityAdapter;
import com.yiwo.friendscometogether.adapter.OtherCityAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.SlideBar;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.model.OtherCityModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.utils.UserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zhouzhuo.zzletterssidebar.ZzLetterSideBar;
import me.zhouzhuo.zzletterssidebar.interf.OnLetterTouchListener;

public class CityActivity extends BaseActivity {

    @BindView(R.id.rl_city_return)
    RelativeLayout returnRl;
    @BindView(R.id.lv_city)
    ListView cityLv;
    @BindView(R.id.sb_city)
    SlideBar citySb;
    @BindView(R.id.rl_reset)
    RelativeLayout rlReset;
    @BindView(R.id.rl_city)
    RelativeLayout rlCity;
    @BindView(R.id.rl_other)
    RelativeLayout rlOtherCity;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_other_city)
    TextView tvOtherCity;

    List<CityModel> list;
    String[] letter;
    private CityAdapter adapter;

    private ListView listView;
    private ZzLetterSideBar sideBar;
    private TextView dialog;
    private OtherCityAdapter otherCityAdapter;
    private List<OtherCityModel.ObjBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(CityActivity.this);
        initData();
        setListener();
        loadHot();
        loadCity();
        initOtherCity();
    }

    @OnClick({R.id.tv_city, R.id.tv_other_city})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_city:
                tvCity.setTextColor(Color.parseColor("#ff9d00"));
                tvOtherCity.setTextColor(Color.parseColor("#333333"));
                rlCity.setVisibility(View.VISIBLE);
                rlOtherCity.setVisibility(View.GONE);
                break;
            case R.id.tv_other_city:
                tvCity.setTextColor(Color.parseColor("#333333"));
                tvOtherCity.setTextColor(Color.parseColor("#ff9d00"));
                rlCity.setVisibility(View.GONE);
                rlOtherCity.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initOtherCity() {

        sideBar = (ZzLetterSideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.tv_dialog);
        listView = (ListView) findViewById(R.id.list_view);

        ViseHttp.POST(NetConfig.otherCityUrl)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                OtherCityModel model = gson.fromJson(data, OtherCityModel.class);
                                mList = model.getObj();
                                otherCityAdapter = new OtherCityAdapter(CityActivity.this, mList);
                                listView.setAdapter(otherCityAdapter);
                                //设置右侧触摸监听
                                sideBar.setLetterTouchListener(listView, otherCityAdapter, dialog, new OnLetterTouchListener() {
                                    @Override
                                    public void onLetterTouch(String letter, int position) {
                                    }

                                    @Override
                                    public void onActionUp() {
                                    }
                                });
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent it = new Intent();
                                        it.putExtra("cityid", mList.get(position).getId());
                                        it.putExtra("city", mList.get(position).getName());
                                        setResult(3, it);
                                        finish();
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

    public void initData() {
        list = new ArrayList<>();
        adapter = new CityAdapter(this, list);
        letter = getResources().getStringArray(R.array.lowerletter);
        cityLv.setAdapter(adapter);

//        CityModel model = (CityModel) getIntent().getSerializableExtra("model");
        CityModel model = UserUtils.readCity(CityActivity.this);
        if (model != null) {
            list.add(model);
        }
    }

    private void setListener() {
        returnRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.putExtra(ActivityConfig.CITY, "");
                setResult(2, it);
                finish();
            }
        });
        citySb.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(MotionEvent event, String s) {
                for (int i = 0; i < list.size(); i++) {
                    if (s.equals(list.get(i).getName())) {
                        cityLv.setSelection(i);
                    }
                }
            }
        });
        cityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!list.get(position).getId().equals("-1")||position==0) {
//                    toToast(CityActivity.this, list.get(position).getName());
                    String ac = getIntent().getStringExtra(ActivityConfig.ACTIVITY);
                    Intent it = new Intent();
                    if (ac.equals("youju")) {
                        it.setClass(CityActivity.this, CreateFriendTogetherActivity.class);
                    } else {
                        it.setClass(CityActivity.this, MainActivity.class);
                    }
                    it.putExtra(ActivityConfig.CITY, list.get(position));
                    setResult(1, it);
                    finish();
                }
            }
        });
    }

    public void loadHot() {
        String token = getToken(NetConfig.BaseUrl + NetConfig.hotCityUrl);
        ViseHttp.POST(NetConfig.hotCityUrl)
                .addParam("app_key", token)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.optInt("code");
                            JSONArray arr = jsonObject.optJSONArray("obj");
                            if (code == 200) {
                                if (arr != null && arr.length() != 0) {
                                    CityModel c = new CityModel();
                                    c.setId("-1");
                                    c.setName("热门城市");
                                    list.add(c);
                                    for (int j = 0; j < arr.length(); j++) {
                                        JSONObject o = arr.optJSONObject(j);
                                        CityModel cm = new CityModel();
                                        cm.setId(o.optString("city_id"));
                                        cm.setName(o.optString("city_name"));
                                        list.add(cm);
                                    }
                                }
                            } else {
                                toToast(CityActivity.this, jsonObject.optString("message").toString());
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
//        OkHttpUtils.post()
//                .tag(this)
//                .url(NetConfig.BaseUrl + NetConfig.hotCityUrl)
//                .addParams("app_key", token)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            int code = jsonObject.optInt("code");
//                            JSONArray arr = jsonObject.optJSONArray("obj");
//                            if (code == 200) {
//                                if (arr != null && arr.length() != 0) {
//                                    CityModel c = new CityModel();
//                                    c.setId("-1");
//                                    c.setName("热门城市");
//                                    list.add(c);
//                                    for (int j = 0; j < arr.length(); j++) {
//                                        JSONObject o = arr.optJSONObject(j);
//                                        CityModel cm = new CityModel();
//                                        cm.setId(o.optString("city_id"));
//                                        cm.setName(o.optString("city_name"));
//                                        list.add(cm);
//                                    }
//                                }
//                            } else {
//                                toToast(CityActivity.this, jsonObject.optString("message").toString());
//                            }
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }

    public void loadCity() {
        String token = getToken(NetConfig.BaseUrl + NetConfig.cityUrl);
        ViseHttp.POST(NetConfig.cityUrl)
                .addParam("app_key", token)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.optInt("code");
                            JSONObject jsonobj = jsonObject.optJSONObject("obj");
                            if (code == 200) {
                                for (int i = 0; i < letter.length; i++) {
                                    JSONArray arr = jsonobj.optJSONArray(letter[i]);
                                    if (arr != null) {
                                        CityModel c = new CityModel();
                                        c.setId("-1");
                                        c.setName(letter[i].toUpperCase());
                                        list.add(c);
                                        for (int j = 0; j < arr.length(); j++) {
                                            JSONObject o = arr.optJSONObject(j);
                                            CityModel cm = new CityModel();
                                            cm.setId(o.optString("ID"));
                                            cm.setName(o.optString("name"));
                                            list.add(cm);
                                        }
                                    }
                                }
                            } else {
                                toToast(CityActivity.this, jsonObject.optString("message").toString());
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
//        OkHttpUtils.post()
//                .tag(this)
//                .url(NetConfig.BaseUrl + NetConfig.cityUrl)
//                .addParams("app_key", token)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            int code = jsonObject.optInt("code");
//                            JSONObject jsonobj = jsonObject.optJSONObject("obj");
//                            if (code == 200) {
//                                for (int i = 0; i < letter.length; i++) {
//                                    JSONArray arr = jsonobj.optJSONArray(letter[i]);
//                                    if (arr != null) {
//                                        CityModel c = new CityModel();
//                                        c.setId("-1");
//                                        c.setName(letter[i].toUpperCase());
//                                        list.add(c);
//                                        for (int j = 0; j < arr.length(); j++) {
//                                            JSONObject o = arr.optJSONObject(j);
//                                            CityModel cm = new CityModel();
//                                            cm.setId(o.optString("ID"));
//                                            cm.setName(o.optString("name"));
//                                            list.add(cm);
//                                        }
//                                    }
//                                }
//                            } else {
//                                toToast(CityActivity.this, jsonObject.optString("message").toString());
//                            }
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }
}
