package hiring.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("candidate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateCache {
    @Id
    private String id;
    private String name;
    private String email;
    private String role;
    private String password;
}
