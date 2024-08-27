package com.parking.demo.DTO;

import lombok.Data;
import java.util.List;

@Data
public class ParkingLotDto {
    private String parkingLotId;
    private String location;
    private int numberOfSlot;
    private Double costPerMinute;


}
