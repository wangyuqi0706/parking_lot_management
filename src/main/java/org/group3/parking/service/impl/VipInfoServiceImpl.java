package org.group3.parking.service.impl;

import org.group3.parking.model.VipInfo;
import org.group3.parking.repository.VipInfoRepository;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VipInfoServiceImpl implements VipInfoService {

    @Autowired
    VipInfoRepository vipInfoRepository;

    @Override
    public List<VipInfo> getAllVipInfo() {
        return this.vipInfoRepository.findAll();
    }

    @Override
    public VipInfo getVipInfoByPlateNumber(String plateNumber) throws CanNotFoundVipException {
        Optional<VipInfo> vipInfoOptional = vipInfoRepository.findById(plateNumber);
        if (vipInfoOptional.isEmpty()) {
            throw new CanNotFoundVipException("This user is not a vip!");
        } else
            return vipInfoOptional.get();
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

    @Override
    public boolean isVip(String plateNumber) {
        Optional<VipInfo> vipInfo = this.vipInfoRepository.findById(plateNumber);
        return vipInfo.isPresent();
    }

    @Override
    public void insertVipInfo(VipInfo vipInfo) {
        vipInfoRepository.save(vipInfo);
    }

    @Override
    public void deleteVipInfo(String plateNumber) {
        vipInfoRepository.deleteById(plateNumber);
    }


    @Override
    public void chargeBalance(String plateNumber, BigDecimal amount) throws Exception {

    }


}
