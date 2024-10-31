package com.sn.rating.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @Column(name = "ID")
    private String ratingId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "HOTEL_ID")
    private String hotelId;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "FEEDBACK")
    private String feedback;
}
