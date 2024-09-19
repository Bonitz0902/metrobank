package com.parking.demo.component;

import com.parking.demo.entity.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ParkingLotTimeChecker {
    @Autowired
    LotRepository lotRepository;

    @Scheduled(fixedRate = 10000)
    public void checkTime(){
        Date currentDate = new Date();

        List<Lot> lotList = lotRepository.findByIsAvailableFalse();

        if(lotList.size() == 0) System.out.println("Empty");

        for(Lot lot : lotList){
            long diffInMillis = currentDate.getTime() - lot.getParkedTime().getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);

            if(diffInMinutes > 15){
                lot.setParkedTime(null);
                lot.setAvailable(true);
                lot.setParkedCarDetail(null);

                lotRepository.saveAndFlush(lot);
            }
        }
    }
}
