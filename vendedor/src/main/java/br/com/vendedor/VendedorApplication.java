package br.com.vendedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class VendedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendedorApplication.class, args);
	}

}
