package org.group3.parking.repository;

import org.group3.parking.model.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingInfoRepository extends JpaRepository <ParkingInfo, Long> {
    Optional<ParkingInfo> findByPlateNumberAndLeaveTimeIsNull(String plateNumber);

    List<ParkingInfo> findByLeaveTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query(nativeQuery = true,value = "select sum() from parking_info where leave_time >= :startTime and leave_time<= :endTime")
    BigDecimal getTotalIncomingBetween(LocalDateTime startTime,LocalDateTime endTime);

}
