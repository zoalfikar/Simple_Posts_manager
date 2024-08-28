package simple_posts_manager.controllers;
import simple_posts_manager.models.entities.User;
import simple_posts_manager.app.requests.LoginUserRequest;
import simple_posts_manager.app.http.requests.UserRequest;
import simple_posts_manager.app.http.responses.LoginResponse;
import simple_posts_manager.app.services.LoginRegsterationService;
import simple_posts_manager.app.http.services.JwtService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class LoginRegistrationController {
    private final JwtService jwtService;
    
    private final LoginRegsterationService loginRegsterationService;

    public LoginRegistrationController(JwtService jwtService, LoginRegsterationService loginRegsterationService) {
        this.jwtService = jwtService;
        this.loginRegsterationService = loginRegsterationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest userRequest) {
        User registeredUser = loginRegsterationService.signup(userRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        User authenticatedUser = loginRegsterationService.authenticate(loginUserRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}