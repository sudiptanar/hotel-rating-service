package com.sn.user.service.services.impl;

import com.sn.user.service.entities.Hotel;
import com.sn.user.service.entities.Rating;
import com.sn.user.service.entities.User;
import com.sn.user.service.exceptions.ResourceNotFoundException;
import com.sn.user.service.external.services.HotelService;
import com.sn.user.service.repositories.UserRepository;
import com.sn.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID is not found in server !! :" + userId));
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        logger.info("{}", ratings);

        List<Rating> ratingList = ratings.stream().peek(rating -> {
//            ResponseEntity<Hotel> entity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            rating.setHotel(entity.getBody());

            Hotel hotel = hotelService.getHotelById(rating.getHotelId()).getBody();
            rating.setHotel(hotel);

        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
