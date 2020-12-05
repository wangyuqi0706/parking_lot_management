package org.group3.parking.service.impl;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.model.ParkingLotConfig;
import org.group3.parking.repository.ParkingInfoRepository;
import org.group3.parking.service.ConfigService;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ParkingInfoServiceImpl implements ParkingInfoService {
    @Autowired
    ParkingInfoRepository parkingInfoRepository;
    @Autowired
    VipInfoService vipInfoService;
    @Autowired
    ConfigService configService;

    @Override
    public List<ParkingInfo> getAllParkingInfo() {
        return parkingInfoRepository.findAll();
    }

    @Override
    public void updateParkingInfo(Long id, ParkingInfo parkingInfo) throws Exception {
        parkingInfo.setParkingId(id);
        this.parkingInfoRepository.saveAndFlush(parkingInfo);
    }

    @Override
    public void deleteParkingInfo(Long id) throws Exception {
        this.parkingInfoRepository.deleteById(id);
    }

    @Override
    public void insertParkingInfo(ParkingInfo parkingInfo) throws Exception {
        parkingInfoRepository.saveAndFlush(parkingInfo);
    }

    @Override
    public ParkingInfo getParkingInfoById(Long id) throws Exception {

        Optional<ParkingInfo> parkingInfo = this.parkingInfoRepository.findById(id);
        if (parkingInfo.isPresent())
            return parkingInfo.get();
        else
            throw new Exception("Can not find this parking_info by the id:" + id);
    }


    @Override
    public void enterParkingLot(String plateNumber) throws Exception {
        //检查是否符合进入的信息条件,再插入数据项
        if (hasEntered(plateNumber))
            throw new Exception("This car has entered!");
        ParkingInfo parkingInfo = new ParkingInfo();
        parkingInfo.setPlateNumber(plateNumber);
        parkingInfo.setEnterTime(LocalDateTime.now());
        parkingInfoRepository.save(parkingInfo);
    }

    @Override
    public void leaveParkingLot(String plateNumber) throws Exception {
        //检查是否符合离开的信息条件，计算应缴金额，再更新数据库
        if (!hasEntered(plateNumber))
            throw new Exception("This car has not entered!");
        Optional<ParkingInfo> parkingInfo = parkingInfoRepository.findByPlateNumberAndLeaveTimeIsNull(plateNumber);
        if (parkingInfo.isPresent()) {
            parkingInfo.get().setLeaveTime(LocalDateTime.now());
            BigDecimal amount = calculateAmount(parkingInfo.get());
            parkingInfo.get().setAmountPayable(amount);
            // TODO: 2020/12/1 还需要返回应缴金额
        }

    }

    @Override
    public void payForLeave(String plateNumber, BigDecimal amount) throws Exception {
        //检查金额是否正确
    }


    /**
     * 按月统计收入
     *
     * @param startYearMonth 初始月份
     * @param endYearMonth   终止月份
     * @return 从初始月到终止月，每个月的收入
     */
    @Override
    public List<BigDecimal> getIncomeByMonth(YearMonth startYearMonth, YearMonth endYearMonth) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        List<BigDecimal> result = new ArrayList<>();
        for (var i = startYearMonth; !i.isAfter(endYearMonth); i = i.plusMonths(1)) {
            startDateTime = i.atDay(1).atTime(LocalTime.MIN);
            endDateTime = i.atEndOfMonth().atTime(LocalTime.MAX);
            BigDecimal monthIncome = parkingInfoRepository.getTotalIncomingBetween(startDateTime, endDateTime);
            result.add(Objects.requireNonNullElse(monthIncome, BigDecimal.ZERO));
        }
        return result;
    }

    /**
     * 按日统计收入（根据当日离开）
     *
     * @param startDate 初始日期
     * @param endDate   终止日期
     * @return 从初始日期到终止日期，每一天的收入
     */
    @Override
    public List<BigDecimal> getIncomeByDay(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        List<BigDecimal> result = new ArrayList<>();
        for (var i = startDate; !i.isAfter(endDate); i = i.plusDays(1)) {
            startDateTime = i.atTime(LocalTime.MIN);
            endDateTime = i.atTime(LocalTime.MAX);
            BigDecimal dayIncome = parkingInfoRepository.getTotalIncomingBetween(startDateTime, endDateTime);
            result.add(Objects.requireNonNullElse(dayIncome, BigDecimal.ZERO));
        }
        return result;
    }

    /**
     * 按月统计停车量（根据进入时间）
     *
     * @param startYearMonth 初始月份
     * @param endYearMonth   终止日期
     * @return 从初始月到终止月，每个月的停车量
     */
    @Override
    public List<Integer> getParkingNumberByMonth(YearMonth startYearMonth, YearMonth endYearMonth) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        List<Integer> result = new ArrayList<>();
        for (var i = startYearMonth; !i.isAfter(endYearMonth); i = i.plusMonths(1)) {
            startDateTime = i.atDay(1).atTime(LocalTime.MIN);
            endDateTime = i.atEndOfMonth().atTime(LocalTime.MAX);
            Integer monthParkingNumber = parkingInfoRepository.getTotalNumberBetween(startDateTime, endDateTime);
            result.add(Objects.requireNonNullElse(monthParkingNumber, 0));
        }
        return result;
    }

    /**
     * 按日统计停车量
     *
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @return 从起始日期到终止日期期间，每一天的收入
     */
    @Override
    public List<Integer> getParkingNumberByDay(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        List<Integer> result = new ArrayList<>();
        for (var i = startDate; !i.isAfter(endDate); i = i.plusDays(1)) {
            startDateTime = i.atTime(LocalTime.MIN);
            endDateTime = i.atTime(LocalTime.MAX);
            Integer dayParkingNumber = parkingInfoRepository.getTotalNumberBetween(startDateTime, endDateTime);
            result.add(Objects.requireNonNullElse(dayParkingNumber, 0));
        }
        return result;
    }

    private boolean hasEntered(String plateNumber) {
        Optional<ParkingInfo> parkingInfo = parkingInfoRepository.findByPlateNumberAndLeaveTimeIsNull(plateNumber);
        return parkingInfo.isPresent();
    }

    private BigDecimal calculateAmount(ParkingInfo parkingInfo) {
        //计算金额
        LocalDateTime enterTime = parkingInfo.getEnterTime();
        LocalDateTime leaveTime = parkingInfo.getLeaveTime();
        BigDecimal amount;
        Duration duration = Duration.between(enterTime, leaveTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        if (minutes >= 30)
            hours++;

        ParkingLotConfig parkingLotConfig = configService.getParkingLotConfig();
        amount = parkingLotConfig.getUnitPrice().multiply(BigDecimal.valueOf(hours));
        if (vipInfoService.isVip(parkingInfo.getPlateNumber())) {
            amount = amount.multiply(parkingLotConfig.getDiscount());
        }
        return amount;
    }


}
