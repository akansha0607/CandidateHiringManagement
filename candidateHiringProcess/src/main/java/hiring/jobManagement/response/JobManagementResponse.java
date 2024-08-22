package hiring.jobManagement.response;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobManagementResponse {
    private Long jobId;
    private String message;
}
