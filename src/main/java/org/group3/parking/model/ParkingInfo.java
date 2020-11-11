package org.group3.parking.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class ParkingInfo {
    @Id
    private Long parkingId;
    private String plateNumber;
    private LocalDateTime enterTime;
    private LocalDateTime leaveTime;
    private String id;

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }

    public LocalDateTime getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(LocalDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    @javax.persistence.Id
    public String getId() {
        return id;
    }
}
