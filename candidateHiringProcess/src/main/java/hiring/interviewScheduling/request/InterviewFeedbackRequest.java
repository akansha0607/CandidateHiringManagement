package hiring.interviewScheduling.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFeedbackRequest {
    private String interviewer;
    private String feedback;
    private int rating;
}
