package hiring.jobManagement.serviceimpl;

import hiring.exception.JobNotFoundException;
import hiring.jobManagement.dto.JobPosting;
import hiring.jobManagement.model.JobDetailsEntity;
import hiring.jobManagement.repository.JobDetailsRepository;
import hiring.jobManagement.request.JobManagementRequest;
import hiring.jobManagement.response.JobManagementResponse;
import hiring.jobManagement.service.JobManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JobManagementServiceImpl implements JobManagementService {

    private final JobDetailsRepository jobRepository;

    @Autowired
    public JobManagementServiceImpl(JobDetailsRepository jobRepository
    ) {
        this.jobRepository = jobRepository;
    }


    public ResponseEntity<JobManagementResponse> manageJobPosting(@RequestBody JobManagementRequest jobManagementRequest) {

        JobDetailsEntity jobDetailsEntity = new JobDetailsEntity();
        jobDetailsEntity.setTitle(jobManagementRequest.getTitle());
        jobDetailsEntity.setDescription(jobManagementRequest.getDescription());
        jobDetailsEntity.setLocation(jobManagementRequest.getLocation());
        jobDetailsEntity.setSalaryRange(jobManagementRequest.getSalaryRange());
        jobDetailsEntity.setJobType(jobManagementRequest.getJobType());
        jobDetailsEntity.setPostedBy(jobManagementRequest.getPostedBy());
        jobDetailsEntity.setRequirements(jobManagementRequest.getRequirements());

        // Save the job posting
        JobDetailsEntity savedJob = saveJob(jobDetailsEntity);

        // Create a response object
        JobManagementResponse response = new JobManagementResponse();
        response.setJobId(savedJob.getId());
        response.setMessage("Job posting successfully saved");

        // Return the response wrapped in a ResponseEntity
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public JobDetailsEntity saveJob(JobDetailsEntity job) {
        return jobRepository.save(job);
    }


    public List<JobPosting> getAllJobPostings() {
        return jobRepository.findAll().stream()
                .map(job -> {
                    JobPosting dto = new JobPosting();
                    dto.setId(job.getId());
                    dto.setTitle(job.getTitle());
                    dto.setLocation(job.getLocation());
                    dto.setSalaryRange(job.getSalaryRange());
                    dto.setJobType(job.getJobType());
                    dto.setPostedDate(job.getPostedDate()); // Convert LocalDate to String
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public JobPosting getJobPostingById(Long id) {
        Optional<JobDetailsEntity> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            JobDetailsEntity job = jobOptional.get();
            JobPosting dto = new JobPosting();
            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setLocation(job.getLocation());
            dto.setSalaryRange(job.getSalaryRange());
            dto.setJobType(job.getJobType());
            dto.setRequirements(job.getRequirements());
            dto.setPostedBy(job.getPostedBy());
            dto.setPostedDate(job.getPostedDate()); // Convert LocalDate to String
            return dto;
        } else {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }
    }

    public JobManagementResponse updateJobPosting(Long id, JobManagementRequest jobManagementRequest) {
        Optional<JobDetailsEntity> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            JobDetailsEntity job = jobOptional.get();
            job.setTitle(jobManagementRequest.getTitle());
            job.setDescription(jobManagementRequest.getDescription());
            job.setLocation(jobManagementRequest.getLocation());
            job.setSalaryRange(jobManagementRequest.getSalaryRange());
            job.setJobType(jobManagementRequest.getJobType());
            job.setRequirements(jobManagementRequest.getRequirements());
            job.setPostedBy(jobManagementRequest.getPostedBy());

            jobRepository.save(job);

            JobManagementResponse response = new JobManagementResponse();
            response.setJobId(job.getId());
            response.setMessage("Updated");
            return response;
        } else {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }
    }

    public JobManagementResponse deleteJobPosting(Long id) {
        Optional<JobDetailsEntity> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            jobRepository.deleteById(id);  // Delete the job posting

            JobManagementResponse response = new JobManagementResponse();
            response.setJobId(id);
            response.setMessage("Deleted");
            return response;
        } else {
            throw new JobNotFoundException("Job with ID " + id + " not found");
        }
    }

}
