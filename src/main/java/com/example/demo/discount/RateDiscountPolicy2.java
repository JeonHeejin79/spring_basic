package com.example.demo.discount;

import com.example.demo.annotation.MainDiscountPolicy;
import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import org.springframework.stereotype.Component;

@MainDiscountPolicy
@Component
public class RateDiscountPolicy2 implements DiscountPolicy2 {

    private int discountPercent = 10;

    // ctr + sheft + t
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
