package com.example.demo.autowired;

import com.example.demo.AutoAppConfig;
import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 조회한 빈이 모두 필요할 때, List, Map
 * -> 해당 타입의 스프링 빈이 다 필요한경우
 * -> 스프링 컨테이너는 생성자에 클래스 정보를 받는다.
 *    생성자에 클래스 정보를 넘기면 해당 클래스가 스프링 빈으로 자동 등록된다.
 *
 * 자동, 수동의 올바른 실무 운영 기준
 * -> 애플래케이션의 로직 구분
 *    ㄴ 업무 로직 / 빈 : 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는
 *                      리포지토리등이 모두 업무 로직이다. 보통 비즈니스 요구사항을 개발할 때 추가되거나 변경된다
 *                      분량이 많고, 유사한 패턴이 있다.
 *                      문제 발생해도 어떤 곳에서 문제가 발생했는지 명확하게 파악하기 쉽다.
 *                      "자동 빈" 등록으로 자동 기능을 적극 활요하는 것이 좋다.
 *    ㄴ 기술 기원 : 기술적인 문제나 공통 관심사(AOP)를 처리할 때 주로 사용된다. 데이터베이스 연결이나,
 *                  공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들이다.
 *                  분량이 적고, 애플리케이션 전만에 걸쳐서 광범위하게 영향을 미친다.
 *                  문제 발생시 어디인지 팡가하기 어려운 점이 있다.
 *                  "수동 빈" 등록을 사용해서 "설정 정보"에 바로 나타나게 하여 명확히 드러내는 것이 좋다.
 *  -> 비즈니스 로직 중에서 다형성을 적극 활용할떄
 *    ㄴ 수동 빈으로 등록하거나 / 자동으로 하면 특정 패키지에 같이 묶어 두는 것이 좋다.
 * */
public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        /*
        policyMap = {fixDiscountPolicy=com.example.demo.discount.FixDiscountPolicy@b2c5e07, rateDiscountPolicy=com.example.demo.discount.RateDiscountPolicy@5812f68b}
        policyList = [com.example.demo.discount.FixDiscountPolicy@b2c5e07, com.example.demo.discount.RateDiscountPolicy@5812f68b]
        */
        DiscountService discountService = ac.getBean(DiscountService.class);

        Member member = new Member(1L, "userA", Grade.VIP);

        int discountPrice = discountService.discount(member, 1000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPolicy = discountService.discount(member, 20000, "rateDiscountPolicy");

        assertThat(rateDiscountPolicy).isEqualTo(2000);
    }

    // 동적으로 빈을 선택해야될 때 map 으로 받아서 사용할 수 있다.
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap; // map 으로 모든 discountPlicy 를 주입받는다.
        private final List<DiscountPolicy> policyList;

        @Autowired
        DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }

    /**
    @Configuration
    public class DiscountPolicyConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
     */

}
