package com.ganzithon.Hexfarming.domain.notification;

import com.ganzithon.Hexfarming.domain.notification.dto.fromClient.DeleteNotificationClientDto;
import com.ganzithon.Hexfarming.domain.notification.dto.fromServer.ResponseNotificationServerDto;
import com.ganzithon.Hexfarming.domain.user.UserService;
import com.ganzithon.Hexfarming.domain.user.util.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, CustomUserDetailsService customUserDetailsService) {
        this.notificationRepository = notificationRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    public List<ResponseNotificationServerDto> getMyNotifications() {
        int nowUserId = customUserDetailsService.getCurrentUserDetails().getUser().getId();
        return getNotifications(nowUserId);
    }

    public List<ResponseNotificationServerDto> getNotifications(int userId) {
        Optional<List<Notification>> notificationsOptional = notificationRepository.findAllByUserId(userId);
        if (notificationsOptional.isEmpty()) {
            return new ArrayList<>();
        }

        return notificationsOptional.get().stream()
                .map(ResponseNotificationServerDto::from)
                .collect(Collectors.toList());
    }

    public void delete(DeleteNotificationClientDto dto) {
        notificationRepository.deleteById(dto.notificationId());
    }
}
