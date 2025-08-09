package com.example.mynotebook.User;

import com.example.mynotebook.User.DTO.LoginRequest;
import com.example.mynotebook.User.DTO.RegisterRequest;
import com.example.mynotebook.User.DTO.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Auth", description = "user related")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "login", description = "use email and password to login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request == null || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            return ResponseEntity.badRequest().body("email 和 password 必填");
        }

        Optional<UserInfoEntity> userOpt = userService.login(request.getEmail().trim(), request.getPassword());
        if (userOpt.isPresent()) {
            UserInfoEntity user = userOpt.get();
            return ResponseEntity.ok(new UserResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getPicture()
            ));
        } else {
            return ResponseEntity.status(401).body("wrong email or password");
        }
    }
    @Operation(summary = "register", description = " ")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        String email = req.getEmail().trim();
        if (userService.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("this email has already been used");
        }
        UserInfoEntity user = userService.register(email, req.getPassword(), req.getUserName(), req.getPicture());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(
                user.getId(), user.getUserName(), user.getEmail(), user.getPicture()
        ));
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

}
