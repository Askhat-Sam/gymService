package dev.gymService.controller;

import dev.gymService.model.Trainer;
import dev.gymService.model.User;
import dev.gymService.model.dto.UserLoginRequest;
import dev.gymService.security.BruteForceLoggingProtectorService;
import dev.gymService.security.JwtService;
import dev.gymService.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/gym-service/authentication")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BruteForceLoggingProtectorService bruteForceLoggingProtectorService;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtService jwtService,
                                    BruteForceLoggingProtectorService bruteForceLoggingProtectorService,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.bruteForceLoggingProtectorService = bruteForceLoggingProtectorService;
        this.userService = userService;
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("User logged out successfully.");
    }

    @PostMapping("/login")
    @Operation(summary = "Login user",
            description = "Logs in user using username and password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login credentials",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserLoginRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logs in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        String username = loginRequest.getUserName();

        if (bruteForceLoggingProtectorService.isBlocked(username)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("User is temporarily locked due to too many failed login attempts.");
        }

        try {
            // Authenticate
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
            );

            // Reset attempts on success
            bruteForceLoggingProtectorService.loginSucceeded(username);

            /// Get user + generate token
            User user = userService.findByUserName(username);

            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(Map.of(
                    "username", user.getUserName(),
                    "role", (user instanceof Trainer) ? "TRAINER" : "TRAINEE",
                    "token", token
            ));

        } catch (BadCredentialsException e) {
            bruteForceLoggingProtectorService.loginFailed(username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

    }
}
