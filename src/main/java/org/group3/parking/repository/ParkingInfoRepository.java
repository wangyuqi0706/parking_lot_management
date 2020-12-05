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

    @Query(nativeQuery = true,value = "select sum(amount_payable) from parking_info where leave_time >= ?1 and leave_time<= ?2")
    BigDecimal getTotalIncomingBetween(LocalDateTime startTime,LocalDateTime endTime);

    @Query(nativeQuery = true,value = "select count(*) from parking_info where enter_time>=?1 and enter_time<=?2")
    Integer getTotalNumberBetween(LocalDateTime startTime,LocalDateTime endTime);

}
