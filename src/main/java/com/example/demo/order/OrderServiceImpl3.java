package com.example.demo.order;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  * 생성자 주입을 선택해라
 *  - 과거 : 수정자 주입과 필드 주입을 많이 사용
 *  - 최근 : 스프링을 포함한 DI 프레임워크 대부분이 생성자 주입을 권장한다.
 *  - 이유
 *   "불변"
 *    ㄴ 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료 시점까지 의존관계를 변경할 일이 없다.
 *       오히려 대부분의 의존관계는 애플리케이션 종료 전까지 변하면 안된다.(불변해야 한다.)
 *    ㄴ 수정자 주입을 사용하면, setXxx 에서드를 public 으로 열어 두어야 한다.
 *    ㄴ 누군가 실수로 변경할 수 도 있고, 변경하면 안되는 메서드를 열어두는 것은 좋은 설꼐 방법이 아니다.
 *    ㄴ 생성자 중비은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 불변하게 설계할 수 있다.
 *
 *   "누락"
 *   프레임워크 없이 순수 자바 코드로 단위 테스흐나는 경우에
 *   의존관계의 누락을 방지할 수 있다.
 *
 *  - 정리
 *   ㄴ 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법 이기도 하다
 *   ㄴ 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
 *      생성자 중비과 수장자 주입을 동시에 사용할 수 있다.
 *   ㄴ 항상 생성자 주입을 선택하라, 그리고 가끔 옵션이 필요하면 수정자 주입을 선택하라, 필드 주입은
 *      사용하지 않는게 좋다.
 *
 * 생성자 의존관계
 */
@Component
public class OrderServiceImpl3 implements OrderService {

    // final : 한번 정해지면 다른곳에서 변경 불가능
    //          -> 생성자에서만 값을 세팅할수 있도록 지정 가능 하도록 만드는 장점이 있다.
    //          -> 생성자에 초기화 누락시 컴파일 오류가 난다. 생성자 세팅 누락 방지 가능
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl3(MemberRepository memberRepository, DiscountPolicy fixDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = fixDiscountPolicy;
    }

//    public init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

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
