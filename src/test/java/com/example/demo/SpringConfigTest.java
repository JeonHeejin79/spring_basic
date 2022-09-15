package com.example.demo;

import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberServiceImpl;
import com.example.demo.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SpringConfigTest {

    private MemberRepository memberRepository;

    @Test
    public void getMemberServiceImplTest() {
        // return memberRepository;
    }

    /** Configuration Singleton Test */
    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);


        /**
         * memberService -> memberRepository1 = com.example.demo.member.MemoryMemberRepository@4671115f
         * orderService -> memberRepository2 = com.example.demo.member.MemoryMemberRepository@4671115f
         * memberRepository = com.example.demo.member.MemoryMemberRepository@4671115f
         * */

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    }

    /**
     * 순수한 클래스라면 'class hello.core.SpringConfig' 같이 출력된다.
     * 스프링이 xxxCGLIB 라는 바이트코드 조작 라이브러리를 사용해서 SpringConfig 클래스를 상속받은
     * 임의의 다른 클래스를 만들고 그 클래스를 스프링 빈으로 등록한 것이다.
     *
     * SpringConfig
     *     ^
     * SpringConfig@CGLIB (instance)
     *
     * 임의의 다른 클래스가 싱글톤이 보장 되도록 해준다.
     * @Bean 이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반호나하고
     * 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적을 만들어진다.
     */
    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        SpringConfig bean = ac.getBean(SpringConfig.class);

        System.out.println("bean = " + bean.getClass());

        /* bean = class com.example.demo.SpringConfig$$EnhancerBySpringCGLIB$$b9ce04be*/
    }

}