package com.example.movie.movie.controller;

import com.example.movie.movie.controller.Dto.UserEntityDto;
import com.example.movie.movie.entity.RoleEntity;
import com.example.movie.movie.entity.UserEntity;
import com.example.movie.movie.repository.UserEntityRepository;
import com.example.movie.movie.service.EmailService;
import com.example.movie.movie.util.ERole;
import com.example.movie.movie.util.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.apache.pdfbox.cos.COSObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserEntityDto dto){

        Map<String, Object> response = new HashMap<>();


        Set<RoleEntity> roles  =dto.getRoles()
                .stream().map(role -> RoleEntity
                        .builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity
                .builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .image(dto.getImage())
                .password(passwordEncoder.encode(dto.getPassword()))
                .movies(dto.getMovies())
                .build();

        userEntityRepository.save(userEntity);
        response.put("message", "User register");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/send-recover")
    public ResponseEntity<?> recoverUser(@RequestParam String token){
        Map<String, Object> response = new HashMap<>();

        if (token.isBlank()){
            response.put("message", "Token is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        if (jwtUtils.isTokenValid(token)){
            response.put("data", new HashMap<>());
            response.put("message", "Endpoint in progress");
            return ResponseEntity.ok(response);
        }

        response.put("message", "TOKEN IS INVALID");
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/find")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email){
        Map<String, Object> response = new HashMap<>();

        if (email.isBlank()){
            response.put("message", "Field email empty");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> userEntityOptional =userEntityRepository.findByEmail(email);

        if (userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            Map<String, Object> user = new HashMap<>();
            String token = jwtUtils.generateAccessToken(userEntity.getUsername());
            user.put("email", userEntity.getEmail());
            user.put("token", token);

            response.put("data", user);
            response.put("token", token);

            emailService.sendEmail(
                    userEntity.getEmail(),
                    "Recover password",
                    " "
                            .concat(userEntity.getUsername())
                            .concat("URL FOR RECOVER PASSWORD")
                            .concat("http://localhost:8081/auth/send-recover?token=")
                            .concat(token)
            );

            return ResponseEntity.ok(response);
        }

        response.put("message", "Email "+email+" not found");
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

    }



}
