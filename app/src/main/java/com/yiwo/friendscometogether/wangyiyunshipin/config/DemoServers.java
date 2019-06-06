package com.yiwo.friendscometogether.wangyiyunshipin.config;

/**
 * Demo应用服务器地址（第三方APP请不要使用）
 */
public class DemoServers {

    private static final String API_SERVER_TEST = "http://223.252.220.238:8080/appdemo"; // 测试
    private static final String API_SERVER = "https://app.netease.im/appdemo"; // 线上

    public static final String apiServer() {
        return ServerConfig.testServer() ? API_SERVER_TEST : API_SERVER;
    }
}
