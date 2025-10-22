package hiring.kafka;

import hiring.kafka.NotificationService;
import hiring.userRegistration.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "candidate_notifications", groupId = "candidate-group")
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @KafkaHandler
    public void consumeCandidate(UserEntity user) {
        notificationService.sendEmailAsync(user);
    }
}
