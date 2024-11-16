package com.ganzithon.Hexfarming.domain.user.dto.fromServer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter // getXXX() 메서드를 자동으로 만들어줌
@Builder
@AllArgsConstructor
public class ResponseTokenDto {
    private String accessToken;
    private String refreshToken;
}
