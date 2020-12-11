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

    void enterParkingLot(String plateNumber, LocalDateTime enterTime) throws Exception;

    ParkingInfo leaveParkingLot(String plateNumber,LocalDateTime enterTime) throws Exception;

    void payForLeave(String plateNumber, BigDecimal amount) throws Exception;

    List<BigDecimal> getIncomeByMonth(YearMonth startYearMonth, YearMonth endYearMonth);

    List<BigDecimal> getIncomeByDay(LocalDate startDate, LocalDate endDate);

    List<Integer> getParkingNumberByMonth(YearMonth startYearMonth, YearMonth endYearMonth);

    List<Integer> getParkingNumberByDay(LocalDate startDate, LocalDate endDate);

    class HasEnteredException extends Exception{
        public HasEnteredException(String message){
            super(message);
        }
    }
}
