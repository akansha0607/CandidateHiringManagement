package hiring.jobManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class JobPosting {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String salaryRange;
    private List<String> requirements;
    private String jobType;
    private String postedBy;
    private String postedDate;
}

