package org.group3.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.group3.parking"})
@EnableJpaRepositories(basePackages = "org.group3.parking.repository")
@EntityScan(basePackages = "org.group3.parking.model")
public class ParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
        System.out.println("http://localhost:8080/admin/main");
    }

}
