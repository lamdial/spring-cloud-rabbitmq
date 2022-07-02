package com.ld.springcloudrabbitmq;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudRabbitmqApplication.class, args);
	}

	@Bean
	Function<String, String> fournisseur() {
		return msg -> {
			System.out.println("Production du message : " + msg);
			return msg + " envoyé";
		};
	}

	@Bean
	Consumer<String> premierConsommateur() {
		return evt -> {
			System.out.println("***** premierConsommateur : " + evt);
		};
	}

	@Bean
	Consumer<String> deuxiemeConsommateur() {
		return evt -> {
			System.out.println("----- deuxiemeConsommateur : " + evt);
		};
	}

	@Bean
	Consumer<String> troisiemeConsommateur() {
		return evt -> {
			System.out.println("+++++ troisiemeConsommateur : " + evt);
		};
	}

}