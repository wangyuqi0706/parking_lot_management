package org.group3.parking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;

@Configuration
@Getter
@Setter
@PropertySource(value = {"classpath:parkingLot.properties", "file:parkingLot.properties"}, ignoreResourceNotFound = true)

public class ParkingLotConfig {
    @Value("${size}")
    int size;

    @Value("${unitPrice}")
    BigDecimal unitPrice;

    @Value("${discount}")
    BigDecimal discount;

}
