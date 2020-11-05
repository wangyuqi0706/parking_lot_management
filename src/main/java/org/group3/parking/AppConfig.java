package org.group3.parking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@ComponentScan
@PropertySource("jdbc.properties")
public class AppConfig {

}
