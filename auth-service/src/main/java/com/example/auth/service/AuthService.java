package com.example.auth.service;

  import com.example.auth.dto.LoginRequest;
  import com.example.auth.dto.RegisterRequest;
  import com.example.auth.model.Role;
  import com.example.auth.model.User;
  import com.example.auth.repository.RoleRepository;
  import com.example.auth.repository.UserRepository;
  import com.example.auth.security.CustomUserDetailsService;
  import com.example.auth.security.JwtUtil;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import org.springframework.stereotype.Service;

  import java.util.HashSet;
  import java.util.Set;

  @Service
  public class AuthService {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private RoleRepository roleRepository;

      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private AuthenticationManager authenticationManager;

      @Autowired
      private CustomUserDetailsService userDetailsService;

      @Autowired
      private JwtUtil jwtUtil;

      public String register(RegisterRequest request) {
          if (userRepository.existsByUsername(request.getUsername())) {
              throw new RuntimeException("Username is already taken");
          }

          if (userRepository.existsByEmail(request.getEmail())) {
              throw new RuntimeException("Email is already taken");
          }

          User user = new User();
          user.setUsername(request.getUsername());
          user.setEmail(request.getEmail());
          user.setPassword(passwordEncoder.encode(request.getPassword()));

          Set<Role> roles = new HashSet<>();
          String requestRole = request.getRole();
          
          if (requestRole == null || requestRole.trim().isEmpty()) {
              Role userRole = roleRepository.findByRoleName("ROLE_USER")
                      .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
              roles.add(userRole);
          } else {
              String roleName = requestRole.startsWith("ROLE_") ? requestRole.toUpperCase() : "ROLE_" + requestRole.toUpperCase();
              Role mappedRole = roleRepository.findByRoleName(roleName)
                      .orElseGet(() -> roleRepository.save(new Role(roleName)));
              roles.add(mappedRole);
          }

          user.setRoles(roles);
          userRepository.save(user);

          return "User registered successfully!";
      }

      public String login(LoginRequest request) {
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
          );

          UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
          return jwtUtil.generateToken(userDetails);
      }
  }\n