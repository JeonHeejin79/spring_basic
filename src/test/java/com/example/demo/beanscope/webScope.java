package com.example.demo.beanscope;


/**
 *웹스코프
 * - 웹스코프는 웹 환경에서만 동작한다.
 * - 웹 스코프는 프로토타입과 다르게 스프링이 해당 스코프의 종료시점까지 고나린한다.
 *  따라서 종료 메서드가 호출된다.
 *
 * - 웹스코프의 종류
 *   request : HTTP 요청 하나가 들어오고 날때 까지 유지되는 스코프
 *             HTTP 요청마다 별도의 빈 인스턴스가 생성되고 관리
 *   session : HTTP Session 과 동일한 생명주기를 가지는 스코프
 *   application : Servlet Context 와 동일한 생명주기를 가지는 스코프
 *   websocket : 웹 소켓과 동일한 생명주기를 가지는 스코프
 *
 *   spring-boot-starter-web 라이브러리 추가
 *   내장톰캣 서버를 활용해서서 웹서버와 스프링 실행
 **/
public class webScope {

    /** request 스코프 예제
     * - request 에대한 로그 구분
     * - UUID
     * - UUID 를 사용해서 구분
     * */

    /** request scope 를 사용하지 않고 파라미터를 이 모든 정보를
     * 서비스 계층에 넘기면 파라미터가 많아지고 지저분해진다.
     * ㄴ request 관련 웹 과 관련된 정보는 컨트롤러에서 관리
     * ㄴ 나머지 biz 로지만 서비스에서 관리하는게
     * 유지보수 관점에서 좋음
     * */
}
