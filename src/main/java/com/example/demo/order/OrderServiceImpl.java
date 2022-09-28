package com.example.demo.order;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 1. SOLID란?
 * 객체지향 설계는 긴 세월과 수많은 시행착오를 거치며 5가지 원칙이 정리되었다. 이것은 객체지향 설계의 5원칙이라고 하며,
 * 앞글자를 따서 SOLID라고 한다.
 *
 * - SPR(Single Responsibility Principle) : 단일 책임 원칙
 * - OCP(Open Closed Principle) : 개방 폐쇄 원칙
 * - LSP(Liskov Substitution Principle) : 리스코프 치환 원칙
 * - ISP(Interface Segregation Principle) : 인터페이스 분리 원칙
 * - DIP(Dependency Inversion Principle) : 의존 역전 원칙
 */

/**
 *  -> 주문과 할인 도메인 설계
 *  -> 주문과 할일 도메인 계발
 */
@Component
public class OrderServiceImpl implements OrderService {

    /** 생성자 주입 */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired // 생성자가 1개만 잇는경우 Autowired 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 할인 정책을 변경하려면 클라이언트인 OrderServiceImpl 코드를 고쳐야 한다.
    // - 역할롸 구현을 충실하게 분리했다. (O)
    // - 다형성도 활용하고, 인터페이스와 구현 객체를 분리했다. (O)
    //      역할 : DiscountPolicy
    //      구현 : FixDiscountPolicy, RateDiscountPolicy
    //  - OCP, DIP 같은 객체지향 설계 원칙을 충실히 준수했다. (X) -> DIP 위반 (DIP : 구체에 의존하지말고 추상에 의존해라)
    //      주문서비스 클라이언트(OrderServiceImpl) 은
    //      추상 인터페이스(DiscuontPolicy) 에 의존하면서
    //      구현 클래스(FixDiscountPolicy, RateDiscountPolicy) 에도 의존하고 있다.
    //  - FixDiscountPolicy를 RateDiscountPolicy 로 변경하는 순간 OrderServiceImpl 의 소스코드도 함께 변경해야 한다. -> OCP 위반
    private final DiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    // 인터페이스에만 의지하도록 코드를 변경한다.
    // -> null point exception 이 발생한다.
    // > 해결방안 : 이문제를 해결하려면 누군가가 클라이언트인 OrderServiceImpl 에 DiscountPolicy 의 구현 객체를 대신 생성하고 주입해줘야 한다.
    //             ㄴ AppConfig : 애플리케이션의 전체 동작 방식을 구성(config) 하기 위해 "구현 객체를 생성" 하고 "연결" 하는 책임을 가지는 별도의 설정 클래스

    private DiscountPolicy discountPolicy2;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
