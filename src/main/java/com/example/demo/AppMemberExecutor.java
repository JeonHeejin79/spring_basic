package com.example.demo;

import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import com.example.demo.member.MemberServiceImpl;

/**
 * -> 회원 도메인 실행과 테스트
 */
public class AppMemberExecutor {

    public static void main(String[] args) {
        /**
        MemberService memberService = new MemberServiceImpl();

        Member member = new Member(1l, "memberA", Grade.VIP);

        Member findMember = memberService.findMember(1l);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
        */

        // App Config 를 통한 개발
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());



    }

}
