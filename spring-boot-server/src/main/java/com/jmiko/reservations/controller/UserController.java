package com.jmiko.reservations.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmiko.reservations.dto.UserDTO;
import com.jmiko.reservations.dto.VendorDTO;
import com.jmiko.reservations.helper.JWTHelper;
import com.jmiko.reservations.model.Role;
import com.jmiko.reservations.model.User;
import com.jmiko.reservations.service.UserService;
import com.jmiko.reservations.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.jmiko.reservations.constant.JWTUtil.AUTH_HEADER;
import static com.jmiko.reservations.constant.JWTUtil.SECRET;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserServiceImpl userServiceImpl;
    private final JWTHelper jwtHelper;
    private final UserService userService;


    public UserController(UserServiceImpl userServiceImpl, JWTHelper jwtHelper, UserService userService) {
        this.userServiceImpl = userServiceImpl;
        this.jwtHelper = jwtHelper;
        this.userService = userService;
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        log.debug("Received request to save user. User details: {}", user);
        String result = userServiceImpl.createUser(user);
        log.debug("User saved successfully. Result: {}", result);
        return result;
    }

    @GetMapping("/refresh-token")
    public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtRefreshToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
        if (jwtRefreshToken != null) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
            String email = decodedJWT.getSubject();
            User user = userService.loadUserByEmail(email);
            String jwtAccessToken = jwtHelper.generateAccessToken(user.getEmail(), user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList()));
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), jwtHelper.getTokensMap(jwtAccessToken, jwtRefreshToken));
        } else {
            throw new RuntimeException("Refresh token required");
        }
    }

    @GetMapping("/get-vendor")
    public ResponseEntity<VendorDTO> getVendorByUserLogin(@RequestParam String userLogin) {
        UserDTO vendorByLogin = userService.getVendorByLogin(userLogin);
        VendorDTO vendor = vendorByLogin.getVendor();

        if (Objects.isNull(vendor)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(vendor);
        }
    }
}
