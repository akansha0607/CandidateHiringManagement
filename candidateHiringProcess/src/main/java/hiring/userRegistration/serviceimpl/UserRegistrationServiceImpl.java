package hiring.userRegistration.serviceimpl;

import hiring.userRegistration.model.UserEntity;
import hiring.userRegistration.repository.UserRegistrationRepository;
import hiring.userRegistration.request.UserRegistrationRequest;
import hiring.userRegistration.response.UserRegistrationResponse;
import hiring.userRegistration.service.UserRegistrationService;
import hiring.userRegistration.security.JWTTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hiring.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRegistrationRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserRegistrationServiceImpl(UserRegistrationRepository userRepository,
                                       PasswordEncoder passwordEncoder,
                                       JWTTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

    public UserRegistrationResponse loginUser(UserRegistrationRequest loginRequest) {
        // Find user by username
        Optional<UserEntity> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        // If user is found and password matches
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Generate JWT token
                String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());

                // Create and return the response
                UserRegistrationResponse response = new UserRegistrationResponse();
                response.setId(user.getId());
                response.setUsername(user.getUsername());
                response.setEmail(user.getEmail());
                response.setName(user.getName());
                response.setRole(user.getRole());
                response.setStatus("Success");
                response.setToken(token);

                return response;
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public UserRegistrationResponse getUserDetails (Long userId){
            Optional<UserEntity> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();

                UserRegistrationResponse response = new UserRegistrationResponse();
                response.setId(user.getId());
                response.setUsername(user.getUsername());
                response.setEmail(user.getEmail());
                response.setName(user.getName());
                response.setRole(user.getRole());
//                if (!optionalUser.isEmpty(user.getResume())) {
//                    response.setResume(user.getResume());
//                }
                return response;
            } else {
                throw new UserNotFoundException("User with ID " + userId + " not found");
            }
    }

    public void updateUserRole(Long userId, String role) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }

    public void deleteUser(Long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }

    public List<UserRegistrationResponse> getAllUsers(String role) {
        List<UserEntity> users;
        if (role != null && !role.isEmpty()) {
            users = userRepository.findByRole(role);
        } else {
            users = userRepository.findAll();
        }
        return users.stream().map(user -> new UserRegistrationResponse(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getName(), user.getRole(), user.getResume())).collect(Collectors.toList());
    }

    public void changeUserPassword(Long userId, String oldPassword, String newPassword) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Old password is incorrect");
            }
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }

}
