package com.example.demo.beanscope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

/***
 * 프로토타입 스코프
 * 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
 */
public class SingletonsWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonPrototypeBean.class);

        SingletonPrototypeBean prototypeBean1 = ac.getBean(SingletonPrototypeBean.class);
        prototypeBean1.addCount();

        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        SingletonPrototypeBean prototypeBean2 = ac.getBean(SingletonPrototypeBean.class);
        prototypeBean2.addCount();

        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(SingletonPrototypeBean.class, Clientbean.class);

        Clientbean clientBean1 = ac.getBean(Clientbean.class);

        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        Clientbean clientBean2 = ac.getBean(Clientbean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    @Test
    void singletonClientUsePrototype2() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(SingletonPrototypeBean.class, Clientbean2.class);

        Clientbean2 clientBean3 = ac.getBean(Clientbean2.class);

        int count3 = clientBean3.logic();
        assertThat(count3).isEqualTo(1);

        Clientbean2 clientBean4 = ac.getBean(Clientbean2.class);
        int count4 = clientBean4.logic();
        assertThat(count4).isEqualTo(1);
    }

    @Scope("singleton")
    static class Clientbean {
        // 생성시점에 주입 x01
        private final SingletonPrototypeBean singletonPrototypeBean; //생성시점에 주입 되어버린다.

        @Autowired
        public Clientbean(SingletonPrototypeBean singletonPrototypeBean) {
            this.singletonPrototypeBean = singletonPrototypeBean;
        }

        public int logic() {
            singletonPrototypeBean.addCount();
            return singletonPrototypeBean.getCount();
        }
    }

    @Scope("singleton")
    static class Clientbean2 {

        @Autowired
        ApplicationContext applicationContext;

        public int logic() {
            SingletonPrototypeBean singletonPrototypeBean = applicationContext.getBean(SingletonPrototypeBean.class);
            singletonPrototypeBean.addCount();
            return singletonPrototypeBean.getCount();
        }
    }



    @Scope("prototype")
    static class SingletonPrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
