package com.example.demo.beanscope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

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


    /** ObjectFactory, ObjectProvider
     * - 지정한 빈을 컨테이너 대신 찾아주는 DL(Dependency Lookup)서비스를 제공한다.
     * - 과거 ObjectFactory -> 편의기능 추가 ObjectProvider
     * - 특징 :
     *    ㄴ ObjectFactory : 기능이 단순, 별도의 라이브러리 필요 없음 ,스프링 의존
     *    ㄴ ObjectProvider : ObjectFactory 상속, 옵션, 스트림 처리 등 편의 기능이 많고, 별도의 라이브러리 필요 없음, 스프링에 의존
     * */
    @Scope("singleton")
    static class Clientbean3 {

        @Autowired
        private ObjectProvider<SingletonPrototypeBean> singletonPrototypeBean;

        @Autowired
        private ObjectFactory<SingletonPrototypeBean> singletonPrototypeBean2;

        public int logic() {
            SingletonPrototypeBean singletonPrototypeBean = singletonPrototypeBean2.getObject();
            singletonPrototypeBean.addCount();
            return singletonPrototypeBean.getCount();
        }
    }

    @Test
    void singletonClientUsePrototype3() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(SingletonPrototypeBean.class, Clientbean3.class);

        Clientbean3 clientBean1 = ac.getBean(Clientbean3.class);

        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        Clientbean3 clientBean2 = ac.getBean(Clientbean3.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    /**
     * JSP-330 Provider
     * javax.inject.Provider 라는 JSP-330 자바 표준의 사용하는 방법
     * 이 방법을 사용하면 javax.inject:javax.inject:1 라이브러리를 gradle에 추가해야 한다.
     * - provider 의 get 을 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL : Dependency Lookup)
     * - 자바 표준이고, 기능이 단순하므로 단위테스트를 만들어간 mock 코드를 만들기는 훨씬 쉬워진다.
     * - Provider 는 지금 딱 필요한 DL 정도의 기능만 제공한다.
     * - 스프링이 아닌 다른 컨테이너에서 사용해야 할 일이 있다면 적절하다.
     * */
    @Scope("singleton")
    static class ClientBean4 {
        @Autowired
        private Provider<SingletonPrototypeBean> prototypeBeanProvider;

        public int logic() {
            SingletonPrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Test
    void jsrProviderTest() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(SingletonPrototypeBean.class, ClientBean4.class);

        ClientBean4 clientBean1 = ac.getBean(ClientBean4.class);

        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean4 clientBean2 = ac.getBean(ClientBean4.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }
    
    @Test
    public void stringTest() {
        String testVal = " ";

        System.out.println("testVal.length() = " + isEmpty(testVal));
        System.out.println("testVal.length() = " + isBlank(testVal));

        List<String> stirngList = new ArrayList<>();
        stirngList.add("113131");
        stirngList.add("123124");
        stirngList.add("141444");
        System.out.println("stirngList = " + stirngList);
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
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
