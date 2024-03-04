package com.proggettazione.richiesteConsapBE;

import com.proggettazione.richiesteConsapBE.service.impl.RichiestaServiceImpl;
import com.proggettazione.richiesteConsapBE.service.impl.StatoServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RichiesteConsapBeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RichiesteConsapBeApplication.class, args);
		context.getBean(StatoServiceImpl.class).implementaStati();
	}


}
