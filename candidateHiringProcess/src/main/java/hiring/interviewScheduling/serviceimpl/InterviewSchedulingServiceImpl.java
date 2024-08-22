package hiring.interviewScheduling.serviceimpl;

import hiring.exception.InterviewNotFoundException;
import hiring.interviewScheduling.model.InterviewEntity;
import hiring.interviewScheduling.model.InterviewFeedbackEntity;
import hiring.interviewScheduling.repository.InterviewSchedulingRepository;
import hiring.interviewScheduling.request.InterviewFeedbackRequest;
import hiring.interviewScheduling.response.InterviewFeedbackResponse;
import hiring.interviewScheduling.response.InterviewSchedulingResponse;
import hiring.interviewScheduling.service.InterviewFeedbackRepository;
import hiring.interviewScheduling.service.InterviewSchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InterviewSchedulingServiceImpl implements InterviewSchedulingService {

    private final InterviewSchedulingRepository interviewSchedulingRepository;

    private final InterviewFeedbackRepository interviewFeedbackRepository;

    @Autowired
    public InterviewSchedulingServiceImpl(InterviewSchedulingRepository interviewSchedulingRepository,
                                          InterviewFeedbackRepository interviewFeedbackRepository
    ) {
        this.interviewSchedulingRepository = interviewSchedulingRepository;
        this.interviewFeedbackRepository = interviewFeedbackRepository;
    }

    public InterviewSchedulingResponse scheduleInterview(Long candidateId, Long jobId, String interviewer,
                                                         LocalDateTime scheduledDateTime, String location,
                                                         String interviewType) {
        InterviewEntity interview = new InterviewEntity();
        interview.setCandidateId(candidateId);
        interview.setJobId(jobId);
        interview.setInterviewer(interviewer);
        interview.setScheduledDateTime(scheduledDateTime);
        interview.setLocation(location);
        interview.setInterviewType(interviewType);
        interview.setStatus("Scheduled");

        InterviewEntity savedInterview = interviewSchedulingRepository.save(interview);

        return new InterviewSchedulingResponse(savedInterview.getId(), "Scheduled");

    }


    public InterviewSchedulingResponse updateInterview(Long interviewId, LocalDateTime scheduledDateTime, String location, String interviewType) {
        Optional<InterviewEntity> optionalInterview = interviewSchedulingRepository.findById(interviewId);

        if (optionalInterview.isPresent()) {
            InterviewEntity interview = optionalInterview.get();
            interview.setScheduledDateTime(scheduledDateTime);
            interview.setLocation(location);
            interview.setInterviewType(interviewType);

            InterviewEntity updatedInterview = interviewSchedulingRepository.save(interview);

            return new InterviewSchedulingResponse(updatedInterview.getId(), "Updated");
        } else {
            throw new InterviewNotFoundException("Interview with ID " + interviewId + " not found.");
        }
    }

    public void cancelInterview(Long interviewId) {
        Optional<InterviewEntity> optionalInterview = interviewSchedulingRepository.findById(interviewId);

        if (optionalInterview.isPresent()) {
            InterviewEntity interview = optionalInterview.get();
            interview.setStatus("Canceled");
            interviewSchedulingRepository.save(interview);
        } else {
            throw new InterviewNotFoundException("Interview with ID " + interviewId + " not found.");
        }
    }

    public InterviewEntity getInterviewDetails(Long interviewId) {
        InterviewEntity interviewEntity = interviewSchedulingRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException("Interview with ID " + interviewId + " not found"));

        return interviewEntity;
    }

//    private InterviewEntity mapToDTO(InterviewEntity interviewEntity) {
//        InterviewEntity interviewDTO = new InterviewEntity();
//        interviewDTO.setId(interviewEntity.getId());
//        interviewDTO.setCandidateId(interviewEntity.getCandidateId());
//        interviewDTO.setJobId(interviewEntity.getJobId());
//        interviewDTO.setInterviewer(interviewEntity.getInterviewer());
//        interviewDTO.setScheduledDateTime(interviewEntity.getScheduledDateTime());
//        interviewDTO.setLocation(interviewEntity.getLocation());
//        interviewDTO.setInterviewType(interviewEntity.getInterviewType());
//        interviewDTO.setStatus(interviewEntity.getStatus());
//        return interviewDTO;
//    }

    public InterviewFeedbackResponse submitFeedback(Long interviewId, InterviewFeedbackRequest feedbackRequest) {
        InterviewEntity interviewEntity = interviewSchedulingRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewNotFoundException("Interview with ID " + interviewId + " not found"));

        InterviewFeedbackEntity feedbackEntity = new InterviewFeedbackEntity();
        feedbackEntity.setInterview(interviewEntity);
        feedbackEntity.setInterviewer(feedbackRequest.getInterviewer());
        feedbackEntity.setFeedback(feedbackRequest.getFeedback());
        feedbackEntity.setRating(feedbackRequest.getRating());

        InterviewFeedbackEntity savedFeedback = interviewFeedbackRepository.save(feedbackEntity);

        InterviewFeedbackResponse feedbackResponse = new InterviewFeedbackResponse();
        feedbackResponse.setFeedbackId(savedFeedback.getId());
        feedbackResponse.setStatus("Feedback Submitted");

        return feedbackResponse;
    }

    public List<InterviewFeedbackResponse> getFeedbackByInterviewId(Long interviewId) {
        List<InterviewFeedbackEntity> feedbackEntities = interviewFeedbackRepository.findByInterviewId(interviewId);
        return feedbackEntities.stream()
                .map(feedback -> {
                    InterviewFeedbackResponse response = new InterviewFeedbackResponse();
                    response.setFeedbackId(feedback.getId());
                    response.setInterviewer(feedback.getInterviewer());
                    response.setFeedback(feedback.getFeedback());
                    response.setRating(feedback.getRating());
                    response.setStatus("Feedback Retrieved"); // You can set appropriate status if needed
                    return response;
                })
                .collect(Collectors.toList());
    }

}
