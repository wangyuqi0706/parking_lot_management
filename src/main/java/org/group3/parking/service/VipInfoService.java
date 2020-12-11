package org.group3.parking.service;

import org.group3.parking.model.VipInfo;

import java.math.BigDecimal;
import java.util.List;

public interface VipInfoService {
    List<VipInfo> getAllVipInfo();

    VipInfo getVipInfoByPlateNumber(String plateNumber) throws CanNotFoundVipException;

    void updateVipInfoByPlateNumber(String plateNumber,VipInfo vipInfo) throws Exception;

    void createVipInfo(VipInfo vipInfo) throws Exception;

    boolean isVip(String plateNumber);

    void insertVipInfo(VipInfo vipInfo);


    void chargeBalance(String plateNumber, BigDecimal amount) throws Exception;

    class CanNotFoundVipException extends Exception {
        public CanNotFoundVipException(String message){
            super(message);
        }
    }
}
