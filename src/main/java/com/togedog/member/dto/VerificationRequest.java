package com.togedog.member.dto;

import com.togedog.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class VerificationRequest {
    @Email
    private String email;
    private String authCode;
//    private MemberDto.Post memberDto;
}
