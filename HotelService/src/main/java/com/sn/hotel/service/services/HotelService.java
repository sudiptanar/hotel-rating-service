package com.sn.hotel.service.services;

import com.sn.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAllHotels();

    Hotel getHotelById(String hotelId);
}
