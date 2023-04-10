package com.mshoes.mshoes.controllers;

import com.mshoes.mshoes.models.dtos.RequestedLogin;
import com.mshoes.mshoes.models.dtos.RequestedSignup;
import com.mshoes.mshoes.models.dtos.UserDTO;
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

    public AuthController(AuthenticationManager authenticationManager,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody RequestedLogin requestedLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestedLogin.getUsername(),requestedLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  new ResponseEntity<>("User login successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody RequestedSignup requestedSignup){
        UserDTO newUser = userService.signupUser(requestedSignup);
        if(newUser == null){
            return new ResponseEntity<>("Sign up fail!",HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>("Sign up successfully!",HttpStatus.OK);
        }
    }

}
