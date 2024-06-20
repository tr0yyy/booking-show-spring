package com.fmi.bookingshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.fmi.bookingshow.model"})
public class BookingshowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingshowApplication.class, args);
	}

}
