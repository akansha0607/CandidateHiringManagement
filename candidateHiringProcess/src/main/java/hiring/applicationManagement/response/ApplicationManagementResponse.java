package hiring.applicationManagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationManagementResponse {
    private Long applicationId;
    private String status;
    private String candidateName;
    private String email;
    private String submittedDate;
    private String resume; // Base64 encoded resume content
    private String coverLetter;
}
