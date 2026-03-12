package com.example.auth.controller;

  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;

  import com.example.auth.dto.AuthResponse;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.service.AuthService;

  @RestController
  @RequestMapping("/api/auth")
  public class AuthController {

      @Autowired
      private AuthService authService;

      @PostMapping("/register")
      public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
          try {
              String result = authService.register(request);
              return ResponseEntity.ok(result);
          } catch (Exception e) {
              return ResponseEntity.badRequest().body(e.getMessage());
          }
      }

      @PostMapping("/login")
      public ResponseEntity<?> login(@RequestBody LoginRequest request) {
          try {
              String token = authService.login(request);
              return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
          } catch (Exception e) {
              return ResponseEntity.badRequest().body("Invalid username or password");
          }
      }
  }