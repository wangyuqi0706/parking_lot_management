package org.group3.parking.service;

import org.group3.parking.model.VipInfo;

import java.math.BigDecimal;
import java.util.List;

public interface VipInfoService {
    List<VipInfo> getAllVipInfo();

    VipInfo getVipInfoByPlateNumber(String plateNumber) throws Exception;

    void updateVipInfoByPlateNumber(String plateNumber,VipInfo vipInfo) throws Exception;

    void createVipInfo(VipInfo vipInfo) throws Exception;

    boolean isVip(String plateNumber);

    void chargeBalance(String plateNumber, BigDecimal amount) throws Exception;
}
