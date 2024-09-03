package hiring.userRegistration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Maps this field to the 'id' column in the 'users' table
    private Long id;

    @Column(name = "username", nullable = false)  // 'username' column, non-nullable, unique
    private String username;

    @Column(name = "password", nullable = false)  // 'password' column, non-nullable
    private String password;

    @Column(name = "email", nullable = false)  // 'email' column, non-nullable, unique
    private String email;

    @Column(name = "name", nullable = false)  // 'name' column, non-nullable
    private String name;

    @Column(name = "role", nullable = false)  // 'role' column, non-nullable (e.g., HR, Interviewer, Candidate)
    private String role;

    @Column(name = "resume", columnDefinition = "TEXT")  // 'resume' column, optional, stores long text
    private String resume;

}
