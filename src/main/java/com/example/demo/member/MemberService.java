package com.example.demo.member;

// 회원가입
// 회원조회
public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
