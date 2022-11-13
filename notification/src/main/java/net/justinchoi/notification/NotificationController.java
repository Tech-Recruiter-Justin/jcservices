package net.justinchoi.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.justinchoi.clients.notification.NotificationRequest;
import net.justinchoi.clients.notification.NotificationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public NotificationResponse sendNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("sending notification to {}", notificationRequest.toCustomerId());
        return notificationService.sendNotification(notificationRequest);
    }

}
