package org.group3.parking.service.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.group3.parking.model.ParkingLotConfig;
import org.group3.parking.repository.ParkingLotConfigRepository;
import org.group3.parking.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ParkingLotConfigRepository parkingLotConfigRepository;

    @Override
    public void updateConfig(ParkingLotConfig parkingLotConfig) {
            parkingLotConfigRepository.deleteAll();
            parkingLotConfig.setId(1);
            parkingLotConfigRepository.save(parkingLotConfig);
    }

    @Override
    public ParkingLotConfig getParkingLotConfig() {
        List<ParkingLotConfig> allConfigs = parkingLotConfigRepository.findAll();
        if (allConfigs.isEmpty())
            return new ParkingLotConfig();
        return allConfigs.get(0);
    }
}
