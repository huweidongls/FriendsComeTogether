package com.yiwo.friendscometogether.wangyiyunshipin.server;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.common.http.NimHttpClient;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.config.DemoServers;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.AddVideoResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.RoomInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.TranscodeResponseEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.VideoInfoEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与应用服务器通信
 */
public class DemoServerHttpClient {
    private static final String TAG = "DemoServerHttpClient";
    public static final String TEST_HOST = "vcloud.163.com"; //用于 ping ip地址,测试、网络是否连通

    // code
    private static final int RESULT_CODE_SUCCESS = 200;

    // api
    private static final String API_REGISTER_SMS_CODE = "/user/reg/sendCode";
    private static final String API_LOGIN_SMS_CODE = "/user/phoneLogin/sendCode";
    private static final String API_NAME_REGISTER = "/user/reg";
    private static final String API_LOGIN = "/user/login";
    private static final String API_PHONE_LOGIN = "/user/phoneLogin";
    private static final String API_LOGOUT = "/user/logout";
    private static final String API_CREATE_ROOM = "/room/create";
    private static final String API_ENTER_ROOM = "/room/enter";
    private static final String API_LEAVE_ROOM = "/room/leave";
    //点播相关 api
    private static final String API_VIDEO_INFO_GET = "/vod/videoinfoget";
    private static final String API_VIDEO_ADD = "/vod/videoadd";
    private static final String API_VIDEO_DELETE = "/vod/videodelete";
    private static final String API_VIDEO_TRANS_STATE = "/vod/videotranscodestatus";

    // header
    private static final String HEADER_KEY_APP_KEY = "appkey";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_DEMO_ID = "Demo-Id";
    private static final String HEADER_DEMO_ID_VALUE = "video-live";

    // request sms code
    private static final String REQUEST_PHONE = "phone";

    // request login
    private static final String REQUEST_USER_NAME = "username";
    private static final String REQUEST_NICK_NAME = "nickname";
    private static final String REQUEST_PASSWORD = "password";
    private static final String REQUEST_ACCID = "accid";
    private static final String REQUEST_VERIFY_CODE = "verifyCode";

    //request room
    public static final String REQUEST_SID = "sid";
    public static final String REQUEST_ROOM_ID = "roomId";
    public static final String REQUEST_PULL_URL = "pullUrl";
    public static final String CID = "cid";
    public static final String REQUEST_DEVICE_ID = "deviceId";

    //request vod
    public static final String VID = "vid";
    public static final String VIDS = "vids";
    public static final String FILE_NAME = "name";
    public static final String VIDEO_FORMAT = "format";
    public static final String TYPE = "type";

    // result
    private static final String RESULT_KEY_CODE = "code";
    private static final String RESULT_KEY_ERROR_MSG = "msg";

    private static final String RESULT_KEY_IMTOKEN = "imToken";
    private static final String RESULT_KEY_VODTOKEN = "vodToken";
    private static final String RESULT_KEY_DATA = "data";
    private static final String RESULT_KEY_TOKEN = "token";
    private static final String RESULT_KEY_VOD_TOKEN = "vodtoken";
    private static final String RESULT_KEY_SID = "sid";

    public static final String RESULT_ROOMID = "roomId";
    public static final String RESULT_PUSH_URL = "pushUrl";
    public static final String RESULT_RTMP_URL = "rtmpPullUrl";
    public static final String RESULT_HLS_URL = "hlsPullUrl";
    public static final String RESULT_HTTP_URL = "httpPullUrl";
    private static final String RESULT_OWNER = "owner";
    public static final String RESULT_LIVE_STATUS = "liveStatus";

    private static final String RESULT_KEY_TRANSJOBSTATUS = "transjobstatus";
    private static final String RESULT_KEY_COMPLETE_TIME = "completeTime";
    private static final String RESULT_KEY_CREATE_TIME = "createTime";
    private static final String RESULT_KEY_ORIG_URL = "origUrl";
    private static final String RESULT_KEY_UPDATE_TIME = "updateTime";
    private static final String RESULT_KEY_SNAPSHOT_URL = "snapshotUrl";
    private static final String RESULT_KEY_DOWNLOAD_ORIG_URL = "downloadOrigUrl";
    private static final String RESULT_KEY_WIDTH = "width";
    private static final String RESULT_KEY_HEIGHT = "height";
    private static final String RESULT_KEY_VIDEO_NAME = "videoName";
    private static final String RESULT_KEY_TYPE_NAME = "typeName";
    private static final String RESULT_KEY_DURATION = "duration";
    private static final String RESULT_KEY_DESCRIPTION = "description";
    private static final String RESULT_KEY_INITIAL_SIZE = "initialSize";

    public interface DemoServerHttpCallback<T> {
        void onSuccess(T t);

        void onFailed(int code, String errorMsg);
    }

    private static DemoServerHttpClient instance;

    public static synchronized DemoServerHttpClient getInstance() {
        if (instance == null) {
            instance = new DemoServerHttpClient();
        }

        return instance;
    }

    private DemoServerHttpClient() {
        NimHttpClient.getInstance().init(DemoCache.getContext());
    }

    /**
     * 获取注册手机验证码
     */
    public void fetchRegisterVerifyCode(String phone, final DemoServerHttpCallback<String> callback) {
        String url = DemoServers.apiServer() + API_REGISTER_SMS_CODE;
        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_PHONE).append("=").append(phone);

        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "fetch sms code failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
                    if (resCode == RESULT_CODE_SUCCESS) {
                        callback.onSuccess(null);
                    } else {
                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });

    }

    /**
     * 获取登录手机验证码
     */
    public void fetchLoginVerifyCode(String phone, final DemoServerHttpCallback<String> callback) {
        String url = DemoServers.apiServer() + API_LOGIN_SMS_CODE;
        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_PHONE).append("=").append(phone);

        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "fetch sms code failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
                    if (resCode == RESULT_CODE_SUCCESS) {
                        callback.onSuccess(null);
                    } else {
                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });

    }

    /**
     * 向应用服务器创建账号（注册账号）
     * 由应用服务器调用WEB SDK接口将新注册的用户数据同步到云信服务器
     */
    public void register(String account, String nickName, String password, String phone, String verfiyCode, final DemoServerHttpCallback<Void> callback) {
        String url = DemoServers.apiServer() + API_NAME_REGISTER;
        try {
            nickName = URLEncoder.encode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_ACCID).append("=").append(account.toLowerCase()).append("&")
                .append(REQUEST_NICK_NAME).append("=").append(nickName).append("&")
                .append(REQUEST_PASSWORD).append("=").append(MD5.getStringMD5(password)).append("&")
                .append(REQUEST_PHONE).append("=").append(phone).append("&")
                .append(REQUEST_VERIFY_CODE).append("=").append(verfiyCode);

        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
                    if (resCode == RESULT_CODE_SUCCESS) {
                        callback.onSuccess(null);
                    } else {
                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }


    /**
     * 向应用服务器创建账号（注册账号）
     * 由应用服务器调用WEB SDK接口将新注册的用户数据同步到云信服务器
     */
    public void login(String account, String password, final DemoServerHttpCallback<Void> callback) {
        String url = DemoServers.apiServer() + API_LOGIN;

        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_ACCID).append("=").append(account.toLowerCase()).append("&")
                .append(REQUEST_PASSWORD).append("=").append(MD5.getStringMD5(password));

        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
                    if (resCode == RESULT_CODE_SUCCESS) {
                        JSONObject dataObject = resObj.getJSONObject(RESULT_KEY_DATA);
                        if (dataObject != null) {
                            DemoCache.setAccount(dataObject.getString(REQUEST_ACCID));
                            DemoCache.setToken(dataObject.getString(RESULT_KEY_IMTOKEN));
                            DemoCache.setVodtoken(dataObject.getString(RESULT_KEY_VODTOKEN));
                            DemoCache.setSid(dataObject.getString(REQUEST_ACCID));
                        }
                        callback.onSuccess(null);
                    } else {
                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                } catch (Exception e){
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }

    /**
     * 手机验证码登录
     */
    public void phoneLogin(String phone, String verifyCode, final DemoServerHttpCallback<Void> callback) {
        String url = DemoServers.apiServer() + API_PHONE_LOGIN;

        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_PHONE).append("=").append(phone).append("&")
                .append(REQUEST_VERIFY_CODE).append("=").append(verifyCode);

        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
                    if (resCode == RESULT_CODE_SUCCESS) {
                        JSONObject dataObject = resObj.getJSONObject(RESULT_KEY_DATA);
                        if (dataObject != null) {
                            DemoCache.setAccount(dataObject.getString(REQUEST_ACCID));
                            DemoCache.setToken(dataObject.getString(RESULT_KEY_IMTOKEN));
                            DemoCache.setVodtoken(dataObject.getString(RESULT_KEY_VODTOKEN));
                            DemoCache.setSid(dataObject.getString(REQUEST_ACCID));
                        }
                        callback.onSuccess(null);
                    } else {
                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                } catch (Exception e){
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }


    /**
     * 向应用服务器发送登出请求
     */
    public void logout(){
        String url = DemoServers.apiServer() + API_LOGOUT;

        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_SID).append("=").append(DemoCache.getSid());

        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                //登出不保证是否成功
            }
        });
    }

    /**
     * 主播创建房间接口
     * @param context
     * @param callback
     */
//    public void createRoom(Activity context, final DemoServerHttpCallback<RoomInfoEntity> callback){
//        String url = DemoServers.apiServer() + API_CREATE_ROOM;
//
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
//        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);
//
//        StringBuilder body = new StringBuilder();
//        body.append(REQUEST_SID).append("=").append(DemoCache.getSid()).append("&")
//            .append(REQUEST_DEVICE_ID).append("=").append(AndTools.getDeviceId(context));
//
//        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
//            @Override
//            public void onResponse(String response, int code, Throwable exception) {
//                if (code != 200 || exception != null) {
//                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
//                            + (exception != null ? exception.getMessage() : "null"));
//                    if (callback != null) {
//                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
//                    }
//                    return;
//                }
//                try {
//                    JSONObject resObj = JSONObject.parseObject(response);
//                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
//                    if (resCode == RESULT_CODE_SUCCESS) {
//                        JSONObject retObj = resObj.getJSONObject(RESULT_KEY_DATA);
//                        RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
//                        roomInfoEntity.setRoomid(retObj.getInteger(RESULT_ROOMID));
//                        roomInfoEntity.setPushUrl(retObj.getString(RESULT_PUSH_URL));
//                        roomInfoEntity.setRtmpPullUrl(retObj.getString(RESULT_RTMP_URL));
//                        roomInfoEntity.setHlsPullUrl(retObj.getString(RESULT_HLS_URL));
//                        roomInfoEntity.setHttpPullUrl(retObj.getString(RESULT_HTTP_URL));
//                        roomInfoEntity.setCid(retObj.getString(CID));
//                        callback.onSuccess(roomInfoEntity);
//                    } else {
//                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
//                        callback.onFailed(resCode, error);
//                    }
//                } catch (JSONException e) {
//                    callback.onFailed(-1, e.getMessage());
//                } catch (Exception e){
//                    callback.onFailed(-1, e.getMessage());
//                }
//            }
//        });
//    }
//
//    /**
//     * 观众获取房间信息接口
//     * @param mode
//     * @param address
//     * @param callback
//     */
//    public void getRoomInfo(int mode, String address, final DemoServerHttpCallback<RoomInfoEntity> callback){
//        String url = DemoServers.apiServer() + API_ENTER_ROOM;
//
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
//        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);
//
//        StringBuilder body = new StringBuilder();
//        body.append(REQUEST_SID).append("=").append(DemoCache.getSid()).append("&");
//        if(mode == EnterAudienceActivity.MODE_ROOM){
//            body.append(REQUEST_ROOM_ID);
//        }else{
//            body.append(REQUEST_PULL_URL);
//        }
//        body.append("=").append(address);
//
//        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
//            @Override
//            public void onResponse(String response, int code, Throwable exception) {
//                if (code != 200 || exception != null) {
//                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
//                            + (exception != null ? exception.getMessage() : "null"));
//                    if (callback != null) {
//                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
//                    }
//                    return;
//                }
//                try {
//                    JSONObject resObj = JSONObject.parseObject(response);
//                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
//                    if (resCode == RESULT_CODE_SUCCESS) {
//                        JSONObject retObj = resObj.getJSONObject(RESULT_KEY_DATA);
//                        RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
//                        roomInfoEntity.setRoomid(retObj.getInteger(RESULT_ROOMID));
//                        roomInfoEntity.setPushUrl(retObj.getString(RESULT_PUSH_URL));
//                        roomInfoEntity.setRtmpPullUrl(retObj.getString(RESULT_RTMP_URL));
//                        roomInfoEntity.setHlsPullUrl(retObj.getString(RESULT_HLS_URL));
//                        roomInfoEntity.setHttpPullUrl(retObj.getString(RESULT_HTTP_URL));
//                        roomInfoEntity.setOwner(retObj.getString(RESULT_OWNER));
//                        roomInfoEntity.setStatus(retObj.getInteger(RESULT_LIVE_STATUS));
//                        callback.onSuccess(roomInfoEntity);
//                    } else {
//                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
//                        callback.onFailed(resCode, error);
//                    }
//                } catch (JSONException e) {
//                    callback.onFailed(-1, e.getMessage());
//                } catch (Exception e){
//                    callback.onFailed(-1, e.getMessage());
//                }
//            }
//        });
//    }
//
//    /**
//     * 主播退出直播时,调用该接口,通知解散聊天室
//     */
//    public void anchorLeave(String roomId, final DemoServerHttpCallback<Void> callback){
//        String url = DemoServers.apiServer() + API_LEAVE_ROOM;
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
//        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);
//
//        StringBuilder body = new StringBuilder();
//        body.append(REQUEST_SID).append("=").append(DemoCache.getSid()).append("&").append(REQUEST_ROOM_ID)
//        .append("=").append(roomId);
//
//        NimHttpClient.getInstance().execute(url, headers, body.toString(), new NimHttpClient.NimHttpCallback() {
//            @Override
//            public void onResponse(String response, int code, Throwable exception) {
//                if (code != 200 || exception != null) {
//                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
//                            + (exception != null ? exception.getMessage() : "null"));
//                    callback.onFailed(code, exception.getMessage());
//                    return;
//                }
//                try {
//                    JSONObject resObj = JSONObject.parseObject(response);
//                    int resCode = resObj.getIntValue(RESULT_KEY_CODE);
//                    if (resCode == RESULT_CODE_SUCCESS) {
//                        callback.onSuccess(null);
//                    } else {
//                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
//                        callback.onFailed(resCode, error);
//                    }
//                } catch (JSONException e) {
//                } catch (Exception e){
//                }
//            }
//        });
//
//    }

    /**s
     * 添加上传的视频ID，客户端上传成功后须调用
     * 服务端返回成功,即通知点播服务器开始转码
     * @param vid 	视频ID
     * @param name  视频文件名
     * @param type  视频类型(0:普通点播视频, 1:短视频)
     * @param callback
     */
    public void addVideo(long vid, String name, int type, final DemoServerHttpCallback<AddVideoResponseEntity> callback){
        String url = DemoServers.apiServer() + API_VIDEO_ADD;
        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put(REQUEST_SID, DemoCache.getSid());
        body.put(VID, vid);
        body.put(FILE_NAME, name);
        body.put(TYPE, type);

        NimHttpClient.getInstance().execute(url, headers, getParamsString(body), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable e) {

                if(code!=200 || e!=null){
                    callback.onFailed(code, e!=null? e.getMessage() : "error code :" + code);
                    return;
                }

                try{
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    int resCode = jsonObject.getIntValue(RESULT_KEY_CODE);
                    if(resCode == 200) {
                        JSONObject retObj = jsonObject.getJSONObject(RESULT_KEY_DATA);
                        AddVideoResponseEntity entity = new AddVideoResponseEntity();
                        entity.setVideoCount(retObj.getInteger("videoCount"));
                        if (retObj.containsKey(RESULT_KEY_TRANSJOBSTATUS)) {
                            entity.setTransjobstatus(retObj.getBoolean(RESULT_KEY_TRANSJOBSTATUS));
                        }
                        JSONObject videoInfoJson = retObj.getJSONObject("videoinfo");
                        VideoInfoEntity videoInfoEntity = new VideoInfoEntity();
                        videoInfoEntity.setCompleteTime(videoInfoJson.getLongValue(RESULT_KEY_COMPLETE_TIME));
                        videoInfoEntity.setCreateTime(videoInfoJson.getLongValue(RESULT_KEY_CREATE_TIME));
                        videoInfoEntity.setOrigUrl(videoInfoJson.getString(RESULT_KEY_ORIG_URL));
                        videoInfoEntity.setSnapshotUrl(videoInfoJson.getString(RESULT_KEY_SNAPSHOT_URL));
                        videoInfoEntity.setUpdateTime(videoInfoJson.getLongValue(RESULT_KEY_UPDATE_TIME));
                        videoInfoEntity.setVid(videoInfoJson.getLongValue(VID));
                        videoInfoEntity.setDescription(videoInfoJson.getString(RESULT_KEY_DESCRIPTION));
                        videoInfoEntity.setTypeName(videoInfoJson.getString(RESULT_KEY_TYPE_NAME));
                        videoInfoEntity.setVideoName(videoInfoJson.getString(RESULT_KEY_VIDEO_NAME));
                        videoInfoEntity.setDuration(videoInfoJson.getLong(RESULT_KEY_DURATION));
                        videoInfoEntity.setInitialSize(videoInfoJson.getLong(RESULT_KEY_INITIAL_SIZE));
                        entity.setVideoInfoEntity(videoInfoEntity);
                        callback.onSuccess(entity);
                    }else{
                        callback.onFailed(resCode, jsonObject.getString(RESULT_KEY_ERROR_MSG));
                    }
                } catch (Exception exception){
                    callback.onFailed(-1, exception.getMessage());
                }
            }
        });
    }

    /**
     *
     * 删除已上传视频
     * 参数只有vid，则删除原视频及其转码视频，该视频所有信息均删除
     * 参数除vid还有format，则只删除单个转码视频
     * @param vid 视频ID
     * @param format 视频转码格式（1表示标清mp4，2表示高清mp4，3表示超清mp4，4表示标清flv，5表示高清flv，6表示超清flv，7表示标清hls，8表示高清hls，9表示超清hls）
     */
    public void videoDelete(long vid, @Nullable Integer format, final DemoServerHttpCallback<Void> callback){
        String url = DemoServers.apiServer() + API_VIDEO_DELETE;
        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put(REQUEST_SID, DemoCache.getSid());
        body.put(VID, vid);
        if(format!=null){
            body.put(VIDEO_FORMAT, format);
        }

        NimHttpClient.getInstance().execute(url, headers, getParamsString(body), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable e) {
                if(code!=200 || e!=null){
                    callback.onFailed(code, e!=null? e.getMessage() : "error code :" + code);
                    return;
                }

                try{
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    int resCode = jsonObject.getIntValue(RESULT_KEY_CODE);
                    if(resCode == 200 || resCode == 1644){
                        //{"code":1644,"msg":"视频不存在"} 多端登录删除同一视频的情况
                        callback.onSuccess(null);
                    }else{
                        callback.onFailed(resCode, jsonObject.getString(RESULT_KEY_ERROR_MSG));
                    }
                } catch (Exception exception){
                    callback.onFailed(-1, exception.getMessage());
                }

            }
        });
    }

    /**
     * 获取所有已上传视频的信息。
     * 如果有vid参数，则只返回该视频信息，否则返回该用户所有视频信息。
     * @param vid 	视频ID
     * @param type 视频类型(0:普通点播视频, 1:短视频)
     */
    public void videoInfoGet(@Nullable Long vid, int type, final DemoServerHttpCallback<List<VideoInfoEntity>> callback){
        String url = DemoServers.apiServer() + API_VIDEO_INFO_GET;
        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put(REQUEST_SID, DemoCache.getSid());
        if(vid != null){
            body.put(VID, vid);
        }
        body.put(TYPE, type);

        NimHttpClient.getInstance().execute(url, headers, getParamsString(body), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable e) {
                if(code!=200 || e!=null){
                    callback.onFailed(code, e!=null? e.getMessage() : "error code :" + code);
                    return;
                }
                try {
                    JSONObject jsonObj = JSONObject.parseObject(response);
                    int resCode = jsonObj.getIntValue(RESULT_KEY_CODE);
                    if(resCode== 200) {
                        JSONObject retObj = jsonObj.getJSONObject(RESULT_KEY_DATA);
                        List<VideoInfoEntity> list = JSON.parseArray(retObj.get("list").toString(), VideoInfoEntity.class);
                        callback.onSuccess(list);
                    }else{
                        callback.onFailed(resCode, jsonObj.getString(RESULT_KEY_ERROR_MSG));
                    }
                }catch (Exception e1){
                    callback.onFailed(-1, e1.getMessage());
                }
            }
        });
    }

    /**
     * 获取某上传视频的转码状态。
     * 客户端在上传成功后，调用应用服务端视频ID添加接口，客户端收到200返回码后则认为该视频已处于转码中。（服务端会发起转码请求）
     * 对于转码状态，客户端每隔1分钟向服务端发起该接口请求，获取状态。
     * @param vidList
     * @param callback
     */
    public void videoTransCodeStatus(List<Long> vidList, final DemoServerHttpCallback<List<TranscodeResponseEntity>> callback){
        String url = DemoServers.apiServer() + API_VIDEO_TRANS_STATE;
        Map<String, String> headers = new HashMap<>(1);
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_DEMO_ID, HEADER_DEMO_ID_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put(REQUEST_SID, DemoCache.getSid());
        body.put(VIDS, vidList);

        NimHttpClient.getInstance().execute(url, headers,getParamsString(body), new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable e) {
                if(code!=200 || e!=null){
                    callback.onFailed(code, e!=null? e.getMessage() : "error code :" + code);
                    return;
                }
                try {

                    JSONObject jsonObject = JSONObject.parseObject(response);
                    int resCode = jsonObject.getIntValue(RESULT_KEY_CODE);
                    if(resCode == 200) {
                        JSONObject retObj = jsonObject.getJSONObject(RESULT_KEY_DATA);
                        List<TranscodeResponseEntity> list = JSON.parseArray(retObj.get("list").toString(), TranscodeResponseEntity.class);
                        callback.onSuccess(list);
                    }else{
                        LogUtil.i(TAG, "video transcode error, resCode:" + resCode + ", error msg:"
                                + jsonObject.getString(RESULT_KEY_ERROR_MSG));
                        callback.onFailed(resCode, jsonObject.getString(RESULT_KEY_ERROR_MSG));
                    }
                }catch (Exception e1){
                    LogUtil.i(TAG, e1.toString());
                    callback.onFailed(-1, e1.getMessage());
                }
            }
        });
    }

    public static String getParamsString(Map<String, Object> maps){
        StringBuilder builder = new StringBuilder();

        boolean first = true;
        for (Map.Entry entry : maps.entrySet()) {
            if(first) {
                first = false;
            }else{
                builder.append("&");
            }

            builder.append(entry.getKey())
                   .append("=");
            if(entry.getValue() instanceof List) {
                boolean innerFirst = true;
                for (Object object: (List)entry.getValue()) {
                    if(innerFirst){
                        innerFirst = false;
                    }else{
                        builder.append(",");
                    }
                    builder.append(object);
                }
            }else{
                builder.append(entry.getValue());
            }
        }

        return builder.toString();
    }



    public static String readAppKey() {
//        try {
//            ApplicationInfo appInfo = DemoCache.getContext().getPackageManager()
//                    .getApplicationInfo(DemoCache.getContext().getPackageName(), PackageManager.GET_META_DATA);
//            if (appInfo != null) {
//                return appInfo.metaData.getString("com.netease.nim.appKey");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
        return "dc4285450493b9851169ad13a64b4cd8";
    }
}
