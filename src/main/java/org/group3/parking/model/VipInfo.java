package org.group3.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class VipInfo {
    @Id
    @Column(nullable = false)
    String plateNumber;

    @Column(nullable = false)
    String phoneNumber;

    BigDecimal balance;

}
