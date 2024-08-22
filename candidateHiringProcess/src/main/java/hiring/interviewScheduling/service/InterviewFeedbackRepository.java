package hiring.interviewScheduling.service;

import hiring.interviewScheduling.model.InterviewFeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewFeedbackRepository extends JpaRepository<InterviewFeedbackEntity, Long> {

    List<InterviewFeedbackEntity> findByInterviewId(Long interviewId);

}
