package com.example.demo.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient3 {

    private String url;

    public NetworkClient3() {
        System.out.println("NetworkClient3 생성자 호출, url = " + url);

        // connect();
        // call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("NetworkClient3.connect = " + url);
    }

    public void call(String message) {
        System.out.println("NetworkClient3.call: " + url + "message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("NetworkClient3.disconnect: " + url);
    }

    // 초기화
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient3.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient3.close");
        disconnect();
    }
}
