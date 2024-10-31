package com.sn.user.service.controllers;

import com.sn.user.service.entities.User;
import com.sn.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSinglerUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("Fallback is executed because service is down: " + ex.getMessage());
        User dummyUser = User.builder()
                .email("abc@gmail.com")
                .name("Dummy name")
                .about("Dummy user created because of server down")
                .userId("1234").build();

        return new ResponseEntity<>(dummyUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
