package hiring.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
public class EmbeddedKafkaConfig {
    @Bean
    public EmbeddedKafkaBroker embeddedKafkaBroker() {
        EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaBroker(1, true, "candidate_notifications");
        embeddedKafka.kafkaPorts(9092); // matches your KafkaTemplate config
        return embeddedKafka;
    }
}
