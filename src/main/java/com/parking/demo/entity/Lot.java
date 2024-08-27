package com.parking.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lot {

    @Id
    @Column(length = 50, unique = true, nullable = false)
    String lotId;
    @Column(nullable = false)
    boolean isAvailable;
    String parkedCarDetail;
    String lotLocation;
    Date parkedTime;

}
