package org.group3.parking.service.impl;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.repository.ParkingInfoRepository;
import org.group3.parking.service.ParkingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingInfoServiceImpl implements ParkingInfoService {
    @Autowired
    ParkingInfoRepository parkingInfoRepository;
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
}
