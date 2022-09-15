package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements MemberRepository{

    // *** 동시성 이휴가 있을때 ConcurrentHashMap 사용
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
