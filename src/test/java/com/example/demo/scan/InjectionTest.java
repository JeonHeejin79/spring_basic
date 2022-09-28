package com.example.demo.scan;

import com.example.demo.AutoAppConfig;
import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import com.example.demo.member.MemoryMemberRepository;
import com.example.demo.order.OrderServiceImpl;
import com.example.demo.order.OrderServiceImpl2;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 다양한 의존관계 주입방법
 * - 생성자 주입 : 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다. "불변, 필수" 의존관계에 사용
 *      private final MemberRepository memberRepository;
 *      private final DiscountPolicy discountPolicy;
 *
 *     @Autowired // 생성자가 1개만 잇는경우 Autowired 생략 가능
 *     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
 *         this.memberRepository = memberRepository;
 *         this.discountPolicy = discountPolicy;
 *     }
 *
 * - 수정자 주입 (setter 주입) : "선택, 변경" 기능성이 있는 의존관계에서 사용
 *
 *      private MemberRepository memberRepository;
 *      private DiscountPolicy discountPolicy;
 *
 *     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
 *         this.memberRepository = memberRepository;
 *         this.discountPolicy = discountPolicy;
 *     }
 *
 *     @Autowired(required = false)
 *     public void setMemberRepository(MemberRepository memberRepository) {
 *         this.memberRepository = memberRepository;
 *     }
 *     public void setDiscountPolicy(DiscountPolicy discountPolicy) {
 *         this.discountPolicy = discountPolicy;
 *     }
 *
 * - 필드 주입
 *     @Autowired private MemberRepository memberRepository;
 *     @Autowired private DiscountPolicy discountPolicy;
 *
 *     @Autowired // 생성자가 1개만 잇는경우 Autowired 생략 가능
 *     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
 *         this.memberRepository = memberRepository;
 *         this.discountPolicy = discountPolicy;
 *     }
 * - 일반 메서드 주입
 * */
public class InjectionTest {

    @Test
    void basicScan() {
        /*
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
        */

    }
    
    @Test
    void fileIndectionTest() {
/*        OrderServiceImpl2 orderService = new OrderServiceImpl2();
        orderService.setMemberRepository(new MemoryMemberRepository());
        orderService.setDiscountPolicy(new FixDiscountPolicy());

        orderService.createOrder(1L, "itemA", 10000);*/
    }
}
