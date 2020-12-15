package org.group3.parking.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class ParkingInfo {
    @Id
    @GeneratedValue
    private Long parkingId;

    @Column(nullable = false, length = 7)
    private String plateNumber;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
    private LocalDateTime enterTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
    @Column(nullable = true)
    private LocalDateTime leaveTime;

    @Transient
    boolean isVip;

    private BigDecimal amountPayable;

}
