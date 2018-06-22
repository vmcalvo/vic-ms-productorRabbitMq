package com.everis.vcalvoma.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsConfigMQ {
	
	 public static final String EXCHANGE_NAME = "exchange_test";
	    public static final String ROUTING_KEY = "routing_key_test";
	 
	    public static final String QUEUE_NAME = "queue_test";
	    private static final boolean IS_DURABLE_QUEUE = false;
		private CachingConnectionFactory cachingConnectionFactory;
	    
		public MsConfigMQ() {
			
			//TODO Esto es muy ñapa
			cachingConnectionFactory = new CachingConnectionFactory();
			cachingConnectionFactory.setUsername("vmcalvo.everis");
			cachingConnectionFactory.setPassword("password");
			cachingConnectionFactory.setVirtualHost("/");
			cachingConnectionFactory.setHost("localhost");
			cachingConnectionFactory.setPort(5672);
			cachingConnectionFactory.setRequestedHeartBeat(30);
			cachingConnectionFactory.setConnectionTimeout(30000);

			RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
			Queue queue = new Queue(QUEUE_NAME);
			admin.declareQueue(queue);
			TopicExchange exchange = new TopicExchange(EXCHANGE_NAME);

			admin.declareExchange(exchange);
			admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY));
			
		}

		public ConnectionFactory connectionFactory() {
			
			return cachingConnectionFactory;
		}

}
