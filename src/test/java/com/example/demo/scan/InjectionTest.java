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
import org.springframework.beans.factory.annotation.Qualifier;
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
 *
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
 * */
public class InjectionTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
    
    @Test
    void fileIndectionTest() {
/*        OrderServiceImpl2 orderService = new OrderServiceImpl2();
        orderService.setMemberRepository(new MemoryMemberRepository());
        orderService.setDiscountPolicy(new FixDiscountPolicy());

        orderService.createOrder(1L, "itemA", 10000);*/
    }
}
