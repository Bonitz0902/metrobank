package com.parking.demo.repository;

import com.parking.demo.entity.Lot;
import com.parking.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository  extends JpaRepository<Lot, String> {
    Lot findByParkedCarDetail(String vehicleDetails);
    List<Lot> findByLotLocation(String location);
    List<Lot> findByIsAvailableFalse();
}
