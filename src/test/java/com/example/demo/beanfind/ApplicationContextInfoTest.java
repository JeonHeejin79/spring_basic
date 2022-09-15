package com.example.demo.beanfind;

import com.example.demo.SpringConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 스프링 컨테이너와 스프링 빈
 * -> 2. 컨테이너에 등록된 모든 빈 조회
 */
public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);

    /**
     * junit 5 부터는 public 설정을 안해도 된다.
     * @Test
     * public void findAllBean() {}
     * v
     * @Test
     * void findAllBean() {}
     */
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        // getBeanDefinitionNames : 스프링에 등록된 모든 빈 이름을 조회한다.
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            // getBean : 빈 이름으로 빈 객체(인스턴스)를 조회한다.
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("bean = " + bean);
            System.out.println("ApplicationContextInfoTest.findAllBean");
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // 빈하나에대한 메타데이터 정보

            // BeanDefinition
            //  - ROLE_APPLICATION 개발하기 위에서 등록한 빈들
            //  - ROLE_INFRASTRUCTURE 스프링 내부에서 사용하기 위한 빈들
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                // 스프링 내부에서 사용하는 빈은 getRole() 로 구분할 수 있다.
                Object bean = ac.getBean(beanDefinitionName);

                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }


}
