package com.example.jdbc.member;

import com.example.jdbc.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long createMember(MemberRequest request) {
        Long teamId = teamRepository.save(request.getTeamName());
        return memberRepository.save(request, teamId);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member not found"));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
