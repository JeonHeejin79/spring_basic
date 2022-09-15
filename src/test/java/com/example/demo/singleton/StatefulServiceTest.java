package com.example.demo.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 싱글톤 방식의 주의점
 * 
 * 스프링 빈은 항상 무상태 (stateless) 상태로 설계해야한다.
 * 상태유지 (stateful) 하게 설계하면 안된다.
 *
 */
class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        /**
       // ThreadA : A 사용자 10000원 주문
        statefulService1.order("userA", 10000);
        // ThreadB : B 사용자 20000원 주문
        statefulService1.order("userB", 20000);

        // ThreadA : 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

         Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
         */

        // ThreadA : A 사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : B 사용자 20000원 주문
        int userBPrice = statefulService1.order("userB", 20000);

        System.out.println("userAPrice = " + userAPrice);
        System.out.println("userBPrice = " + userBPrice);

    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}