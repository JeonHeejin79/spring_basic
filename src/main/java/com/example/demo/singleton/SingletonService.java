package com.example.demo.singleton;

/**
 * -> 싱글톤 패턴
 *  - 클래스와 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다.
 *  - 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야한다.
 */
public class SingletonService {

    // 클래스 래밸에 한개만 존재하게 된다.
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자를 생성해서 외부에서 new 생성을 막는다.
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
