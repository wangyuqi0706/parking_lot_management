package org.group3.parking.service;

import org.group3.parking.model.ParkingInfo;

import java.math.BigDecimal;

public interface CalculateIncomeService {

    BigDecimal calculateIncome(ParkingInfo parkingInfo);
}
