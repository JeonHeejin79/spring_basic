package com.example.demo.beanscope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 빈스코프
 * 스프링이 지원하는 스코프
 * - 싱글톤 : 기본스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프
 *  동일한 memberService 반환       Spring Di Container
 *                            -------------------------
 *      clientA ------------> |   <scope: singleton>  |  1. 싱글통 스코프빈을 스프링 컨테이너에 요청
 *      clientB ------------> |     memberSerivce     |  2. 스프링 컨테이너는 본인이 관리하는 스프링 빈을 반환
 *      clientC ------------> |           x01         |  3. 이후에 스프링 컨테이너에 같은 요청이 와도 같은 객체의 인스턴스 스프링 빈을 반환
 *
 * - 프로토타입 : 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는
 *              관리하지 않는 매우 짧은 범위의 스코프
 *
 *       <scope:prorotype>       Spring Di Container
 *                            -----------------------
 *      clientA ------------> |  생성된 빈 반환, 관리 x |  1. 스프링 컨테이너는생성한 프로터 타입 빈을 클라이언트에 반환한다.
 *      clientB ------------> |  생성된 빈 반환, 관리 x |  2. 이후에 스프링 컨테이너에 같은 요청이 오면 항 상 새로운 프로토타입 빈을 생성해서 반환한다.
 *      clientC ------------> |  생성된 빈 반환, 관리 x |
 *   + PreDestroy 같은 종료 메소드가 호출되지 않는다.
 *
 * - 웹 권롼 스코프
 *   ㄴ request : 웹 요청이 들어오고 나갈떄 까지 유지되는 스코프
 *   ㄴ session : 웹 세선이 생성되고 종료될 떄 까지 유지되는 스코프
 *   ㄴ application : 웹의 서블릿 컨넥스트와 같은 범위로 유지되는 스코프
 */
public class beanScope {

    // 컴포넌트 스캔 자동 등록
    @Scope("prototype")
    @Component
    public class HelloBean{};


    // 수동등록
    @Scope("prototype")
    @Bean
    HelloBean HelloBean() {
        return new HelloBean();
    }


}
