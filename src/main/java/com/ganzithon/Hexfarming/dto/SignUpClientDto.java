package com.ganzithon.Hexfarming.dto;

import lombok.Getter;

@Getter
public class SignUpClientDto {
    private String username;
    private String password;
    private String rePassword; // 비밀번호 확인
    private String nickname;
}
