package org.group3.parking.controller;

import org.group3.parking.model.ParkingInfo;
import org.group3.parking.model.VipInfo;
import org.group3.parking.service.ParkingInfoService;
import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    ParkingInfoService parkingInfoService;
    @Autowired
    VipInfoService vipInfoService;

    @RequestMapping("/index")
    public String toLoginPage() {
        return "index";
    }

    @RequestMapping("/main")
    public String toMainPage() {
        return "/admin/main";
    }

    @RequestMapping("/login/error")
    public String toLoginFailurePage(Model msg) {
        msg.addAttribute("msg", "用户名或密码错误，请重试！");
        return "index";
    }

    @GetMapping("/parking/info")
    public String toParkingInfoPage(Model msg) {
        List<ParkingInfo> parkingInfoList = parkingInfoService.getAllParkingInfo();
        msg.addAttribute("parkingInfoList", parkingInfoList);
        return "/admin/parking_info/parking_info";
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

    @GetMapping("parking/edit/{parking_id}")
    public String toEditParkingInfo(@PathVariable("parking_id") Long parking_id, Model msg) {
        try {
            ParkingInfo parkingInfo = this.parkingInfoService.getParkingInfoById(parking_id);
            msg.addAttribute("parking_info", parkingInfo);
        } catch (Exception e) {
            return "404";
        }
        return "admin/parking_info/edit";
    }

    @PostMapping("parking/edit/{parking_id}")
    public String editParingInfo(@PathVariable Long parking_id, ParkingInfo parkingInfo) {
        try {
            this.parkingInfoService.updateParkingInfo(parking_id, parkingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/parking/info";
    }

    @GetMapping("parking/delete/{parking_id}")
    public String deleteParkingInfo(@PathVariable Long parking_id) {
        try {
            this.parkingInfoService.deleteParkingInfo(parking_id);
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
        return "/admin/vip_info/vip_info";
    }

    @GetMapping("vip/add")
    public String toVipAddPage(){
        return "/admin/vip_info/add";
    }

    @PostMapping("vip/add")
    public String addVipInfo(VipInfo vipInfo){
        try {
            this.vipInfoService.createVipInfo(vipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/vip/info";
    }

    @GetMapping("vip/edit/{plateNumber}")
    public String toVipEditPage(@PathVariable("plateNumber") String plateNumber,Model msg){
        try {
            VipInfo vipInfo = this.vipInfoService.getVipInfoByPlateNumber(plateNumber);
            msg.addAttribute("vipInfo",vipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "/admin/vip_info/edit";
    }

    @PostMapping("vip/edit/{plateNumber}")
    public String editVipInfo(@PathVariable("plateNumber") String plateNumber,VipInfo vipInfo) {
        try {
            this.vipInfoService.updateVipInfoByPlateNumber(plateNumber,vipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "redirect:/admin/vip/info";

    }
}

