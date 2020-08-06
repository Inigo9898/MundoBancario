package es.eoi.mundobancario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.eoi.mundobancario.exception.ClienteNotFoundException;

@SpringBootApplication
public class MundobancarioApplication {

	public static void main(String[] args) throws ClienteNotFoundException {
		SpringApplication.run(MundobancarioApplication.class, args);
	
		MenuApp.main(args);
	
	}

}
