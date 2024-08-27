package com.parking.demo.controller;

import com.parking.demo.DTO.ParkingLotDto;
import com.parking.demo.DTO.VehicleDto;
import com.parking.demo.entity.Lot;
import com.parking.demo.entity.ParkingLot;
import com.parking.demo.entity.Vehicle;
import com.parking.demo.repository.LotRepository;
import com.parking.demo.repository.ParkingLotRepository;
import com.parking.demo.service.LotService;
import com.parking.demo.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/parkingLot")
public class ParkingLotController {
    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Autowired
    ParkingLotService parkingLotService;
    @Autowired
    LotRepository lotRepository;
    @Autowired
    LotService lotService;

    @PostMapping("/create")
    public String createParkingLot(@RequestBody ParkingLotDto parkingLotDto) {
        ParkingLot parkingLot = ParkingLot.builder()
                .parkingLotId(UUID.randomUUID().toString())
                .location(parkingLotDto.getLocation())
                .costPerMinute(parkingLotDto.getCostPerMinute())
                .lotList(parkingLotService.buildListOfLot(parkingLotDto))
                .build();

        parkingLotRepository.save(parkingLot);
        return "Success";
    }

    @PostMapping("/all")
    public List<ParkingLot> getAllParkingLot() {
        return parkingLotRepository.findAll();
    }

    @PostMapping("/availableSlot")
    public String getAvailableLot(@RequestParam String location) {
        ParkingLot parkingLot = parkingLotService.findParkingLotByLocation(location);
        if (!parkingLotService.checkIfLocationExist(parkingLot))
            return location + "does not exist";


        return "Number of available slot in " + location + ": " + parkingLotService.getTheNumberOfSlot(parkingLot);
    }

    @PostMapping("/parkVehicle")
    public String parkVehicle(@RequestParam String location, @RequestBody VehicleDto vehicleDto) {
        ParkingLot parkingLot = parkingLotService.findParkingLotByLocation(location);
        Vehicle vehicle = Vehicle.builder()
                .licensePlate(parkingLotService.cleanLisencePlateString(vehicleDto.getLicensePlate()))
                .type(parkingLotService.validVehicleType(vehicleDto.getType()))
                .ownerName(parkingLotService.cleanOwnerName(vehicleDto.getOwnerName()))
                .build();

        if (!parkingLotService.checkIfLocationExist(parkingLot))
            return location + "does not exist";

        if (parkingLotService.getTheNumberOfSlot(parkingLot) == 0) {
            return "The parking is full";
        }

        if(parkingLotService.checkIfVehicleIsParked(parkingLot, vehicle)){
            return "The vehicle is already parked";
        }

        if(vehicle.getType() == null){
            return "Invalid Vehicle";
        }

        Optional<Lot> availableLot = parkingLot.getLotList().stream()
                .filter(lot -> lot.isAvailable() && lot.getLotLocation().equals(location))
                .findFirst();

        if(availableLot.isPresent()){
            Lot lot = availableLot.get();
            lot.setAvailable(false);
            lot.setParkedCarDetail(vehicle.getLicensePlate());
            lot.setParkedTime(new Date());
            lotRepository.save(lot);
            return vehicle.getLicensePlate() + " is parked in " + location;
        }else {
            return "No available slot in " + location;
        }
    }

    @PostMapping("/unparkVehicle")
    public String unparkVehicle(@RequestBody VehicleDto vehicleDto){
        Lot parkedLotCar = lotService.findByParkedCarDetail(parkingLotService.cleanLisencePlateString(vehicleDto.getLicensePlate()));

        if(parkedLotCar == null) return "Vehicle doesn't exist";

        parkedLotCar.setAvailable(true);
        parkedLotCar.setParkedCarDetail("");

        lotRepository.saveAndFlush(parkedLotCar);

        return "The vehicle has been unparked.";
    }
}
