package org.group3.parking.service.impl;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.repository.ParkingInfoRepository;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ParkingInfoServiceImpl implements ParkingInfoService {
    @Autowired
    ParkingInfoRepository parkingInfoRepository;
    @Autowired
    VipInfoService vipInfoService;

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
    public void enterParkingLot(ParkingInfo parkingInfo) throws Exception {
        //检查是否符合进入的信息条件,再插入数据项

    }

    @Override
    public void leaveParkingLot(ParkingInfo parkingInfo) throws Exception {
        //检查是否符合离开的信息条件，计算应缴金额，再更新数据库
    }

    @Override
    public void payForLeave(String plateNumber, BigDecimal amount) throws Exception {
        //检查金额是否正确
    }
}
