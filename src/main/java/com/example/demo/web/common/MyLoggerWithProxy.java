package com.example.demo.web.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * - 동작
 *  ㄴ CGLIB 라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 *  ㄴ 이 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직이 들어있ㄷ.
 *  ㄴ 가짜 프록시 객체는 실제 request scope 와는 관계 없다. 그냥 가짜이고, 내부에 단순한 위임 로직만 있고.
 *    싱글톤처럼 동작한다.
 *    가짜 프록시 객체는 요청이 오면 그떄 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.
 *  - 특징 정리
 *   ㄴ 프록시 객체 덕분에 클라이언트는 마치 싱글톤 빈을 사용하듯이 편리하게 request scope 를 사용할 수 있다.
 *   ㄴ 사실 Provider를 사용하든, 프록시를 사용하든 핵심 아이디어는 진짜 개체 조회를
 *      꼭 필요한 시점까지 지연처리 한다는 점이다.
 *   ㄴ 단지 애노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대체할 수 있다.
 *      이것이 바로 다형성과 DI 컨테이너가 가진 큰 강점이다.
 *   ㄴ 꼭 웹 스코프가 아니여도 프록시는 사용할 수 있다.
 * */
@Component
@Scope(value = "requet", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLoggerWithProxy {
    private String uuid;
    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]"+"[" + requestUrl + "]" + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}
