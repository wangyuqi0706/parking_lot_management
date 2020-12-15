package org.group3.parking.service.impl;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.repository.ParkingInfoRepository;
import org.group3.parking.service.ConfigService;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void enterParkingLot(String plateNumber, LocalDateTime enterTime) throws HasEnteredException {
        //检查是否符合进入的信息条件,再插入数据项
        if (hasEntered(plateNumber))
            throw new HasEnteredException("Car " + plateNumber + " has entered!");
        ParkingInfo parkingInfo = new ParkingInfo();
        parkingInfo.setPlateNumber(plateNumber);
        parkingInfo.setEnterTime(enterTime);
        parkingInfoRepository.save(parkingInfo);
    }

    @Override
    public ParkingInfo leaveParkingLot(String plateNumber, LocalDateTime leaveTime) throws Exception {
        //检查是否符合离开的信息条件，计算应缴金额，再更新数据库
        Optional<ParkingInfo> parkingInfo = parkingInfoRepository.findByPlateNumberAndLeaveTimeIsNull(plateNumber);
        if (parkingInfo.isEmpty())
            throw new Exception("This car " + plateNumber + " has not entered!");
        if (leaveTime.isAfter(parkingInfo.get().getEnterTime())) {
            parkingInfo.get().setLeaveTime(leaveTime);
            BigDecimal amount = calculateAmount(parkingInfo.get());
            parkingInfo.get().setAmountPayable(amount);
            parkingInfoRepository.save(parkingInfo.get());
            return parkingInfo.get();
        } else
            throw new Exception("Leave time must be later than enter time!");
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

    /**
     * @param parkingInfo 输入具有进入时间，离开时间的ParkingInfo类型
     * @return 返回这次停车的应缴金额
     */
    @Override
    public BigDecimal calculateAmount(ParkingInfo parkingInfo) throws Exception {
        //计算金额
        if (parkingInfo == null || parkingInfo.getEnterTime() == null || parkingInfo.getLeaveTime() == null)
            throw new Exception("无效的参数：空指针");
        //进入、离开时间
        LocalDateTime enterTime = parkingInfo.getEnterTime();
        LocalDateTime leaveTime = parkingInfo.getLeaveTime();
        if (leaveTime.isBefore(enterTime))
            throw new Exception("离开时间不能早于进入时间!");
        //获取基础价格
        BigDecimal amount = configService.getParkingLotConfig().getBasePrice();
        //获取日间、夜间价格
        BigDecimal dayTimePrice = configService.getParkingLotConfig().getDayTimeUnitPrice();
        BigDecimal nightTimePrice = configService.getParkingLotConfig().getNightTimeUnitPrice();
        //获取时间间隔
        Duration duration = Duration.between(enterTime, leaveTime);
        //计算天数
        BigDecimal days = BigDecimal.valueOf(duration.toDays());
        //计算不满一天的小时数
        BigDecimal hours = BigDecimal.valueOf(duration.toHoursPart());
        //计算不满一小时的分钟数
        BigDecimal minutes = BigDecimal.valueOf(duration.toMinutesPart());
        //分钟数超过半小时，按照满一小时计算
        if (minutes.compareTo(BigDecimal.valueOf(30)) >= 0)
            hours = hours.add(BigDecimal.ONE);

        //计算整天部分的金额
        amount = amount.add(days.multiply(dayTimePrice.add(nightTimePrice).multiply(BigDecimal.valueOf(12))));

        //计算不足一天部分的金额
        int day = 0;
        int night = 0;
        for (var i = enterTime.getHour(); i < enterTime.getHour() + hours.intValue(); i++) {
            if ((i >= 9 && i < 21) || (i >= 9 + 24 && i < 21 + 24)) {
                amount = amount.add(dayTimePrice);
                day++;
            } else {
                amount = amount.add(nightTimePrice);
                night++;
            }
        }

        if (vipInfoService.isVip(parkingInfo.getPlateNumber())) {
            amount = amount.multiply(configService.getParkingLotConfig().getDiscount());
        }
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        return amount;
    }

    /**
     * 获取当前停车场内车辆
     *
     * @return 当前停车场内车辆数
     */
    @Override
    public Integer getCurrentNumber() {
        return parkingInfoRepository.countAllByLeaveTimeIsNull();
    }

}
