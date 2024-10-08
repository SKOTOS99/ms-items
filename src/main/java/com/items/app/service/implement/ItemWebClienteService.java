package com.items.app.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.items.app.models.Item;
import com.items.app.models.Producto;
import com.items.app.service.ItemService;

@Primary
@Service
public class ItemWebClienteService implements ItemService{
	
	private final WebClient.Builder client;
	
	
	public ItemWebClienteService(WebClient.Builder client) {
		this.client = client;
	}

	@Override
	public List<Item> findAll() {
		System.out.println("reactivo");
		return this.client.build()
				.get()
				.uri("http://ms-productos/productos/listar")
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
				.uri("http://ms-productos/productos/listar/{id}", params)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Producto.class)
				.map(p -> new Item( p, 4 ))
				.blockOptional();
	}

}
