package com.ganzithon.Hexfarming.domain.user;

import com.ganzithon.Hexfarming.dto.SignUpClientDto;
import com.ganzithon.Hexfarming.dto.SignUpServerDto;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Service 클래스(로직을 처리)임을 알려줌
public class UserService {

    @Autowired
    UserRepository userRepository; // UserRepository 의존성 주입 -> UserService에서 사용 가능

    @Transactional // DB에 접근한다는 것을 알리는 애너테이션
    public SignUpServerDto signUp(SignUpClientDto dto) throws IllegalArgumentException {
        // 입력된 두 패스워드가 같은지 검사
        validateRePasswordIsCorrect(dto.getPassword(), dto.getRePassword());

        // DB에 해당 username이나 nickname이 동일한 User가 있는지 검증
        validateUsername(dto.getUsername());
        validateNickname(dto.getNickname());

        // 비밀번호 암호화(해싱)
        String hashedPassword = PasswordEncoder;

        // 새로운 유저를 생성하여 DB에 저장
        User newUser = User.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .nickname(dto.getNickname())
                .build();
        userRepository.save(newUser);
    }

    private void validateRePasswordIsCorrect(String password, String rePassword) throws IllegalArgumentException {
        if (password != rePassword) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    private void validateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }
}
