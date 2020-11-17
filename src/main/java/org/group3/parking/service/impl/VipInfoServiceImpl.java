package org.group3.parking.service.impl;

import org.group3.parking.model.VipInfo;
import org.group3.parking.repository.VipInfoRepository;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipInfoServiceImpl implements VipInfoService {
    @Autowired
    VipInfoRepository vipInfoRepository;

    @Override
    public List<VipInfo> getAllVipInfo() {
        return this.vipInfoRepository.findAll();
    }

    @Override
    public VipInfo getVipInfoByPlateNumber(String plateNumber) throws Exception {
        return this.vipInfoRepository.getOne(plateNumber);
    }

    @Override
    public void updateVipInfoByPlateNumber(String plateNumber, VipInfo vipInfo) throws Exception {
        //如果修改了主键，需要删除旧的信息
        if (!plateNumber.equals(vipInfo.getPlateNumber())) {
            this.vipInfoRepository.deleteById(plateNumber);
        }
        this.vipInfoRepository.save(vipInfo);
    }

    @Override
    public void createVipInfo(VipInfo vipInfo) throws Exception {
        this.vipInfoRepository.saveAndFlush(vipInfo);
    }
}
