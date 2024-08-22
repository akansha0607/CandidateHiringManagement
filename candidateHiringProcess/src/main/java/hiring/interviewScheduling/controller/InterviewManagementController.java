package hiring.interviewScheduling.controller;

import hiring.interviewScheduling.model.InterviewEntity;
import hiring.interviewScheduling.model.InterviewFeedbackEntity;
import hiring.interviewScheduling.request.InterviewFeedbackRequest;
import hiring.interviewScheduling.request.InterviewSchedulingRequest;
import hiring.interviewScheduling.response.InterviewFeedbackResponse;
import hiring.interviewScheduling.response.InterviewSchedulingResponse;
import hiring.interviewScheduling.service.InterviewSchedulingService;
import hiring.interviewScheduling.serviceimpl.InterviewSchedulingServiceImpl;
import hiring.jobManagement.service.JobManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hiring/interview/manage")
public class InterviewManagementController {

    private InterviewSchedulingService interviewSchedulingService;

    @Autowired
    public InterviewManagementController(InterviewSchedulingService interviewSchedulingService) {
        this.interviewSchedulingService = interviewSchedulingService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<InterviewSchedulingResponse> scheduleInterview(@RequestBody InterviewSchedulingRequest interviewRequest) {
        try {
            InterviewSchedulingResponse scheduledInterview = interviewSchedulingService.scheduleInterview(
                    interviewRequest.getCandidateId(),
                    interviewRequest.getJobId(),
                    interviewRequest.getInterviewer(),
                    interviewRequest.getScheduledDateTime(),
                    interviewRequest.getLocation(),
                    interviewRequest.getInterviewType()
            );

            InterviewSchedulingResponse response = new InterviewSchedulingResponse();
            response.setInterviewId(scheduledInterview.getInterviewId());
            response.setStatus(scheduledInterview.getStatus());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{interviewId}")
    public ResponseEntity<InterviewSchedulingResponse> updateInterview(
            @PathVariable Long interviewId,
            @RequestBody InterviewSchedulingRequest interviewUpdateRequest) {

        InterviewSchedulingResponse response = interviewSchedulingService.updateInterview(
                interviewId,
                interviewUpdateRequest.getScheduledDateTime(),
                interviewUpdateRequest.getLocation(),
                interviewUpdateRequest.getInterviewType()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{interviewId}")
    public ResponseEntity<Map<String, String>> cancelInterview(@PathVariable Long interviewId) {
        interviewSchedulingService.cancelInterview(interviewId);
        Map<String, String> response = new HashMap<>();
        response.put("interviewId", interviewId.toString());
        response.put("status", "Canceled");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{interviewId}")
    public ResponseEntity<InterviewEntity> getInterviewDetails(@PathVariable Long interviewId) {
        InterviewEntity interviewDTO = interviewSchedulingService.getInterviewDetails(interviewId);
        return ResponseEntity.ok(interviewDTO);
    }

    @PostMapping("/{interviewId}/feedback")
    public ResponseEntity<InterviewFeedbackResponse> submitFeedback(@PathVariable Long interviewId, @RequestBody InterviewFeedbackRequest feedbackRequest) {
        InterviewFeedbackResponse feedbackResponse = interviewSchedulingService.submitFeedback(interviewId, feedbackRequest);
        return new ResponseEntity<>(feedbackResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{interviewId}/feedback")
    public ResponseEntity<List<InterviewFeedbackResponse>> getInterviewFeedback(@PathVariable Long interviewId) {
        List<InterviewFeedbackResponse> feedbackList = interviewSchedulingService.getFeedbackByInterviewId(interviewId);
        if (feedbackList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(feedbackList);
    }
}
