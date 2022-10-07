package com.example.demo.order;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 수정자 의존관계
 */
@Component
public class OrderServiceImpl2 implements OrderService {

    /** 필드주입 */
    @Autowired private MemberRepository memberRepository; // -> 객체 인스턴스를 생성하고, 참조값 을 전달해서 연결된다.
    @Autowired private DiscountPolicy fixDiscountPolicy; // -> 객체 인스턴스를 생성하고, 참조값 을 전달해서 연결된다.

    // 수정자 의존관계
    @Autowired(required = false)
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy fixDiscountPolicy) {
        this.fixDiscountPolicy = fixDiscountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = fixDiscountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
