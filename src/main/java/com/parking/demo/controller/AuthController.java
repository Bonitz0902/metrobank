//package com.parking.demo.controller;
//
//import com.parking.demo.component.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthController {
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    @PostMapping("/authenticate")
//    public String generateToken(@RequestParam String username, @RequestParam String password) {
//        if ("user".equals(username) && "password".equals(password)) {
//            return jwtUtil.generateToken(username);
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//}
