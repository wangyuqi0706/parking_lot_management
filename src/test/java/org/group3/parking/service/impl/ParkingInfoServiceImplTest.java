package org.group3.parking.service.impl;

import org.group3.parking.ParkingApplication;
import org.group3.parking.model.ParkingInfo;
import org.group3.parking.service.ParkingInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingApplication.class)
@WebAppConfiguration
@Transactional
public class ParkingInfoServiceImplTest {
    @Autowired
    ParkingInfoService parkingInfoService;

    ArrayList<ParkingInfo> cases = new ArrayList<>();
    ArrayList<BigDecimal> excepted = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    @Before
    public void init() {
        ParkingInfo parkingInfo1 = new ParkingInfo();
        parkingInfo1.setPlateNumber("川A12345");
        parkingInfo1.setEnterTime(LocalDateTime.parse("2019-12-31T23:50", formatter));
        parkingInfo1.setLeaveTime(LocalDateTime.parse("2020-01-01T00:19", formatter));
        cases.add(parkingInfo1);
        excepted.add(BigDecimal.valueOf(1.00).setScale(2, RoundingMode.HALF_UP));

        ParkingInfo parkingInfo2 = new ParkingInfo();
        parkingInfo2.setPlateNumber("川A12345");
        parkingInfo2.setEnterTime(LocalDateTime.parse("2019-02-28T10:50", formatter));
        parkingInfo2.setLeaveTime(LocalDateTime.parse("2019-02-28T11:20", formatter));
        cases.add(parkingInfo2);
        excepted.add(BigDecimal.valueOf(6.50).setScale(2, RoundingMode.HALF_UP));

        ParkingInfo parkingInfo3 = new ParkingInfo();
        parkingInfo3.setPlateNumber("川A12345");
        parkingInfo3.setEnterTime(LocalDateTime.parse("2019-12-31T23:15", formatter));
        parkingInfo3.setLeaveTime(LocalDateTime.parse("2020-01-01T08:45", formatter));
        cases.add(parkingInfo3);
        excepted.add(BigDecimal.valueOf(31.00).setScale(2, RoundingMode.HALF_UP));

        ParkingInfo parkingInfo4 = new ParkingInfo();
        parkingInfo4.setPlateNumber("川A12345");
        parkingInfo4.setEnterTime(LocalDateTime.parse("2020-02-29T20:00", formatter));
        parkingInfo4.setLeaveTime(LocalDateTime.parse("2020-03-01T10:45", formatter));
        cases.add(parkingInfo4);
        excepted.add(BigDecimal.valueOf(53.50).setScale(2, RoundingMode.HALF_UP));

        ParkingInfo parkingInfo5 = new ParkingInfo();
        parkingInfo5.setPlateNumber("川A12345");
        parkingInfo5.setEnterTime(LocalDateTime.parse("2019-02-28T20:00", formatter));
        parkingInfo5.setLeaveTime(LocalDateTime.parse("2020-03-01T12:30", formatter));
        cases.add(parkingInfo5);
        excepted.add(BigDecimal.valueOf(37396.50).setScale(2, RoundingMode.HALF_UP));

    }

    @Test
    public void calculateAmount() throws Exception {
        for (int i = 0; i < cases.size(); i++) {
            BigDecimal actual = parkingInfoService.calculateAmount(cases.get(i));
            BigDecimal expected = this.excepted.get(i);
            Assertions.assertEquals(expected, actual);
        }
    }
}