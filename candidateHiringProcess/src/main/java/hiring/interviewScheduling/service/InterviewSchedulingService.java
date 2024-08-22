package hiring.interviewScheduling.service;

import hiring.interviewScheduling.model.InterviewEntity;
import hiring.interviewScheduling.model.InterviewFeedbackEntity;
import hiring.interviewScheduling.request.InterviewFeedbackRequest;
import hiring.interviewScheduling.response.InterviewFeedbackResponse;
import hiring.interviewScheduling.response.InterviewSchedulingResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewSchedulingService {

    InterviewSchedulingResponse scheduleInterview(Long candidateId, Long jobId, String interviewer, LocalDateTime scheduledDateTime, String location, String interviewType);

    InterviewSchedulingResponse updateInterview(Long interviewId, LocalDateTime scheduledDateTime, String location, String interviewType);

    void cancelInterview(Long interviewId);

    InterviewEntity getInterviewDetails(Long interviewId);

    InterviewFeedbackResponse submitFeedback(Long interviewId, InterviewFeedbackRequest feedbackRequest);

    List<InterviewFeedbackResponse> getFeedbackByInterviewId(Long interviewId);
}
