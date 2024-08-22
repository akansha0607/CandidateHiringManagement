package hiring.applicationManagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationManagementRequest {
    private String candidateName;
    private String email;
    private String resume; // Base64 encoded resume content
    private String coverLetter;
}
