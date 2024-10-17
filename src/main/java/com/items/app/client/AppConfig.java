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
	 * slidingWindowSize(10): Esto significa que el circuit breaker observará los
	 * últimos 10 intentos (o llamadas) para determinar si algo está fallando. Si en
	 * esos 10 intentos, más de un cierto porcentaje de ellos fallan, el circuito se
	 * abrirá.
	 * 
	 * failureRateThreshold(50): Esto indica que si el 50% o más de las últimas 10
	 * llamadas fallan, entonces el circuit breaker se "abrirá". Es decir, si la
	 * tasa de fallos es mayor o igual al 50%, el sistema dejará de hacer más
	 * intentos a esa parte del servicio.
	 * 
	 * waitDurationInOpenState(Duration.ofSeconds(10L)): Cuando el circuit breaker
	 * está abierto (es decir, cuando se ha detectado un número elevado de fallos),
	 * permanecerá en este estado durante 10 segundos antes de permitir que se
	 * vuelvan a hacer intentos (estado "semiabierto").
	 * 
	 * permittedNumberOfCallsInHalfOpenState(5): Cuando el circuit breaker está en
	 * estado "semiabierto", se permitirá que se hagan 5 intentos. En este estado,
	 * el sistema prueba si los problemas se han resuelto. Si estas 5 llamadas son
	 * exitosas, el circuit breaker se cerrará (vuelve a permitir llamadas
	 * normalmente). Si fallan, se abrirá nuevamente.
	 * 
	 * Resilience4JConfigBuilder(id).circuitBreakerConfig(...)
	 * 
	 * Resilience4JConfigBuilder(id): Crea un configurador de circuit breaker con un
	 * ID específico (usado para identificar este circuito en particular).
	 * circuitBreakerConfig(...): Aquí es donde estás configurando las reglas que
	 * definimos previamente (sliding window, failure rate, etc.).
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
	                        .build()).timeLimiterConfig(TimeLimiterConfig.custom()
	                        		.timeoutDuration(Duration.ofSeconds(3L)).build())
	                .build();
	    });
	}

}
