package hiring.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import hiring.userRegistration.model.UserEntity;

@Service
public class CandidateProducer {

    private static final String TOPIC = "candidate_notifications";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCandidateEvent(UserEntity user) {
        kafkaTemplate.send(TOPIC, user);
    }
}
