package org.group3.parking.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ParkingLotConfig {
    @Id
    Integer id;

    int size;

    BigDecimal unitPrice;

    BigDecimal discount;

}
