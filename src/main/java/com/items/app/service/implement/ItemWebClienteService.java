package com.items.app.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.items.app.models.Item;
import com.items.app.models.Producto;
import com.items.app.service.ItemService;

import reactor.core.publisher.Mono;

@Primary
@Service
public class ItemWebClienteService implements ItemService{
	
	private final WebClient.Builder client;
	
	
	public ItemWebClienteService(WebClient.Builder client) {
		this.client = client;
	}

	/*
	 * .get(): Esto especifica que la solicitud HTTP será de tipo GET, es decir,
	 * para obtener datos. .uri("/api/productos/listar"): Aquí se define la URI del
	 * recurso al que se va a hacer la solicitud. En este caso, está solicitando los
	 * productos en la ruta /api/productos/listar.
	 * .accept(MediaType.APPLICATION_JSON): Esto indica que la respuesta que
	 * esperamos del servidor será en formato JSON (especificando el tipo de
	 * contenido que acepta el cliente). .retrieve(): Este método ejecuta la
	 * solicitud y recupera la respuesta. .bodyToFlux(Producto.class): Convierte el
	 * cuerpo de la respuesta en un Flux (un tipo de Mono o Flux de WebFlux, que es
	 * un contenedor reactivo que representa una secuencia de datos). En este caso,
	 * el cuerpo se deserializa en una secuencia de objetos Producto.
	 */
	@Override
	public List<Item> findAll() {
		System.out.println("reactivo");
		return this.client.build()
				.get()
				.uri("/api/productos/listar")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Producto.class)
				.map(p -> new Item( p, 4 ))
				.collectList()
				.block();
	}

	@Override
	public Optional<Item> findById(Long id) {
		Map<String, Long> params = new HashMap<>();
		params.put("id", id);
		return this.client.build()
				.get()
				.uri("/api/productos/listar/{id}", params)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(
		                status -> status.is4xxClientError(),  // Manejar errores 4xx
		                clientResponse -> {
		                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
		                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
		                    }
		                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de cliente"));
		                }
		            )
		            .onStatus(
		                status -> status.is5xxServerError(),  // Manejar errores 5xx
		                clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el servidor"))
		            )
		            .bodyToMono(Producto.class)
		            .map(p -> new Item(p, 4))
		            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado")))
		            .blockOptional();  // Si prefieres retornar Optional
		}

}
