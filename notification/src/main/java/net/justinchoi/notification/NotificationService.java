package net.justinchoi.notification;

import lombok.AllArgsConstructor;
import net.justinchoi.clients.notification.NotificationRequest;
import net.justinchoi.clients.notification.NotificationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        notificationRepository.save(Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("System")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build());
        return new NotificationResponse(true);
    }

}
