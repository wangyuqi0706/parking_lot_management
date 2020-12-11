package org.group3.parking.controller;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.model.VipInfo;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("pay")
public class PayController {
    @Autowired
    VipInfoService vipInfoService;
    @Autowired
    ParkingInfoService parkingInfoService;

    @GetMapping("leave/{parkingId}")
    public String toPayPage(@PathVariable("parkingId") Long parkingId, Model msg) throws Exception {
        ParkingInfo parkingInfo = parkingInfoService.getParkingInfoById(parkingId);
        if (vipInfoService.isVip(parkingInfo.getPlateNumber())) {
            //用户是VIP
            VipInfo vipInfo = vipInfoService.getVipInfoByPlateNumber(parkingInfo.getPlateNumber());
            BigDecimal balance = vipInfo.getBalance();
            if (balance.compareTo(parkingInfo.getAmountPayable()) >= 0) {
                //余额充足
                balance = balance.subtract(parkingInfo.getAmountPayable());
                vipInfo.setBalance(balance);
                msg.addAttribute("vipInfo", vipInfo);
                return "pay/success";
            } else {
                //余额不足
                msg.addAttribute("vipInsufficientBalance", true);
                msg.addAttribute("vipInfo", vipInfo);
                return "pay/vipBalanceCharge";
            }
        } else {
            //用户不是VIP
            msg.addAttribute("parkingInfo", parkingInfo);
            return "pay/payForLeave";
        }
    }

    @GetMapping("vipBalanceCharge")
    public String toVipBalanceCharge(Model msg) {
        msg.addAttribute("vipInsufficientBalance", false);
        return "pay/vipBalanceCharge";
    }

    @PostMapping("vipBalanceCharge")
    public String vipBalanceCharge(@RequestParam("plateNumber") String plateNumber
            , @RequestParam("amount") BigDecimal amount
            , @RequestParam("phoneNumber") String phoneNumber) throws Exception {
        try {
            VipInfo vipInfo = vipInfoService.getVipInfoByPlateNumber(plateNumber);
            vipInfo.setBalance(vipInfo.getBalance().add(amount));
            vipInfoService.updateVipInfoByPlateNumber(plateNumber, vipInfo);
        } catch (VipInfoService.CanNotFoundVipException e) {
            VipInfo vipInfo = new VipInfo();
            vipInfo.setPlateNumber(plateNumber);
            vipInfo.setBalance(amount);
            vipInfo.setPhoneNumber(phoneNumber);
            vipInfoService.insertVipInfo(vipInfo);
        }
        return "pay/success";
    }

    @RequestMapping("success")
    public String toSuccessPage() {
        return "pay/success";
    }

}
