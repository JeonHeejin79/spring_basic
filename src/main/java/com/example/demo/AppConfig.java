package com.example.demo;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import com.example.demo.member.MemberServiceImpl;
import com.example.demo.member.MemoryMemberRepository;
import com.example.demo.order.Order;
import com.example.demo.order.OrderService;
import com.example.demo.order.OrderServiceImpl;
import org.springframework.context.annotation.Configuration;

/**
 * -> 관심사의분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
 */
// * AppConfig
// - 애플리케이션의 실제 동작에 필요한 "구현 객체를 생성" 한다.
// - 생성한 객체 인스턴스의 참조(레퍼런스) 를 "생성자를 통해서 주입(연결)" 해준다.
// - 객체의 생성과 연결은 AppConfig 가 담당한다.
// - "DIP" 의 완성 MemberServiceImpl 은 MemberRepository 인 추상에만 의존하면 된다.  이제 구체 클래스를 몰라도 된다.
/**
 * -> 제어의 역전 IoC (Inversion of Control)
 */
// 기존 : 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성, 연결, 실행 했다.
// 개선 : AppConfig 가 제어의 흐름을 가져간다.  이제 'OrderServiceImpl' 은 필요한 인터페이스들을
//       호출하지만 어떤 구현 객체들이 실행 될지 모른다.
// 이렇게 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC) 이라 한다.
/**
 * -> 의존 관계 주입 DI (Dependency Injection)
 */
// OrderServiceImpl 은 DiscountPolicy 인터페이스에 의존한다. 실제 어떤 구현 객체가 사용될지는 모른다.
// 의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 한다.
// - 정적인 클래스 의존 관계 : 클래스가 사용하는 import 코드만 모고 의존관계를 쉽게 판단할 수 있다.
//                         애플리케이션을 실행하지 않아도 분석할 수 있다.
//                         OrderServiceImpl 은 MemberRepository, DiscountPolicy 에 의존한다.
// - 동적인 객체 인스턴스 의존관계 : 애플리케이션 실행 시점에 객체 인스턴스의 참조가 연결된 의존 관계다.
public class AppConfig {

    // MemberServiceImpl 안에 직접  private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 하는것을 AppConfig 에서 하도록 한다.
    // 생성자 주입 : 생성자를 통해 객체가 들어감
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); // MemoryMemberRepository 를 사용
    }

    /** -> AppConfig 리펙토링 */
    // new MemoryMemberRepository() 이 부분이 중복 제거 되었다.
    // 이제 MemoryMemberRepository 를 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
    // AppConfig 를 보면 역할과 구현 클래스가 한눈에 들어온다.
    // 애플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있다.
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /** -> 새로운 구조와 할인정책 적용 */
    // - AppConfig 에서 할인 정책 역할을 담당하는 구현을 'FixDiscountPolicy -> RateDiscountPolicy'
    // 객체로 변경했다.
    // - 이제 할인 정책을 변경해도, 애플리케이션의 구성 역할을 담당하는 AppConfig 만 변경하면 된다.
    // 클라이언트 코드인 'OrderServiceImpl' 를 포함해서 사용 영역의 어떤 코드도 변경할 필요가 없다.
    // - 구성 영역은 당연히 변경된다. 구성 역할을 담당하는 AppConfig를 애플리케이션이라는
    // 공연의 기획자로 생각하자, 공역 기획자는 공연 참여자인 구현 객체들을 모두 알아야 한다.
    // - 사용영역에 있는 코드를 전혀 손댈 필요가 없다.
    // : 클라이언트 코드를 전혀 변경하지 않고 애플리케이션의 기능 확장이 가능해 진다.
    //    -> OrderServiceImpl, OrderServiceImpl
    // : 구성영역의 코드만 수정해서 개발이 가능해진다.
    //    -> AppConfig
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy(); // RateDiscountPolicy / FixDiscountPolicy
        return new FixDiscountPolicy();
    }
}
