package org.group3.parking.controller;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.model.ParkingLotConfig;
import org.group3.parking.model.VipInfo;
import org.group3.parking.service.ConfigService;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    ParkingInfoService parkingInfoService;
    @Autowired
    VipInfoService vipInfoService;
    @Autowired
    ConfigService configService;


    @RequestMapping("index")
    public String toLoginPage() {
        return "index";
    }

    @RequestMapping("main")
    public String toMainPage() {
        return "admin/main";
    }

    @RequestMapping("login/error")
    public String toLoginFailurePage(Model msg) {
        msg.addAttribute("msg", "用户名或密码错误，请重试！");
        return "index";
    }

    @GetMapping("parking/info")
    public String toParkingInfoPage(Model msg) {
        List<ParkingInfo> parkingInfoList = parkingInfoService.getAllParkingInfo();
        msg.addAttribute("parkingInfoList", parkingInfoList);
        return "admin/parking_info/parking_info";
    }

    @GetMapping("parking/add")
    public String toParkingInfoAddPage() {
        return "admin/parking_info/add";
    }

    @PostMapping("parking/add")
    public String addParkingInfo(ParkingInfo parkingInfo) {
        try {
            this.parkingInfoService.insertParkingInfo(parkingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/parking/info";
    }

    @GetMapping("parking/edit/{parkingId}")
    public String toEditParkingInfo(@PathVariable("parkingId") Long parkingId, Model msg) {
        try {
            ParkingInfo parkingInfo = this.parkingInfoService.getParkingInfoById(parkingId);
            msg.addAttribute("parking_info", parkingInfo);
        } catch (Exception e) {
            return "404";
        }
        return "admin/parking_info/edit";
    }

    @PostMapping("parking/edit/{parkingId}")
    public String editParingInfo(@PathVariable Long parkingId, ParkingInfo parkingInfo) {
        try {
            this.parkingInfoService.updateParkingInfo(parkingId, parkingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/parking/info";
    }

    @GetMapping("parking/delete/{parkingId}")
    public String deleteParkingInfo(@PathVariable Long parkingId) {
        try {
            this.parkingInfoService.deleteParkingInfo(parkingId);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/parking/info";
    }

    @GetMapping("vip/info")
    public String toVipInfoPage(Model msg) {
        List<VipInfo> vipInfoList;
        vipInfoList = this.vipInfoService.getAllVipInfo();
        msg.addAttribute("vipInfoList", vipInfoList);
        return "admin/vip_info/vip_info";
    }

    @GetMapping("vip/add")
    public String toVipAddPage() {
        return "admin/vip_info/add";
    }

    @PostMapping("vip/add")
    public String addVipInfo(VipInfo vipInfo) {
        try {
            this.vipInfoService.createVipInfo(vipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/vip/info";
    }

    @GetMapping("vip/edit/{plateNumber}")
    public String toVipEditPage(@PathVariable("plateNumber") String plateNumber, Model msg) {
        try {
            VipInfo vipInfo = this.vipInfoService.getVipInfoByPlateNumber(plateNumber);
            msg.addAttribute("vipInfo", vipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "admin/vip_info/edit";
    }

    @PostMapping("vip/edit/{plateNumber}")
    public String editVipInfo(@PathVariable("plateNumber") String plateNumber, VipInfo vipInfo) {
        try {
            this.vipInfoService.updateVipInfoByPlateNumber(plateNumber, vipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        return "redirect:/admin/vip/info";
    }

    @GetMapping("config")
    public String toConfigPage(Model msg) {

        ParkingLotConfig parkingLotConfig = configService.getParkingLotConfig();
        msg.addAttribute("currentConfig", parkingLotConfig);
        return "admin/config";
    }

    @PostMapping("config")
    public String updateConfig(ParkingLotConfig parkingLotConfig, Model msg) {
        try {
            configService.updateConfig(parkingLotConfig);
            msg.addAttribute("success", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/config/success";
    }

    @GetMapping("config/success")
    public String toConfigSuccessPage(Model msg){
        ParkingLotConfig parkingLotConfig = configService.getParkingLotConfig();
        msg.addAttribute("currentConfig", parkingLotConfig);
        msg.addAttribute("success",true);
        return "admin/config";
    }

}

