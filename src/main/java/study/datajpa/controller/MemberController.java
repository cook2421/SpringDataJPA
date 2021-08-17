package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }   // 도메인 클래스 컨버터 (권장하지 않음)
    // 조회용으로 쓰는 것을 권장

    @GetMapping("/members")
    public Page<MemberDTO> list(@PageableDefault(size=5, sort="username") Pageable pageable){
        return memberRepository.findAll(pageable)
                .map(MemberDTO::new);
    }
    // http://localhost:8080/members?page=0&size=3&sort=id,desc&sort=username,desc

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));

        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user"+i, i));
        }
    }
}
