package com.ganzithon.Hexfarming.domain.user;

import com.ganzithon.Hexfarming.domain.user.dto.fromClient.LoginClientDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromClient.SignUpClientDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromClient.ValidateDuplicateUsernameClientDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.ResponseTokenDto;
import com.ganzithon.Hexfarming.domain.user.dto.fromServer.ValidateDuplicateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j // 로그를 자세하게 남겨줘서 적용하면 좋음
@RequestMapping("/user") // <www.hexfarming.com/user> Url 아래로 들어오는 요청을 처리
@RestController // REST API로 사용되는 컨트롤러 (url의 엔드포인트를 매칭해준다)
public class UserController {

    @Autowired
    UserService userService; // UserService에 대한 의존성 주입 -> UserController에서 UserService 사용 가능

    @Tag(name = "유저")
    @Operation(summary = "회원가입", description = "회원가입한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseTokenDto.class))),
            @ApiResponse(responseCode = "400", description = "중복된 아이디 혹은 닉네임, 또는 비밀번호가 일치하지 않은 경우", content = @Content(mediaType = "application/json"))
    })
    @CrossOrigin(origins = "*", methods = RequestMethod.POST) // 해당 Endpoint로 들어오는 Post 요청은 CORS를 모두 허용
    @PostMapping("/signup") // /user/signup에 대한 Post 요청을 받겠다
    public ResponseTokenDto signUp(@RequestBody SignUpClientDto dto) { // SignUpClientDto를 요청받아서 SignUpServerDto를 반환함
        return userService.signUp(dto);
    }

    @Tag(name = "유저")
    @Operation(summary = "로그인", description = "로그인한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseTokenDto.class))),
            @ApiResponse(responseCode = "400", description = "아이디 혹은 비밀번호가 일치하지 않은 경우", content = @Content(mediaType = "application/json"))
    })
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping("/login")
    public ResponseTokenDto logIn(@RequestBody LoginClientDto dto) {
        return userService.logIn(dto);
    }

    @Tag(name = "유저")
    @Operation(summary = "중복 아이디 검사", description = "중복된 아이디인지 검사한다\n(중복이면 true, 중복이 아니면 false 반환)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "검사 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidateDuplicateDto.class)))
    })
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping("/validateDuplicateUsername")
    public ValidateDuplicateDto validateDuplicateUsername(@RequestBody ValidateDuplicateUsernameClientDto dto) {
        return userService.validateDuplicateUsername(dto);
    }
}
