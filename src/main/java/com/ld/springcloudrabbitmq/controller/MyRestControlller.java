package com.ld.springcloudrabbitmq.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MyRestControlller {

	@Value("${destination}")
	private String exchange;

	@Value("${routingKey1}")
	private String routingKey1;

	@Value("${routingKey2}")
	private String routingKey2;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping(value = "/api", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getMessage(@RequestParam(value = "message") String message) {

		// System.out.println("Message REST recu : " + message);
		System.out.println("#############################################################################3");
		String str = String.join("", "Voici le message : ", message);

		// fournisseur.apply(str);
		if (StringUtils.length(message) <= 5) {
			rabbitTemplate.convertAndSend(exchange, routingKey1, str);
		} else if (StringUtils.length(message) <= 10) {
			rabbitTemplate.convertAndSend(exchange, routingKey2, str);
		} else {
			System.out.println("Trop long");
		}

		return str;
	}

}