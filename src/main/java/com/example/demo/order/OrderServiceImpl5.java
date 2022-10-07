package com.example.demo.order;

import com.example.demo.annotation.MainDiscountPolicy;
import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.DiscountPolicy2;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * + 여러개의 빈이 주입됏을떄 해결하는 방법
 * 조회 대상 빈이 2개 이상일떄 해결 방법
 * - @Autowired 필드명 매칭
 *    1. 타입매칭
 *    2. 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭
 * - @Qualifier("name") -> @Qualifier("name") 끼리 매칭 -> 빈 이름 매칭
 *
 * - @Primary 사용
 *
 */
@Component
public class OrderServiceImpl5 implements OrderService {

    /** 생성자 주입 */
    private final MemberRepository  memberRepository;
    private final DiscountPolicy2 discountPolicy;

    @Autowired // 생성자가 1개만 잇는경우 Autowired 생략 가능
    public OrderServiceImpl5(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy2 discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


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
