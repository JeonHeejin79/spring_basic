package com.example.demo;

import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 스프링 컨테이너 생성
 * ex)
 *  ApplicationContext
 * - ApplicationContext를 스프링 컨테이너라 한다.
 * - ApplicationContext 는 인터페이스이다.
 * - 설정방법
 *     ㄴ 스프링 컨테이너는 XML 기반으로 만들 수 있고
 *     ㄴ 애노테이션 기반의 자바 설정 클래스로 만들 수 있다.
 * - SpringConfig 를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만든 것이다.
 *
 * (+ 더 정확히는 스프링 컨테이너를 부를때 BeanFactory, ApplicationContext 를 구분해서 이야기한다.
 *  BeanFactory 를 직접 사용하는 경우는 거의 없으므로 일밙거으로 ApplicationContext 를 스프링
 *  컨테이너라 한다.)
 *
 *  스프링 컨테이너 생성과정
 *  1. 스프링 컨테이너 생성 (SpringConfig.class)
 *         v
 *    스프링 컨테이너 (스프링 빈저장소 [빈이름 | 빈객체 ] ) ) : 빈이름 은 기본 메소드명, 빈이름은 중복불가
 *         v
 *  2. 구성정보 활용
 *         v
 *    AppConfig.class
 *         v
 *  3. 스프링 빈 의존관계 설정 - 준비 -> 객체할당      memberService, memberRepository, orderService, discountPolicy
 *  4. 스프링 빈 의존관계 설정 - 완료 -> 의존관계 주입  memberService -> memberRepository <- orderService -> discountPolicy
 *  (+ 스프링은 빈을 생성하고 의존관계를 주입하는 단계가 나누어져 있다.)
 */
public class SpringMemberExecutor {

    public static void main(String[] args) {
        // ApplicationContext : 스프링은 여기에 객체생성을 해서 관리한다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        // 기존에는 AppConfig appConfig = new AppConfig(); 에서 직접 찾아왔으나
        // 스프링 컨테이너에서 직접 찾아올 수 잇다.
        // @Bean 으로 등록시 기봄적으로 메서드 이름을 등록된다.
        // -> 메서드 이름으로 빈객체를 찾아올 수 있다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
