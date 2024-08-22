package hiring.jobManagement.repository;

import hiring.jobManagement.model.JobDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDetailsRepository extends JpaRepository<JobDetailsEntity, Long> {

}
