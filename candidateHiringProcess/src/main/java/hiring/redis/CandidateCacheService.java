package hiring.redis;

import hiring.redis.CandidateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CandidateCacheService {

    @Autowired
    private RedisTemplate<String, CandidateCache> redisTemplate;

    private final String PREFIX_ID = "candidateId:";
    private final String PREFIX_USERNAME = "candidate:";

    // 1️⃣ Cache candidate by username
    public void cacheCandidateByUsername(CandidateCache candidate) {
        redisTemplate.opsForValue().set(PREFIX_USERNAME + candidate.getName(), candidate);
    }

    // 2️⃣ Get candidate by username
    public CandidateCache getCandidateByUsername(String username) {
        return redisTemplate.opsForValue().get(PREFIX_USERNAME + username);
    }

    // 3️⃣ Cache candidate by ID
    public void cacheCandidateById(CandidateCache candidate) {
        redisTemplate.opsForValue().set(PREFIX_ID + candidate.getId(), candidate);
    }

    // 4️⃣ Get candidate by ID
    public CandidateCache getCandidateById(String id) {
        return redisTemplate.opsForValue().get(PREFIX_ID + id);
    }
}
