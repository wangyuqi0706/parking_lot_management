package org.group3.parking.service;

import org.group3.parking.model.ParkingLotConfig;

import java.math.BigDecimal;

public interface ConfigService {
    void updateConfig(ParkingLotConfig parkingLotConfig) throws Exception;
    ParkingLotConfig getParkingLotConfig();
}
