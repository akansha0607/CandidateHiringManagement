package hiring.userRegistration.service;

import hiring.userRegistration.model.UserEntity;
import hiring.userRegistration.request.UserRegistrationRequest;
import hiring.userRegistration.response.UserRegistrationResponse;

import java.util.List;

public interface UserRegistrationService {
    UserRegistrationResponse registerUser(UserRegistrationRequest request);

//    UserRegistrationResponse loginUser(UserRegistrationRequest loginRequest);

    UserRegistrationResponse getUserDetails(Long userId);

    void updateUserRole(Long userId, String role);

    void deleteUser(Long userId);

    List<UserRegistrationResponse> getAllUsers(String role);

    void changeUserPassword(Long userId, String oldPassword, String newPassword);

    String verify(UserEntity user);
}
