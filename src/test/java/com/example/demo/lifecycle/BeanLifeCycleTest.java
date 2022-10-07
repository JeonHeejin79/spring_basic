package com.example.demo.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. 빈 생명주기 콜백
 * 스프링 빈 라이프사이클 : 객체생성 -> 의존관계주입
 *
 * 스프링 빈의 이벤트 라이프 사이클
 * [스프링 컨테이너생성 -> 스프링 빈 생성 -> 의존관계주입 ->
 * 초기화 콜백 -> 사용 -> 소멸전콜백 -> 스프링종료]
 *
 * 초기화콜백 : 빈이 생성되고, 빈이 의존관계 주입이 완료된 후 호출
 * 소멸전콜백 : 빈이 소멸되기 직전에 호출
 *
 * + 객체의 생성과 초기화를 분리하자
 * 생성자는 필수정보를 파라미터를 받고, 메로리르 할당해서 객체를 생성하는 책임을 가진다.
 * 초기화는 이렇게 생성된 값을 활용해서 커넥션을 연결하는 무거운 종작을 수행한다.
 * -> 분리하는게 유지보수 관저에서 좋다.
 *
 * 스프이 지원하는 빈 생명주기 콜백 3가지
 *  - 인터페이스 (InitializingBean, DisposableBean)
 *  - 설정 정보에 초기화 메서드, 종료 메서드 지정
 *  - @PostConstruct, @PreDestroy 지원
 *
 * 2. 인터페이스 Initializing, DisposableBean
 *   ㄴ 초기화, 소멸 인터페이스 단점
 *     - 이 인터페이스는 스프링 전용 인터페이스이다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
 *     - 초기화, 소멸 메서드의 이름을 변경할 수 없다.
 *     - 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
 *
 * 3, 빈 등록 초기화, 소멸 메서드
 *   ㄴ 설정 정보에 @Bean(initMethod = "init", destroyMethod = "close")
 *     - 메서드 이름을 자유롭게 지정할 수 있다.
 *     - 스프링 빈이 스프링 코드에 의존하지 않는다.
 *     - 코드가 아니라 설정 정보를 사용하기 떄문에 코드를 고칠 수 없는
 *        외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
 *   ㄴ 종료 메소드의 추론
 *      - @Bean 의 destroyMethod 에는 특별한 기능이 있따.
 *      - 라이브러리는 대부분 close, shutdown 이라는 이름의 종료 메서드를 사용한다.
 *      - @Bean 의 destroyMethod 는 기본값이 (inferred) (추론) 으로 등록되어 있다.
 *      - 이 추론 기능은 close, shutdown 라는 이름의 메서드를 자동으로 호출해준다.
 *
 * */
public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Test
    public void lifeCycleTest2() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        NetworkClient2 client = ac.getBean(NetworkClient2.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient2 networkClient2() {
            NetworkClient2 networkClient2 = new NetworkClient2();
            networkClient2.setUrl("http://hello-spring.dev");
            return networkClient2;
        }
    }
}
