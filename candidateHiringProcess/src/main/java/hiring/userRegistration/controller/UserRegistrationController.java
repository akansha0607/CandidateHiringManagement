package hiring.userRegistration.controller;

import hiring.exception.UserNotFoundException;
import hiring.userRegistration.request.ChangePasswordRequest;
import hiring.userRegistration.request.UserRegistrationRequest;
import hiring.userRegistration.response.UserRegistrationResponse;
import hiring.userRegistration.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hiring/user")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    // User Registration(User can be HR, Interviewer or Candidate)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            UserRegistrationResponse userResponse = userRegistrationService.registerUser(request);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute("_csrf");
//    }


//    @PostMapping("/login")
//    public ResponseEntity<UserRegistrationResponse> loginUser(@RequestBody UserRegistrationRequest loginRequest) {
//        UserRegistrationResponse userResponse = userRegistrationService.loginUser(loginRequest);
//        if (userResponse != null) {
//            return new ResponseEntity<>(userResponse, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRegistrationResponse> getUserDetails(@PathVariable Long userId) {
        UserRegistrationResponse userDetails = userRegistrationService.getUserDetails(userId);
        return ResponseEntity.ok(userDetails);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestBody UserRegistrationRequest updateRoleRequest) {
        userRegistrationService.updateUserRole(userId, updateRoleRequest.getRole());
        return ResponseEntity.ok("User role updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userRegistrationService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/all/users")
    public ResponseEntity<List<UserRegistrationResponse>> listAllUsers(@RequestParam(value = "role", required = false) String role) {
        List<UserRegistrationResponse> users = userRegistrationService.getAllUsers(role);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<String> changeUserPassword(@PathVariable Long userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        userRegistrationService.changeUserPassword(userId, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        return ResponseEntity.ok("Password updated successfully");
    }

}
