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
@RequestMapping("statistics")
public class StatisticsController {
    @Autowired
    private ParkingInfoService parkingInfoService;

    private final DateTimeFormatter YearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("income")
    public String toIncomingStatistics() {
        return "admin/statistics/income";
    }

    @PostMapping("income")
    public String showIncomeStatistics(@RequestParam("startTime") String startTimeString,
                                       @RequestParam("endTime") String endTimeString,
                                       @RequestParam("type") String type,
                                       Model msg) {
        List<BigDecimal> result;
        List<String> xSeries;
        if (type.equals("month")) {
            var startYearMonth = YearMonth.parse(startTimeString, YearMonthFormatter);
            var endYearMonth = YearMonth.parse(endTimeString, YearMonthFormatter);
            result = parkingInfoService.getIncomeByMonth(startYearMonth, endYearMonth);
            xSeries = getYearMonthList(startYearMonth, endYearMonth);
        } else {
            var startDate = LocalDate.parse(startTimeString, dateFormatter);
            var endDate = LocalDate.parse(endTimeString, dateFormatter);
            result = parkingInfoService.getIncomeByDay(startDate, endDate);
            xSeries = getDateList(startDate, endDate);
        }
        msg.addAttribute("incomeList", result);
        msg.addAttribute("xSeries", xSeries);
        return "admin/statistics/income";
    }


    @GetMapping("number")
    public String toNumberStatistics() {
        return "admin/statistics/number";
    }

    @PostMapping("number")
    public String showNumberStatistics(@RequestParam("startTime") String startTimeString,
                                       @RequestParam("endTime") String endTimeString,
                                       @RequestParam("type") String type,
                                       Model msg) {
        List<Integer> result;
        List<String> xSeries;
        if (type.equals("month")) {

            var startYearMonth = YearMonth.parse(startTimeString, YearMonthFormatter);
            var endYearMonth = YearMonth.parse(endTimeString, YearMonthFormatter);
            result = parkingInfoService.getParkingNumberByMonth(startYearMonth, endYearMonth);
            xSeries = getYearMonthList(startYearMonth, endYearMonth);
        } else {
            var startDate = LocalDate.parse(startTimeString, dateFormatter);
            var endDate = LocalDate.parse(endTimeString, dateFormatter);
            result = parkingInfoService.getParkingNumberByDay(startDate, endDate);
            xSeries = getDateList(startDate, endDate);
        }
        msg.addAttribute("numberList", result);
        msg.addAttribute("xSeries", xSeries);
        return "admin/statistics/number";
    }


    private List<String> getYearMonthList(YearMonth start, YearMonth end) {
        List<String> xSeries = new ArrayList<>();
        for (var i = start; !i.isAfter(end); i = i.plusMonths(1)) {
            xSeries.add(i.format(YearMonthFormatter));
        }
        return xSeries;
    }

    private List<String> getDateList(LocalDate start, LocalDate end) {
        List<String> xSeries = new ArrayList<>();
        for (var i = start; !i.isAfter(end); i = i.plusDays(1)) {
            xSeries.add(i.format(dateFormatter));
        }
        return xSeries;
    }
}
