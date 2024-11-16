package com.ganzithon.Hexfarming.domain.experience;

import com.ganzithon.Hexfarming.domain.experience.dto.ExperienceServerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @Tag(name = "경험치 관련")
    @Operation(summary = "자신의 경험치 조회", description = "현재 요청한 유저의 역량 6개에 대한 경험치 정보를 조회한다.\n\nAbility 목록: LEADERSHIP(리더십), PROBLEM_SOLVING(창의력), COMMUNICATION_SKILL(소통 역량), DILIGENCE(성실성), RESILIENCE(회복 탄력성), TENACITY(인성)\n\nTier 목록: TIER1_1(최하) ~ TIER5_5(최상)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExperienceServerDto.class)))),
            @ApiResponse(responseCode = "400", description = "중복된 아이디 혹은 닉네임, 또는 비밀번호가 일치하지 않은 경우", content = @Content(mediaType = "application/json"))
    })
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping("/getMyExperiences")
    public List<ExperienceServerDto> getMyExperiences() {
        return experienceService.getMyExperiences();
    }
}
