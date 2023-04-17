package com.mshoes.mshoes.controllers;

import com.mshoes.mshoes.models.response.JWTAuthResponse;
import com.mshoes.mshoes.models.requested.RequestedLogin;
import com.mshoes.mshoes.models.requested.RequestedSignup;
import com.mshoes.mshoes.models.dtos.UserDTO;
import com.mshoes.mshoes.securities.JwtTokenProvider;
import com.mshoes.mshoes.services.UserService;
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
    private JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody RequestedLogin requestedLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestedLogin.getUsername(),requestedLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody RequestedSignup requestedSignup){

        UserDTO newUser = userService.signupUser(requestedSignup);
        if (newUser != null) {
            return new ResponseEntity<>("Sign up successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sign up fail!", HttpStatus.BAD_REQUEST);
        }
    }

}
