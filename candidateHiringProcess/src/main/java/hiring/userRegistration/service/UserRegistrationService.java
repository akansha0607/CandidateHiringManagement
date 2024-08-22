package hiring.userRegistration.service;

import hiring.userRegistration.request.UserRegistrationRequest;
import hiring.userRegistration.response.UserRegistrationResponse;

public interface UserRegistrationService {
    UserRegistrationResponse registerUser(UserRegistrationRequest request);
}
