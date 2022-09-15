package com.example.demo;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import com.example.demo.member.MemberServiceImpl;
import com.example.demo.member.MemoryMemberRepository;
import com.example.demo.order.OrderService;
import com.example.demo.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링 컨테이너와 스프링 빈
 * -> 1. 스프링 컨테이너 생성
 */
/**
 * -> 스프링으로 전환하기
 * @Configuration : 설정정보
 * @Bean : 스프링 컨테이너에 등록된다.
 *
 * 스프링 컨테이너
 * - ApplicationContext : 스프링컨테이너
 * - 기존에는 개발자가 AppConfig 를 사용해서 직접 객체를 생성하고, DI 했지만
 * - 스프링 어노테이션 설정시 스프링 컨테이너를 통해서 사용한다.
 * - 스프링 컨테이너는 @Configuration 이 붙은 StringConfig 를 설정(구성) 정보로 사용한다.
 * - 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
 * - 이렇게 등록된 객체를 스프링 빈 이라고 한다.
 * - 스프링 빈은 @Bean 이 붙은 명을 스프링 빈의 이름으로 사용한다.
 * - 기존에는 개발자가 직접 자바코드로 모든것을 했다면 이제부터는 스프링 컨테이너에 객체를
 * 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경됐다.
 *  @Bean
 *  @Bean(name = "mmm") : 이름지정시
 */
@Configuration
public class SpringConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()
    @Bean
    public MemberService memberService() {
        System.out.println("SpringConfig.memberService");
        return new MemberServiceImpl(memberRepository()); // MemoryMemberRepository 를 사용
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("SpringConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("SpringConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy(); // RateDiscountPolicy / FixDiscountPolicy
        return new FixDiscountPolicy();
    }
}
