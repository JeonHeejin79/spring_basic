package com.example.demo.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient2 {

    private String url;

    public NetworkClient2() {
        System.out.println("NetworkClient2 생성자 호출, url = " + url);

        // connect();
        // call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("NetworkClient2.connect = " + url);
    }

    public void call(String message) {
        System.out.println("NetworkClient2.call: " + url + "message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("NetworkClient2.disconnect: " + url);
    }

    // 초기화
    public void init() throws Exception {
        System.out.println("NetworkClient2.init");
        connect();
        call("초기화 연결 메세지");
    }

    public void close() throws Exception {
        System.out.println("NetworkClient2.close");
        disconnect();
    }
}
