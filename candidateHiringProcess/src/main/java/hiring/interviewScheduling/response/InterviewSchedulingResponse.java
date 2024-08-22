package hiring.interviewScheduling.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewSchedulingResponse {
    private Long interviewId;
    private String status;
}
