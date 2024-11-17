package com.ganzithon.Hexfarming.domain.notification.dto.fromServer;

import com.ganzithon.Hexfarming.domain.notification.Notification;

public record ResponseNotificationServerDto(int notificationId, Long postId, String message) {

    public static ResponseNotificationServerDto from(Notification notification) {
        return new ResponseNotificationServerDto(notification.getId(), notification.getPost().getPostId(), notification.getMessage());
    }
}
