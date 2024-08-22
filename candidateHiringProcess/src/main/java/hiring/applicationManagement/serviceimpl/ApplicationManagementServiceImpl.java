package hiring.applicationManagement.serviceimpl;

import hiring.applicationManagement.model.ApplicationDetailsEntity;
import hiring.applicationManagement.repository.ApplicationDetailsRepository;
import hiring.applicationManagement.request.ApplicationManagementRequest;
import hiring.applicationManagement.request.UpdateApplicationStatusRequest;
import hiring.applicationManagement.response.ApplicationManagementResponse;
import hiring.applicationManagement.response.UpdateApplicationStatusResponse;
import hiring.applicationManagement.service.ApplicationManagementService;
import hiring.exception.ApplicationNotFoundException;
import hiring.exception.JobNotFoundException;
import hiring.jobManagement.repository.JobDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplicationManagementServiceImpl implements ApplicationManagementService {

    private final ApplicationDetailsRepository applicationRepository;

    private final JobDetailsRepository jobDetailsRepository;

    @Autowired
    public ApplicationManagementServiceImpl(ApplicationDetailsRepository applicationRepository,
                                            JobDetailsRepository jobDetailsRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.jobDetailsRepository = jobDetailsRepository;
    }

    public ApplicationManagementResponse applyForJob(Long jobId, ApplicationManagementRequest applicationRequest) {
        if (!jobDetailsRepository.existsById(jobId)) {
            throw new JobNotFoundException("Job with ID " + jobId + " not found");
        }

        ApplicationDetailsEntity applicationEntity = new ApplicationDetailsEntity();
        applicationEntity.setJobId(jobId);
        applicationEntity.setCandidateName(applicationRequest.getCandidateName());
        applicationEntity.setEmail(applicationRequest.getEmail());
        applicationEntity.setResume(applicationRequest.getResume());
        applicationEntity.setCoverLetter(applicationRequest.getCoverLetter());
        applicationEntity.setSubmittedDate(LocalDateTime.now());

        ApplicationDetailsEntity savedApplication = applicationRepository.save(applicationEntity);

        ApplicationManagementResponse response = new ApplicationManagementResponse();
        response.setApplicationId(savedApplication.getId());
        response.setStatus("Submitted");

        return response;
    }

    public ApplicationDetailsEntity saveJob(ApplicationDetailsEntity applicationDetails) {
        return applicationRepository.save(applicationDetails);
    }

    public List<ApplicationManagementResponse> getAllApplicationsForJob(Long jobId) {
        List<ApplicationDetailsEntity> applications = applicationRepository.findByJobId(jobId);

        return applications.stream()
                .map(application -> {
                    ApplicationManagementResponse response = new ApplicationManagementResponse();
                    response.setApplicationId(application.getId());
                    response.setCandidateName(application.getCandidateName());
                    response.setEmail(application.getEmail());
                    response.setSubmittedDate(application.getSubmittedDate().toString()); // Convert LocalDateTime to String
                    return response;
                })
                .collect(Collectors.toList());
    }

    public ApplicationManagementResponse getApplicationDetails(Long applicationId) {
        Optional<ApplicationDetailsEntity> applicationOpt = applicationRepository.findById(applicationId);

        ApplicationDetailsEntity application = applicationOpt.orElseThrow(() ->
                new ApplicationNotFoundException("Application not found with ID: " + applicationId));

        ApplicationManagementResponse response = new ApplicationManagementResponse();
        response.setApplicationId(application.getId());
        response.setCandidateName(application.getCandidateName());
        response.setEmail(application.getEmail());
        response.setResume(application.getResume());
        response.setCoverLetter(application.getCoverLetter());
        response.setSubmittedDate(application.getSubmittedDate().toString()); // Convert LocalDateTime to String

        return response;
    }

    public UpdateApplicationStatusResponse updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequest request) {
        Optional<ApplicationDetailsEntity> applicationOpt = applicationRepository.findById(applicationId);

        ApplicationDetailsEntity application = applicationOpt.orElseThrow(() ->
                new ApplicationNotFoundException("Application not found with ID: " + applicationId));

        application.setStatus(request.getStatus());
        application.setReviewer(request.getReviewer());
        ApplicationDetailsEntity updatedApplication = applicationRepository.save(application);

        UpdateApplicationStatusResponse response = new UpdateApplicationStatusResponse();
        response.setApplicationId(updatedApplication.getId());
        response.setStatus(updatedApplication.getStatus());

        return response;
    }


    public List<ApplicationDetailsEntity> getApplicationsByStatus(String status) {
        List<ApplicationDetailsEntity> applications = applicationRepository.findByStatus(status);
        return applications.stream()
                .map(app -> {
                    ApplicationDetailsEntity dto = new ApplicationDetailsEntity();
                    dto.setId(app.getId());
                    dto.setCandidateName(app.getCandidateName());
                    dto.setEmail(app.getEmail());
                    dto.setStatus(app.getStatus());
                    dto.setReviewer(app.getReviewer());
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
