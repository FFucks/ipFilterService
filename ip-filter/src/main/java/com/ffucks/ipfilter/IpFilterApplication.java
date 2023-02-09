package com.ffucks.ipfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class IpFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpFilterApplication.class, args);
	}

}
