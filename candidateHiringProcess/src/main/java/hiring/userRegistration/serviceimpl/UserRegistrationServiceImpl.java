package hiring.userRegistration.serviceimpl;

import hiring.userRegistration.model.UserEntity;
//import hiring.userRegistration.model.UserPrincipal;
import hiring.userRegistration.repository.UserRegistrationRepository;
import hiring.userRegistration.request.UserRegistrationRequest;
import hiring.userRegistration.response.UserRegistrationResponse;
import hiring.userRegistration.service.JWTService;
import hiring.userRegistration.service.UserRegistrationService;
//import hiring.userRegistration.security.JWTTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
//
    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private final UserRegistrationRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserRegistrationServiceImpl(UserRegistrationRepository userRepository) {
        this.userRepository = userRepository;
//        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest userDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
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


    public String verify(UserEntity user) {
        System.out.println("user" + user.toString());
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
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
        return users.stream().map(user -> new UserRegistrationResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), "Active", "")).collect(Collectors.toList());
    }

    public void changeUserPassword(Long userId, String oldPassword, String newPassword) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (encoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(encoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Old password is incorrect");
            }
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }


}
