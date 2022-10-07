package com.example.demo.discount;

import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// @Qualifier("mainDiscountPolicy")
@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {

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
