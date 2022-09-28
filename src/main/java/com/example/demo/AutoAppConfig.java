package com.example.demo;

import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    // basePackages = "com.example.demo.member", // 탐색할 패키지의 노드 지정
    // basePackageClasses = AutoAppConfig.class, // 탐색할 패키지 노드 클래스 지정
    // + 지정하지 않는경우 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
    // + 스프링 부트인경우 @SpringBootApplication 를 프로젝트 시작위치에 두는 관례인 이유도 같다
    // (@SpringBootApplication 설정안에 @ComponentScan 존재)
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 컴포넌트 스캔 기본 대상
    // @Component : 컴포넌트 스캔에서 사용
    // @Controller : 스프링 MVC 컨트롤러에서 사용,
    //               스프링 MVC 컨트롤러로 인식하고 컨트롤러 메카니즘을 동작할수 있도록 도와준다.
    // @Service : 스프링 비즈니스 로직에서 사용, 개발자들이 비즈니스 로직이 있다는것을 인지하도록 도와줌
    // @Repository : 스프링 데이터 접근 계층에서 사용, 데이터 계층의 예외를 스프링 예외로 변환해준다.
    //                EX) DB 변경시 예외가 해당 DB 에 맞쳐 올라와보이면 서비스 계층의 예외 코드가 달라지는것을 방지
    // @Configuration : 스프링 설정 정보에서 사용, 스프링 빈이 싱글톤을 유지하도록 추가 처리

    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
