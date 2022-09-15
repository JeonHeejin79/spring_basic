package com.example.demo.singleton;

import com.example.demo.SpringConfig;
import com.example.demo.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * -> 웹 애플리에케이션과 싱글톤
 * - 스프링 컨테이너 덕분에 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라,
 * 이미 만들어진 객체를 공유해서 효율적으로 재사용 사용할 수 있다.
 *
 * (참고 : 스프링의 기본 빈 등록 방식은 싱글톤 이지만, 싱글통 방식만 지원하는 것은 아니다.
 * 요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공한다. rel 빈 스코프 )
 *
 *                 동일한 meberService 반환
 * Client A ----------->  Spring DI <----------- Client B
 * */
public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContaioner() {
        SpringConfig springConfig = new SpringConfig();
        
        // 1. 조회 : 호출할 떄 마다 객체를 생성
        MemberService memberService1 = springConfig.memberService();

        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = springConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " +  memberService2);

        // memberService 1!= memberService2 -> 호출될떄마다 객체를 계속 생성한다.
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

    }


}
