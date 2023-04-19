package com.mshoes.mshoes.controllers;

import com.mshoes.mshoes.models.response.JWTAuthResponse;
import com.mshoes.mshoes.models.requested.LoginRequest;
import com.mshoes.mshoes.models.requested.SignupRequest;
import com.mshoes.mshoes.models.dtos.UserDTO;
import com.mshoes.mshoes.securities.JwtConfig;
import com.mshoes.mshoes.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtConfig tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        //set JWT in cookie
        addAuthentication(response, token);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
    private void addAuthentication(HttpServletResponse response, String jwtToken) {
        // Set cookie value
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){

        UserDTO newUser = userService.signupUser(signupRequest);
        if (newUser != null) {
            return new ResponseEntity<>("Sign up successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sign up fail!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Đăng xuất thành công");
    }
}
