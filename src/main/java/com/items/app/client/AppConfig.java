package com.items.app.client;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	  
	/**
	 * slidingWindowSize(10): Define el número de intentos (llamadas) que el circuit
	 * breaker tendrá en cuenta para calcular si debe abrirse o no. En este caso,
	 * está observando los últimos 10 intentos.
	 * 
	 * failureRateThreshold(50): Si el 50%
	 * de esos últimos 10 intentos fallan, el circuit breaker se abrirá (esto
	 * significa que no se podrán hacer más solicitudes hasta que el circuito se
	 * recupere).
	 *  
	 * waitDurationInOpenState(Duration.ofSeconds(10)): Cuando el
	 * circuito está abierto (debido a fallos), se espera 10 segundos antes de
	 * permitir cualquier nueva solicitud. Durante este tiempo, el circuit breaker
	 * no permite llamadas para evitar sobrecargar el sistema.
	 * 
	 * permittedNumberOfCallsInHalfOpenState(5): Cuando el circuito pasa a estado
	 * semiabierto (después de la espera en estado abierto), permite hasta 5
	 * intentos para verificar si el sistema se ha recuperado. Si estas 5 llamadas
	 * son exitosas, el circuito se cerrará de nuevo.
	 * 
	 * slowCallDurationThreshold(Duration.ofSeconds(2L)): Define un umbral para las
	 * llamadas lentas. Si una llamada tarda más de 2 segundos, se considerará como
	 * una llamada "lenta". 
	 * 
	 * slowCallRateThreshold(50): Si el 50% o más de las
	 * llamadas son lentas, el circuito se abrirá. Esto ayuda a evitar que se
	 * realicen demasiadas solicitudes lentas que puedan afectar el rendimiento del
	 * sistema.
	 * 
	 * @return
	 */
	@Bean
	Customizer<Resilience4JCircuitBreakerFactory> customizerCb() {
	    return factory -> factory.configureDefault(id -> {
	        return new Resilience4JConfigBuilder(id)
	                .circuitBreakerConfig(CircuitBreakerConfig.custom()
	                        .slidingWindowSize(10) // Toleramos 10 intentos
	                        .failureRateThreshold(50) // Si el 50% de los intentos fallan, el circuito se abre
	                        .waitDurationInOpenState(Duration.ofSeconds(10)) // Espera de 10 segundos cuando el circuito está abierto
	                        .permittedNumberOfCallsInHalfOpenState(5) // Solo se permiten 5 llamadas en estado semiabierto
	                        .slowCallDurationThreshold(Duration.ofSeconds(2L)) // Umbral para considerar una llamada como "lenta" si dura más de 2 segundos
	                        .slowCallRateThreshold(50) // Si el 50% o más de las llamadas son lentas, el circuito se abrirá
	                        .build())
	                .timeLimiterConfig(TimeLimiterConfig.custom()
	                       .timeoutDuration(Duration.ofSeconds(3L)).build())
	                .build();
	    });
	}

}
