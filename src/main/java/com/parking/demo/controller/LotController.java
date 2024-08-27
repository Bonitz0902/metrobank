package com.parking.demo.controller;

import com.parking.demo.DTO.VehicleDto;
import com.parking.demo.entity.Lot;
import com.parking.demo.repository.LotRepository;
import com.parking.demo.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/lot")
public class LotController {

    @Autowired
    LotRepository lotRepository;
    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping("/checkVehicle")
    public String findAllLots(@RequestBody VehicleDto vehicleDto) {
        List<Lot> lotList = lotRepository.findAll();

        Optional<Lot> parkedLot = lotList.stream()
                .filter(lot -> parkingLotService.cleanLisencePlateString(vehicleDto.getLicensePlate()).equals(lot.getParkedCarDetail()))
                .findFirst();

        if(parkedLot.isPresent()){
            Lot lot = parkedLot.get();
            return "The vehicle is parked in: " + lot.getLotLocation();
        }

        return "The vehicle doesn't exist";
    }

    @PostMapping("calculateCost")
    public String calculateCostOfParking(@RequestBody VehicleDto vehicleDto) {
        Date endDate = new Date();
        Lot parkedLot = lotRepository.findByParkedCarDetail(parkingLotService.cleanLisencePlateString(vehicleDto.getLicensePlate()));

        if(parkedLot == null){
            return "The vehicle doesn't exist";
        }
        Double parkingLotCostPerMinute = parkingLotService.findParkingLotByLocation(parkedLot.getLotLocation()).getCostPerMinute();
        long diffInMillis = endDate.getTime() - parkedLot.getParkedTime().getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);

        Double costOfParking = diffInMinutes * parkingLotCostPerMinute;

        return "Total cost of parking is :" + costOfParking;
    }

}
