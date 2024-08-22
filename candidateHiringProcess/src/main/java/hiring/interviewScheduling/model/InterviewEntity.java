package hiring.interviewScheduling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interviews")
public class InterviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "interviewer")
    private String interviewer;

    @Column(name = "scheduled_date_time")
    private LocalDateTime scheduledDateTime;

    @Column(name = "location")
    private String location;

    @Column(name = "interview_type")
    private String interviewType;

    @Column(name = "status")
    private String status; // e.g., Scheduled, Completed, Canceled

}
