package org.group3.parking.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ParkingLotConfig {
    @Id
    Integer id;

    int size;

    BigDecimal dayTimeUnitPrice;
    BigDecimal nightTimeUnitPrice;
    BigDecimal basePrice;

    BigDecimal discount;

}
