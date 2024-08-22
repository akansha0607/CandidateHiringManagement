package hiring.userRegistration.serviceimpl;

import hiring.userRegistration.model.UserEntity;
import hiring.userRegistration.repository.UserRegistrationRepository;
import hiring.userRegistration.request.UserRegistrationRequest;
import hiring.userRegistration.response.UserRegistrationResponse;
import hiring.userRegistration.service.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRegistrationRepository userRepository;


    @Autowired
    public UserRegistrationServiceImpl (UserRegistrationRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest userDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());  // Consider encrypting the password
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());

        // Handle resume if the user is a candidate
        if ("Candidate".equalsIgnoreCase(userDto.getRole())) {
            user.setResume(userDto.getResume());
        }
        userRepository.save(user);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setStatus("Registered");

        return response;
    }

}
