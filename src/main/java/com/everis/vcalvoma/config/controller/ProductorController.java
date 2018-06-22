package com.everis.vcalvoma.config.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.everis.vcalvoma.domain.Resultado;
import com.everis.vcalvoma.service.ProductorService;

@Controller
public class ProductorController {

		
		protected Logger logger = Logger.getLogger(ProductorController.class
				.getName());
		
		@Autowired
		protected ProductorService productorService;
		

		@RequestMapping("/productor")
		public String home() {
			return "index";
		}
		
		@RequestMapping("/productor/{textoMensaje}")
		public String msproducer(Model model, @PathVariable("textoMensaje") String textoMensaje) {
		
			logger.info("eviarMensaje: " + textoMensaje);

			Resultado result = productorService.enviarMensaje(textoMensaje);	
				
			model.addAttribute("enviarMensaje", result.getResultado());
		
			return "enviarMensaje";
			
		}
		
		@RequestMapping("/productorFuture/{textoMensaje}")
		public String msproducerFuture(Model model, @PathVariable("textoMensaje") String textoMensaje) throws InterruptedException, ExecutionException {
		
			logger.info("eviarMensaje: " + textoMensaje);

			Future<Resultado> result = productorService.enviarMensajeFuture(textoMensaje);	
				
			model.addAttribute("enviarMensaje", result.get().getResultado());
		
			return "enviarMensaje";
			
		}
		
	}

	
	
	

