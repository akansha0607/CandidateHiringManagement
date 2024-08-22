package hiring.applicationManagement.controller;

import hiring.applicationManagement.model.ApplicationDetailsEntity;
import hiring.applicationManagement.request.ApplicationManagementRequest;
import hiring.applicationManagement.request.UpdateApplicationStatusRequest;
import hiring.applicationManagement.response.ApplicationManagementResponse;
import hiring.applicationManagement.response.UpdateApplicationStatusResponse;
import hiring.applicationManagement.service.ApplicationManagementService;
import hiring.exception.JobNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hiring/application/manage")
public class ApplicationManagementController {

    private ApplicationManagementService applicationManagementService;

    @Autowired
    public ApplicationManagementController(ApplicationManagementService applicationManagementService) {
        this.applicationManagementService = applicationManagementService;
    }

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApplicationManagementResponse> applyForJob(
            @PathVariable Long jobId,
            @RequestBody ApplicationManagementRequest applicationRequest) {
        try {
            ApplicationManagementResponse response = applicationManagementService.applyForJob(jobId, applicationRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (JobNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{jobId}/applications")
    public ResponseEntity<List<ApplicationManagementResponse>> getAllApplicationsForJob(@PathVariable Long jobId) {
        List<ApplicationManagementResponse> applications = applicationManagementService.getAllApplicationsForJob(jobId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationManagementResponse> getApplicationDetails(@PathVariable Long applicationId) {
        ApplicationManagementResponse applicationDetails = applicationManagementService.getApplicationDetails(applicationId);
        return new ResponseEntity<>(applicationDetails, HttpStatus.OK);
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<UpdateApplicationStatusResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody UpdateApplicationStatusRequest request) {

        UpdateApplicationStatusResponse response = applicationManagementService.updateApplicationStatus(applicationId, request);
        return new ResponseEntity<UpdateApplicationStatusResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/get/application/by/status")
    public ResponseEntity<List<ApplicationDetailsEntity>> getApplicationsByStatus(@RequestParam String status) {
        List<ApplicationDetailsEntity> applications = applicationManagementService.getApplicationsByStatus(status);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

}
