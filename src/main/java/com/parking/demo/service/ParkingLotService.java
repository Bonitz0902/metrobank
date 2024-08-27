package com.parking.demo.service;

import com.parking.demo.DTO.ParkingLotDto;
import com.parking.demo.entity.Lot;
import com.parking.demo.entity.ParkingLot;
import com.parking.demo.entity.Vehicle;
import com.parking.demo.repository.LotRepository;
import com.parking.demo.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ParkingLotService {


    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Autowired
    LotRepository lotRepository;

    public List<Lot> buildListOfLot(ParkingLotDto parkingLotDto) {
        return IntStream.range(0, parkingLotDto.getNumberOfSlot())
                .mapToObj(i -> {
                    Lot lot = Lot.builder()
                            .lotId(UUID.randomUUID().toString())
                            .lotLocation(parkingLotDto.getLocation())
                            .isAvailable(true)
                            .build();

                    lotRepository.save(lot);
                    return lot;
                }).collect(Collectors.toList());

    }

    public boolean checkIfLocationExist(ParkingLot parkingLot) {
        return parkingLot != null;
    }

    public Integer getTheNumberOfSlot(ParkingLot parkingLot) {
        return parkingLot.getLotList().stream()
                .filter(lot -> lot.isAvailable())
                .mapToInt(lot -> 1)
                .sum();
    }

    public boolean checkIfVehicleIsParked(ParkingLot parkingLot, Vehicle vehicle) {
        String vehicleDetails = vehicle.getLicensePlate();
        List<Lot> lotList = lotRepository.findByLotLocation(parkingLot.getLocation());

        return lotList.stream()
                .anyMatch(lot -> vehicleDetails.equals(lot.getParkedCarDetail()));
    }

    public ParkingLot findParkingLotByLocation(String location) {
        return parkingLotRepository.findByLocation(location);
    }

    public String cleanLisencePlateString(String licensePlate) {
        return licensePlate.replaceAll("[^a-zA-Z0-9-]", "");
    }

    ;

    public String validVehicleType(String type) {
        List<String> validVehicleType = List.of("Car", "Motorcycle", "Truck");

        return validVehicleType.stream()
                .filter(validVehicle -> validVehicle.equals(type))
                .findFirst()
                .orElse(null);
    }

    public String cleanOwnerName(String ownerName) {
        return ownerName.replaceAll("[^a-zA-Z ]", "");
    }
}
