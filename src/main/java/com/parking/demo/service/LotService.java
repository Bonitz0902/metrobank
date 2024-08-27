package com.parking.demo.service;

import com.parking.demo.entity.Lot;
import com.parking.demo.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotService {

    @Autowired
    LotRepository lotRepository;

    public Lot findByParkedCarDetail(String vehicleDetails){
        return lotRepository.findByParkedCarDetail(vehicleDetails);
    }
}
