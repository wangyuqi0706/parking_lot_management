package org.group3.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.group3.parking.model.ParkingInfo;
import org.group3.parking.service.ParkingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    ParkingInfoService parkingInfoService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    @PostMapping("enter")
    public ResponseEntity<String> enter(@RequestParam("plateNumber") String plateNumber,
                                @RequestParam("enterTime") String enterTime) {

        try {
            parkingInfoService.enterParkingLot(plateNumber, LocalDateTime.parse(enterTime, formatter));
        } catch (ParkingInfoService.HasEnteredException e) {
            String message = e.getMessage();
            System.out.println(message);
            return new ResponseEntity<String>("The car has entered", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @RequestMapping(value = "leave",method = RequestMethod.POST)
    public ParkingInfo leave(@RequestParam("plateNumber") String plateNumber,
                        @RequestParam("leaveTime") String leaveTime) {

        try {
            return parkingInfoService.leaveParkingLot(plateNumber, LocalDateTime.parse(leaveTime, formatter));
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
            return null;
        }
    }

}
