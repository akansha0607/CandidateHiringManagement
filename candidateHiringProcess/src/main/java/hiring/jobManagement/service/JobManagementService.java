package hiring.jobManagement.service;

import hiring.jobManagement.dto.JobPosting;
import hiring.jobManagement.model.JobDetailsEntity;
import hiring.jobManagement.request.JobManagementRequest;
import hiring.jobManagement.response.JobManagementResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobManagementService {

    ResponseEntity<JobManagementResponse> manageJobPosting(JobManagementRequest jobManagementRequest);

    JobDetailsEntity saveJob(JobDetailsEntity job);

    List<JobPosting> getAllJobPostings();

    JobPosting getJobPostingById(Long id);

    JobManagementResponse updateJobPosting(Long id, JobManagementRequest jobManagementRequest);  // New method

    JobManagementResponse deleteJobPosting(Long id);  // New method for deleting a job

}
