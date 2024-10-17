package com.items.app.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Value("${config.base.endpoind.ms-productos}")
	private String url;

	/*
	 * @Bean:
	 * 
	 * Esta anotación le indica a Spring que el método debe crear un bean. En
	 * términos sencillos, un "bean" es un objeto que Spring maneja y administra,
	 * como parte de su sistema de inyección de dependencias. El bean que este
	 * método devuelve será inyectado en otras partes de la aplicación donde sea
	 * necesario.
	 * 
	 * @LoadBalanced:
	 * 
	 * Esta anotación es parte de Spring Cloud. Lo que hace es habilitar el balanceo
	 * de carga de servicios. En lugar de hacer llamadas a una URL estática (por
	 * ejemplo, http://localhost:8080), con @LoadBalanced, Spring resolverá la URL
	 * de un servicio a través de un mecanismo de descubrimiento de servicios (como
	 * Eureka o Consul). Por ejemplo, si tienes un servicio llamado my-service, en
	 * lugar de hacer una solicitud directamente a una IP específica o puerto,
	 * Spring buscará automáticamente las instancias de ese servicio disponibles en
	 * la red y las equilibrará (balanceará) para que las solicitudes se distribuyan
	 * entre ellas.
	 * 
	 * WebClient.Builder webClient():
	 * 
	 * WebClient es una clase de Spring que se usa para realizar solicitudes HTTP de
	 * manera reactiva (sin bloqueo). Es la versión moderna y más flexible de
	 * RestTemplate. Aquí, se está creando un builder de WebClient. El builder es un
	 * patrón de diseño que facilita la construcción de objetos complejos paso a
	 * paso, en este caso, la configuración del WebClient.
	 * 
	 * WebClient.builder().baseUrl(url):
	 * 
	 * WebClient.builder() crea una instancia de WebClient.Builder, que es el punto
	 * de partida para construir un objeto WebClient. baseUrl(url): Aquí se define
	 * la URL base para las solicitudes HTTP que se realizarán con este WebClient.
	 * Es decir, si defines la URL base como http://my-service, todas las
	 * solicitudes realizadas con este WebClient se harán utilizando esa URL
	 * inicial. En este caso, url es una variable que contiene la URL base que se
	 * está configurando para el WebClient.
	 */
	@Bean
	@LoadBalanced
	WebClient.Builder webClient(){
		return WebClient.builder().baseUrl(url);
	}
}
