package org.group3.parking.controller;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.service.ParkingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    ParkingInfoService parkingInfoService;

    @PostMapping("/enter")
    public String enter(ParkingInfo parkingInfo) {
        try {
            parkingInfoService.enterParkingLot(parkingInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/leave")
    public String leave(ParkingInfo parkingInfo) {
        try {
            parkingInfoService.leaveParkingLot(parkingInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/pay")
    public String pay(String plateNumber, BigDecimal amount) {
        try {
            this.parkingInfoService.payForLeave(plateNumber, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
