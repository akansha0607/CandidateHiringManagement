package hiring.interviewScheduling.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewSchedulingRequest {

    private Long candidateId;
    private Long jobId;
    private String interviewer;
    private LocalDateTime scheduledDateTime;
    private String location;
    private String interviewType;
}
