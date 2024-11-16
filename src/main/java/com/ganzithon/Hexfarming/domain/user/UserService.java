package com.ganzithon.Hexfarming.domain.user;

import com.ganzithon.Hexfarming.domain.user.dto.fromClient.*;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.CheckRePasswordServerDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.ResponseTokenDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.CheckDuplicateDto;
import com.ganzithon.Hexfarming.domain.user.util.UserValidator;
import com.ganzithon.Hexfarming.global.utility.JwtManager;
import com.ganzithon.Hexfarming.global.utility.PasswordEncoderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service // Service 클래스(로직을 처리)임을 알려줌
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoderManager passwordEncoderManager;
    private final JwtManager jwtManager;

    @Autowired // Bean으로 관리하고 있는 객체들을 자동으로 주입해줌
    public UserService(UserRepository userRepository, PasswordEncoderManager passwordEncoderManager, JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.passwordEncoderManager = passwordEncoderManager;
        this.jwtManager = jwtManager;
    }

    @Transactional // DB에 접근한다는 것을 알리는 애너테이션
    public ResponseTokenDto signUp(SignUpClientDto dto) throws IllegalArgumentException {
        // 패스워드의 길이를 검사
        UserValidator.validatePasswordLength(dto.password());

        // 비밀번호 암호화(해싱)
        String hashedPassword = passwordEncoderManager.encode(dto.password());

        // 새로운 유저를 생성하여 DB에 저장
        User newUser = User.builder()
                .email(dto.email())
                .password(hashedPassword)
                .name(dto.name())
                .build();
        userRepository.save(newUser);

        // Jwt 토큰 생성 후 발급
        String accessToken = jwtManager.createToken(newUser.getId(), false);
        String refreshToken = jwtManager.createToken(newUser.getId(), true);

        return new ResponseTokenDto(accessToken, refreshToken);
    }

    public ResponseTokenDto logIn(LoginClientDto dto) {
        String email = dto.email();
        String password = dto.password();

        // 해당 email으로 등록된 유저가 있는지 확인하고 없으면 예외
        User existUser = userRepository.findByEmail(email);
        if (existUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 잘못되었습니다.");
        }

        // 패스워드가 일치하는지 확인 후 토큰 반환
        if (passwordEncoderManager.matches(password, existUser.getPassword())) {
            String accessToken = jwtManager.createToken(existUser.getId(), false);
            String refreshToken = jwtManager.createToken(existUser.getId(), true);

            return new ResponseTokenDto(accessToken, refreshToken);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 잘못되었습니다.");
    }

    public CheckDuplicateDto checkDuplicateEmail(CheckDuplicateEmailClientDto dto) {
        boolean result = userRepository.existsByEmail(dto.email());
        return new CheckDuplicateDto(result);
    }

    public CheckDuplicateDto checkDuplicateName(CheckDuplicateNameClientDto dto) {
        boolean result = userRepository.existsByName(dto.name());
        return new CheckDuplicateDto(result);
    }

    public CheckRePasswordServerDto checkRePassword(CheckRePasswordClientDto dto) {
        boolean result = UserValidator.validateRePasswordIsCorrect(dto.password(), dto.rePassword());
        return new CheckRePasswordServerDto(result);
    }
}
