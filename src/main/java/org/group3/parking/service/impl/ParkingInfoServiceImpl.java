package org.group3.parking.service.impl;

import org.group3.parking.config.ParkingLotConfig;
import org.group3.parking.model.ParkingInfo;
import org.group3.parking.repository.ParkingInfoRepository;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingInfoServiceImpl implements ParkingInfoService {
    @Autowired
    ParkingInfoRepository parkingInfoRepository;
    @Autowired
    VipInfoService vipInfoService;
    @Autowired
    ParkingLotConfig parkingLotConfig;

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
        return this.parkingInfoRepository.findById(id).get();
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
        amount = parkingLotConfig.getUnitPrice().multiply(BigDecimal.valueOf(hours));
        if (vipInfoService.isVip(parkingInfo.getPlateNumber())) {
            amount = amount.multiply(parkingLotConfig.getDiscount());
        }
        return amount;
    }
}
