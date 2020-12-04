package org.group3.parking.controller;

import org.group3.parking.service.ParkingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/statistics/")
public class StatisticsController {
    @Autowired
    private ParkingInfoService parkingInfoService;

    @GetMapping("/incoming")
    public String toIncomingStatistics() {
        return "income";
    }

    @PostMapping("/incoming")
    public String showIncomeStatisticsByDay(@RequestParam LocalDate startDay,
                                            @RequestParam LocalDate endDay,
                                            Model msg) {
        List<BigDecimal> result = new ArrayList<>();
        for (var i = startDay; !i.isAfter(endDay); i = i.plusDays(1)) {
            LocalDateTime startDateTime = LocalDateTime.of(startDay, LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(endDay,LocalTime.MAX);
            BigDecimal incomingOfADay = parkingInfoService.getIncomeSumBetweenTwoTime(startDateTime, endDateTime);
            result.add(incomingOfADay);
        }
        msg.addAttribute("income",result);
        msg.addAttribute("startDay",startDay);
        msg.addAttribute("endDay",endDay);
        return "/admin/statistics/income";
    }
}
