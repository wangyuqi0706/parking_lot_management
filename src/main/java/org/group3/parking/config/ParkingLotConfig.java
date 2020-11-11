package org.group3.parking.config;

import org.springframework.beans.factory.annotation.Autowired;

public class ParkingLotConfig {
    Integer size;
    @Autowired
    BillingStandardConfig billingStandardConfig;
}
