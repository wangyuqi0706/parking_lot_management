package org.group3.parking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class VipInfo {
    @Id
    long vipId;
    String plateNumber;
    String phoneNumber;
    BigDecimal balance;

}
