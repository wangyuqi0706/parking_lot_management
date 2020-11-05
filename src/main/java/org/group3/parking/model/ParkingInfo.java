package org.group3.parking.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "parkingInfo")
public class ParkingInfo {
    @Id
    private Long parkingId;
    private String plateNumber;
    private Date enterTime;
    private Date leaveTime;
}
