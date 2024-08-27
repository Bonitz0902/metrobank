package com.parking.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Vehicle {
    @Id
    @Column(length = 50, unique = true, nullable = false)
    String licensePlate;
    String type;
    String ownerName;
}
