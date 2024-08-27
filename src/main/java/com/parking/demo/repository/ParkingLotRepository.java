package com.parking.demo.repository;

import com.parking.demo.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {

    ParkingLot findByLocation(String Location);
}
