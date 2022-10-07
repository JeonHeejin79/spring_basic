package com.example.demo.autowired;

import com.example.demo.AutoAppConfig;
import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.member.*;
import com.example.demo.order.Order;
import com.example.demo.order.OrderServiceImpl;
import com.example.demo.order.OrderServiceImpl2;
import com.example.demo.order.OrderServiceImpl3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * + 여러개의 빈이 주입됏을떄 해결하는 방법
 * 조회 대상 빈이 2개 이상일떄 해결 방법
 * - @Autowired 필드명 매칭
 *    1. 타입매칭
 *    2. 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭
 * - @Qualifier("name") -> @Qualifier("name") 끼리 매칭 -> 빈 이름 매칭
 *    1. 추가 구분자를 붙여주는 방법이다. 빈이름을 변경하는 것은 아니다.
 *    2. @Qualifier 끼리 매칭, 빈이름 매칭
 *    3. NoSuchBeanDefinitionException 발생
 * - @Primary 사용
 *    1. 우선순위를 지정하는 방법이다.
 *
 * - 활용 ex) 메인 커넥션을 획득하는 스프링빈은  @Primary 지정하고 서브 DB 커넥션 획득은 @Qualifier
 * - 우선순위 : @Qualifier 가 우선순위가 더 높음
 */

/**
 * 애뇨테이션 직접 만들기
 * @MainDiscountPolicy
 *  - 애노테이션에는 상속이라는 개념이 없다.
 *  - 여러 애노테이션을 모아서 사용하는 기능은 스프링이 지원해주는 기능이다.
 *  - 애노태이션들을 함께 조합해서 사용할 수 있다.
 *  - 뚜렷한 목적 없이 무분별하게 재정의 하는 것은 유지보수에 혼란을 가중할 수 있다.
 *  - 너무 무분별하게 사용하는것은 금하자
 */
public class AutowiredTest3 {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
}
