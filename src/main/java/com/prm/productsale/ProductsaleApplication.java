package com.prm.productsale;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableScheduling
@SpringBootApplication
public class ProductsaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsaleApplication.class, args);
	}

}
