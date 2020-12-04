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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/statistics/")
public class StatisticsController {
    @Autowired
    private ParkingInfoService parkingInfoService;

    @GetMapping("/income")
    public String toIncomingStatistics() {
        return "/admin/statistics/income";
    }

    @PostMapping("/income")
    public String showIncomeStatistics(@RequestParam("startTime") String startTimeString,
                                       @RequestParam("endTime") String endTimeString,
                                       @RequestParam("type") String type,
                                       Model msg) {
        List<BigDecimal> result;
        List<String> xSeries = new ArrayList<>();
        if (type.equals("month")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            var startYearMonth = YearMonth.parse(startTimeString, formatter);
            var endYearMonth = YearMonth.parse(endTimeString, formatter);
            result = parkingInfoService.getIncomeByMonth(startYearMonth, endYearMonth);
            for (var i = startYearMonth; !i.isAfter(endYearMonth); i = i.plusMonths(1)) {
                xSeries.add(i.format(formatter));
            }

        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var startDate = LocalDate.parse(startTimeString, formatter);
            var endDate = LocalDate.parse(endTimeString, formatter);
            result = parkingInfoService.getIncomeByDay(startDate, endDate);
            for (var i = startDate; !i.isAfter(endDate); i = i.plusDays(1)) {
                xSeries.add(i.format(formatter));
            }
        }
        msg.addAttribute("incomeList", result);
        msg.addAttribute("xSeries", xSeries);
        return "/admin/statistics/income";
    }
}
