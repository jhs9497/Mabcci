package com.mabcci.domain.member.ui;

import com.mabcci.domain.member.application.MemberService;
import com.mabcci.domain.member.dto.JoinRequestDto;
import com.mabcci.domain.member.dto.MemberResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/api/members")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDto joinRequestDto) {
        MemberResponseDto joinedResponseDto = memberService.join(joinRequestDto.entity());
        validateNull(joinedResponseDto);
        return ResponseEntity.ok().body(joinedResponseDto);
    }

    @GetMapping("/api/members/{nickname}")
    public ResponseEntity<?> findByNickname(@PathVariable String nickname) {
        MemberResponseDto memberResponseDto = memberService.findByNickName(nickname);
        validateNull(memberResponseDto);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @GetMapping("/api/members")
    public ResponseEntity<?> findAll() {
        List<MemberResponseDto> members = memberService.findAll();
        validateNull(members);
        return ResponseEntity.ok().body(members);
    }

    private void validateNull(Object object) {
        if(Objects.isNull(object)) {
            throw new AssertionError();
        }
    }

}
