package com.vedruna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.vedruna")
public class ProyectoAcceso2EvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoAcceso2EvApplication.class, args);
	}

}
