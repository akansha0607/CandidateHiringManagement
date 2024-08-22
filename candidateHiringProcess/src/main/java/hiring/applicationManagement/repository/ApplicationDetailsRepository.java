package hiring.applicationManagement.repository;

import hiring.applicationManagement.model.ApplicationDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationDetailsRepository extends JpaRepository<ApplicationDetailsEntity, Long> {

    List<ApplicationDetailsEntity> findByJobId(Long jobId);

    Optional<ApplicationDetailsEntity> findById(Long applicationId);

    List<ApplicationDetailsEntity> findByStatus(String status);

}
