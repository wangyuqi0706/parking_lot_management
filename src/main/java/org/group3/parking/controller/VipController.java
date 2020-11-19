package org.group3.parking.controller;

import org.group3.parking.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/vip")
public class VipController {
    @Autowired
    VipInfoService vipInfoService;

    @GetMapping("/charge")
    public String toChargePage(){
        return null;
    }

    @PostMapping("/charge")
    public String charge(String plateNumber, BigDecimal amount){
        return null;
    }
}
