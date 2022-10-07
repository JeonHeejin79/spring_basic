package com.example.demo.order;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 생성자 의존관계
 * 
 * 롬복
 */
@Component
@RequiredArgsConstructor // 생성자 자동 생성 - final 이붙은 필수값들의 필드로 생성자를 자동으로 만들어준다.
public class OrderServiceImpl4 implements OrderService {

    // final : 한번 정해지면 다른곳에서 변경 불가능
    //          -> 생성자에서만 값을 세팅할수 있도록 지정 가능 하도록 만드는 장점이 있다.
    //          -> 생성자에 초기화 누락시 컴파일 오류가 난다. 생성자 세팅 누락 방지 가능
    private final MemberRepository memberRepository;
    private final DiscountPolicy fixDiscountPolicy;

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
