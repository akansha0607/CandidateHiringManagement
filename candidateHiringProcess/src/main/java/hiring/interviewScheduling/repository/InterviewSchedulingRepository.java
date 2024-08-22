package hiring.interviewScheduling.repository;

import hiring.interviewScheduling.model.InterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewSchedulingRepository extends JpaRepository<InterviewEntity, Long> {

}
