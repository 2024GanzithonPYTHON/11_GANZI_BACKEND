package com.ganzithon.Hexfarming.domain.user;

import static org.assertj.core.api.Assertions.*;
import com.ganzithon.Hexfarming.domain.experience.ExperienceService;
import com.ganzithon.Hexfarming.domain.user.dto.fromClient.SignUpClientDto;
import com.ganzithon.Hexfarming.global.utility.JwtManager;
import com.ganzithon.Hexfarming.global.utility.PasswordEncoderManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoderManager passwordEncoderManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExperienceService experienceService;

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private UserService userService;

    @Test
    void 회원가입에_성공한다() {
        // given
        String email = "test@test.com";
        String password = "testtest";
        String rePassword = "testtest";
        String name = "테스트";
        String nickname = "테스트닉";
        SignUpClientDto dto = new SignUpClientDto(email, password, rePassword, name, nickname);

        // when
        userService.signUp(dto);

        // then
        User user = userRepository.findByEmail(email).get();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(passwordEncoderManager.matches(password, user.getPassword())).isTrue();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getNickname()).isEqualTo(nickname);
    }
}
