package com.example.demo.beanfind;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 스프링 컨테이너와 스프링 빈
 * -> 5. 스프링 빈 조회 - 상속관계
 * -> 6. BeanFactory 와 ApplicationContext
 *  [BeanFactory] <interface>
 *      ^
 *  [ApplicationContext] <interface>
 *      ^
 *  [AnnotationConfig ApplicationContext]
 *
 *   "BeanFactory"
 *   - 스프링 컨테이너의 최상위 인터페이스이다.
 *   - 스프링 빈을 고나리하고 조회하는 역할을 담당한다.
 *   - 'getBean()' 을 제공한다.
 *   - 지금까지 우리가 사용했던 대부분의 기능은 BeanFactory 가 제공하는 기능이다.
 *
 *   "ApplicationCotnext"
 *   - BeanFactory 기능을 모두 상속받아서 제공한다.
 *   - 빈을 관리하고 검색하는 기능을 BeanFactory 가 제공해주는데, 그러면 둘의 차이가 뭘가?
 *   - 애플리케이션을 갭라할 때는 빈은 고나리하고 조회하는 기능은 문론이고, 수 많은 부가기능이 필요하다.
 *
 *
 */

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 잇으면, 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate() {
        DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 빈 이름을 지정하면 된다.")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입을 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);

        assertThat(beansOfType.size()).isEqualTo(2);

        for (String key: beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("브모 타입을 모두 조회하기 - Object")
    void findAllBeansByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);

        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
