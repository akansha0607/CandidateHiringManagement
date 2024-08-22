package hiring.userRegistration.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private String status;
    private String token;
}
