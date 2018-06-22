package com.everis.vcalvoma.service;


import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.vcalvoma.config.MsConfigMQ;
import com.everis.vcalvoma.domain.Resultado;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@Service
public class ProductorService {

	protected Logger logger = Logger.getLogger(ProductorService.class.getName());

	@Autowired
	protected MsConfigMQ configMq;
	@Autowired
	protected RabbitTemplate rabbitTemplate;

	@HystrixCommand(fallbackMethod = "envioMensajePorDefecto", commandProperties = {
	        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")})
	public Resultado enviarMensaje(String textoMensaje) {
		try {
			logger.info("enviarMensaje() invoked: for " + textoMensaje);
			rabbitTemplate.setConnectionFactory(configMq.connectionFactory());
			rabbitTemplate.convertAndSend(MsConfigMQ.EXCHANGE_NAME,MsConfigMQ.ROUTING_KEY, textoMensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resultado("Enviado OK");

	}

	// Asynchronous Model
	@HystrixCommand(fallbackMethod = "envioMensajeFuturePorDefecto")
	public Future<Resultado> enviarMensajeFuture(final String textoMensaje) {
		return new AsyncResult<Resultado>() {
			public Resultado invoke() {
				logger.info("enviarMensaje() invoked: for " + textoMensaje);
				rabbitTemplate.setConnectionFactory(configMq.connectionFactory());
				rabbitTemplate.convertAndSend(MsConfigMQ.EXCHANGE_NAME,MsConfigMQ.ROUTING_KEY, textoMensaje);
				return new Resultado("Enviado OK");
			}
		};
	}

	public Resultado envioMensajePorDefecto(String textoMensaje) {
		return new Resultado("Hello World thanks to Circuit Breaker (Hystrix) - SYNCHRONOUS");
	}

	public Resultado envioMensajeFuturePorDefecto(String textoMensaje) {
		return new Resultado("Hello World thanks to Circuit Breaker (Hystrix) - FUTURE (ASYNCHRONOUS");
	}

}
