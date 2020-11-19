package org.group3.parking.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class VipInfo {
    @Id
    @Column(nullable = false, length = 7)
    String plateNumber;

    @Column(nullable = false, length = 11)
    String phoneNumber;

    BigDecimal balance;

}
