package com.ganzithon.Hexfarming.domain.notification;

import com.ganzithon.Hexfarming.domain.notification.dto.fromClient.DeleteNotificationClientDto;
import com.ganzithon.Hexfarming.domain.notification.dto.fromServer.ResponseNotificationServerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/Notification")
@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Tag(name = "알림")
    @Operation(summary = "내 알림 보기", description = "내가 받은 알림들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseNotificationServerDto.class)))),
    })
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping("/myNotifications")
    public List<ResponseNotificationServerDto> myNotifications() {
        return notificationService.getMyNotifications();
    }

    @Tag(name = "알림")
    @Operation(summary = "알림 삭제", description = "특정 알림을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공", content = @Content(mediaType = "application/json"))
    })
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody DeleteNotificationClientDto dto) {
        notificationService.delete(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
