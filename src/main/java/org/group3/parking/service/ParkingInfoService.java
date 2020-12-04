package org.group3.parking.service;

import org.group3.parking.model.ParkingInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public interface ParkingInfoService {
    List<ParkingInfo> getAllParkingInfo();

    void updateParkingInfo(Long id, ParkingInfo parkingInfo) throws Exception;

    void deleteParkingInfo(Long id) throws Exception;

    void insertParkingInfo(ParkingInfo parkingInfo) throws Exception;

    ParkingInfo getParkingInfoById(Long id) throws Exception;

    void enterParkingLot(String plateNumber) throws Exception;

    void leaveParkingLot(String plateNumber) throws Exception;

    void payForLeave(String plateNumber, BigDecimal amount) throws Exception;

    BigDecimal getIncomeSumBetweenTwoTime(LocalDateTime startTime, LocalDateTime endTime);

    List<BigDecimal> getIncomeByMonth(YearMonth startYearMonth,YearMonth endYearMonth);

    List<BigDecimal> getIncomeByDay(LocalDate startDate, LocalDate endDate);


}
