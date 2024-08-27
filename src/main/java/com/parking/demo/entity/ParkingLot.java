package com.parking.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLot {

    @Id
    @Column(length = 50, unique = true, nullable = false)
    String parkingLotId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Lot> lotList;
    @Column(name = "LOCATION")
    String location;
    @Column(name = "COST_PER_MINUTE")
    Double costPerMinute;

}
