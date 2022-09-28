package com.example.demo.autowired;

import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import com.example.demo.member.MemoryMemberRepository;
import com.example.demo.order.Order;
import com.example.demo.order.OrderServiceImpl2;
import com.example.demo.order.OrderServiceImpl3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

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
 */
public class AutowiredTest2 {

    @Test
    void createOrder() {
        OrderServiceImpl2 orderServiceImpl2 = new OrderServiceImpl2();

        orderServiceImpl2.createOrder(1l, "itemA", 1000);
    }

    @Test
    void createOrder2() {
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1l, "name", Grade.VIP));

        OrderServiceImpl3 orderServiceImpl2 = new OrderServiceImpl3(memoryMemberRepository, new FixDiscountPolicy());

        Order itemA = orderServiceImpl2.createOrder(1l, "itemA", 1000);

        Assertions.assertThat(itemA.getDiscountPrice()).isEqualTo(1000);
    }

}
