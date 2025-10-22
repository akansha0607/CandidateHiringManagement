package hiring.kafka;

import hiring.userRegistration.model.UserEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class NotificationService {

    @Async
    public CompletableFuture<Void> sendEmailAsync(UserEntity candidate) {
        System.out.println("Sending email to " + candidate.getEmail());
        // call email API
        return CompletableFuture.completedFuture(null);
    }
}
