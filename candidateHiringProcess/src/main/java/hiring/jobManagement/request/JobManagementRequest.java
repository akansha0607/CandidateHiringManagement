package hiring.jobManagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobManagementRequest {
    private String title;
    private String description;
    private String location;
    private String salaryRange;
    private String jobType;
    private List<String> requirements;
    private String postedBy;
}
