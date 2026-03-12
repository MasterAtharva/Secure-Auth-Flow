package com.example.auth.controller;

  import java.util.HashMap;
  import java.util.Map;

  import org.springframework.http.ResponseEntity;
  import org.springframework.security.core.Authentication;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;

  @RestController
  @RequestMapping("/api/user")
  public class UserController {

      @GetMapping("/profile")
      public ResponseEntity<?> getUserProfile(Authentication authentication) {
          Map<String, Object> profile = new HashMap<>();
          profile.put("username", authentication.getName());
          profile.put("authorities", authentication.getAuthorities());
          profile.put("message", "Welcome to the User Profile!");
          return ResponseEntity.ok(profile);
      }
  }