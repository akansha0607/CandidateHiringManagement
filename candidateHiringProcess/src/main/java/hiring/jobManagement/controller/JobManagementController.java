package hiring.jobManagement.controller;

import hiring.exception.JobNotFoundException;
import hiring.jobManagement.dto.JobPosting;
import hiring.jobManagement.request.JobManagementRequest;
import hiring.jobManagement.response.JobManagementResponse;
import hiring.jobManagement.service.JobManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hiring/job/manage")
public class JobManagementController {

    private JobManagementService jobManagementService;

    @Autowired
    public JobManagementController(JobManagementService jobManagementService) {
        this.jobManagementService = jobManagementService;
    }

    @PostMapping(value = "/create/job", consumes ="application/json" , produces = "application/json")
    public ResponseEntity<JobManagementResponse> jobManagementResponse(@RequestBody JobManagementRequest jobManagementRequest) {
        ResponseEntity<JobManagementResponse> manualResponse = jobManagementService.manageJobPosting(jobManagementRequest);
        return manualResponse;
    }

    @GetMapping(value = "/jobs")
    public List<JobPosting> getAllJobPostings() {
        return jobManagementService.getAllJobPostings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobPostingById(@PathVariable Long id) {
        try {
            JobPosting jobPosting = jobManagementService.getJobPostingById(id);
            return new ResponseEntity<>(jobPosting, HttpStatus.OK);
        } catch (JobNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobManagementResponse> updateJobPosting(
            @PathVariable Long id,
            @RequestBody JobManagementRequest jobManagementRequest) {
        try {
            JobManagementResponse response = jobManagementService.updateJobPosting(id, jobManagementRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JobNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JobManagementResponse> deleteJobPosting(@PathVariable Long id) {
        try {
            JobManagementResponse response = jobManagementService.deleteJobPosting(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JobNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
