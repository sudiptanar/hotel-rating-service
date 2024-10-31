package com.sn.rating.service.services;

import com.sn.rating.service.entities.Rating;

import java.util.List;


public interface RatingService {

    Rating createRating(Rating rating);
    List<Rating> getAllRatings();
    List<Rating> getAllRatingsByUserId(String userId);
    List<Rating> getAllRatingsByHotelId(String hotelId);
    Rating getRatingById(String ratingId);
}
