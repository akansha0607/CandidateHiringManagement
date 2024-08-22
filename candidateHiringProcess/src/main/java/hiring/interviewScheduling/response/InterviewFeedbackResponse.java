package hiring.interviewScheduling.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFeedbackResponse {
    private Long feedbackId;
    private String status;
    private String interviewer;
    private String feedback;
    private int rating;
}
