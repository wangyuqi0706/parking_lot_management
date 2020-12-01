package org.group3.parking.repository;

import org.group3.parking.model.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingInfoRepository extends JpaRepository <ParkingInfo, Long> {
    Optional<ParkingInfo> findByPlateNumberAndLeaveTimeIsNull(String plateNumber);

}
