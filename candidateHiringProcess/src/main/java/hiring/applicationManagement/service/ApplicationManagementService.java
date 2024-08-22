package hiring.applicationManagement.service;

import hiring.applicationManagement.model.ApplicationDetailsEntity;
import hiring.applicationManagement.request.ApplicationManagementRequest;
import hiring.applicationManagement.request.UpdateApplicationStatusRequest;
import hiring.applicationManagement.response.ApplicationManagementResponse;
import hiring.applicationManagement.response.UpdateApplicationStatusResponse;

import java.util.List;

public interface ApplicationManagementService {

    ApplicationManagementResponse applyForJob(Long jobId, ApplicationManagementRequest applicationRequest);

    List<ApplicationManagementResponse> getAllApplicationsForJob(Long jobId);

    ApplicationManagementResponse getApplicationDetails(Long applicationId);

    UpdateApplicationStatusResponse updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequest request);

    List<ApplicationDetailsEntity> getApplicationsByStatus(String status);

}
