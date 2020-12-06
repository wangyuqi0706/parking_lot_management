package org.group3.parking.controller;

import org.group3.parking.service.ParkingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    ParkingInfoService parkingInfoService;

    @PostMapping("/enter")
    public String enter(@RequestParam("plateNumber") String plateNumber,
                        @RequestParam("enterTime") LocalDateTime enterTime) {

        try {
            parkingInfoService.enterParkingLot(plateNumber, enterTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }

    @PostMapping("/leave")
    public String leave(@RequestParam("plateNumber") String plateNumber,
                        @RequestParam("leaveTime") LocalDateTime leaveTime) {

        try {
            parkingInfoService.leaveParkingLot(plateNumber, leaveTime);
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
