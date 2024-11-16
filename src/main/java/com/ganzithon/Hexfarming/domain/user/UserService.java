package com.ganzithon.Hexfarming.domain.user;

import com.ganzithon.Hexfarming.domain.user.dto.fromClient.LoginClientDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromClient.SignUpClientDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromClient.ValidateDuplicateUsernameClientDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.ResponseTokenDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.ValidateDuplicateDto;
import com.ganzithon.Hexfarming.utility.JwtManager;
import com.ganzithon.Hexfarming.utility.PasswordEncoderManager;
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
        // 입력된 두 패스워드가 같은지 검사
        validateRePasswordIsCorrect(dto.getPassword(), dto.getRePassword());

        // 비밀번호 암호화(해싱)
        String hashedPassword = passwordEncoderManager.encode(dto.getPassword());

        // 새로운 유저를 생성하여 DB에 저장
        User newUser = User.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .nickname(dto.getNickname())
                .build();
        userRepository.save(newUser);

        // Jwt 토큰 생성 후 발급
        String accessToken = jwtManager.createToken(newUser.getId(), false);
        String refreshToken = jwtManager.createToken(newUser.getId(), true);

        return ResponseTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ResponseTokenDto logIn(LoginClientDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        // 해당 username으로 등록된 유저가 있는지 확인하고 없으면 예외
        User existUser = userRepository.findByUsername(username);
        if (existUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 잘못되었습니다.");
        }

        // 패스워드가 일치하는지 확인 후 토큰 반환
        if (passwordEncoderManager.matches(password, existUser.getPassword())) {
            String accessToken = jwtManager.createToken(existUser.getId(), false);
            String refreshToken = jwtManager.createToken(existUser.getId(), true);

            return ResponseTokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 잘못되었습니다.");
    }


    public ValidateDuplicateDto validateDuplicateUsername(ValidateDuplicateUsernameClientDto dto) {
        boolean result = userRepository.existsByUsername(dto.username());
        return new ValidateDuplicateDto(result);
    }

    private void validateRePasswordIsCorrect(String password, String rePassword) throws IllegalArgumentException {
        if (!password.equals(rePassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }
}
