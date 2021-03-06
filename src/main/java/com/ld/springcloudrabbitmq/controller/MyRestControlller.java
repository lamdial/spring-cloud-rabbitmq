package com.ld.springcloudrabbitmq.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
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
	private JmsTemplate jmsTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping(value = "/api", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getMessage(@RequestParam(value = "message") String message) {

		// System.out.println("Message REST recu : " + message);
		System.out.println("#############################################################################3");

		String str = String.join("", "Voici le message : ", message);

		sendToIbmMq(str);

		return str;
	}

	private void sendToRabbitMq(String message, String str) {

		if (StringUtils.length(message) <= 5) {
			rabbitTemplate.convertAndSend(exchange, routingKey1, str);
		} else if (StringUtils.length(message) <= 10) {
			rabbitTemplate.convertAndSend(exchange, routingKey2, str);
		} else {
			System.out.println("Trop long");
		}
	}

	private void sendToIbmMq(String str) {
		jmsTemplate.convertAndSend("DEV.QUEUE.1", str);
	}

	// @JmsListener(destination = "DEV.QUEUE.1")
	public void getMessageFromIbmMq(final Message message) throws JMSException {

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("Message JMS recu : " + textMessage.getText());
		}
	}

}