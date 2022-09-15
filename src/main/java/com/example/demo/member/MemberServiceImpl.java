package com.example.demo.member;

/**
 * -> 회원 도메인 설계
 * -> 회원 도메인 개발
 **/
//* 회원 도메인 설계의 문제점
//  - 이  코드의 설계상 문제점
//  - 다른 저장소로 변경할 떄 OCP 원칙을 잘 준수하는가 ?
//  - DIP 를 잘 지키고 있는가 ? (Dipendency Inversion Principle)
//  의존 관계가 인터페이스 뿐만 아니라구현까지 모두 의존하는 문제점이 있음
//   -> 주문까지 만들고 나서 문제점과 해결 방안을 설명
//
//   MemberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
//   DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.

public class MemberServiceImpl implements MemberService{

    // Ctrl + Shift + Enter : 자동완성 + 세미콜론
    // 실제 할당하는 부분이 구현체를 의존한다.
    // MemberServiceImpl 은 memberRepository 도의존하고 MemoryMemberRepository 도 의존한다.
    // 추상화에도 의존하고 구체화에도 의존한다. 나중에 변경할떄 문제가된다.
    // DIP를 위반하고 있다.
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    // 생성자를 통해서 검색 -> 추상화에만 의존하게된다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
