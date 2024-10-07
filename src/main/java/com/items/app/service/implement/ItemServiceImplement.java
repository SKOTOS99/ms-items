package com.items.app.service.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.items.app.client.FeingClient;
import com.items.app.models.Item;
import com.items.app.models.Producto;
import com.items.app.service.ItemService;

import feign.FeignException;

@Service
public class ItemServiceImplement implements ItemService {
	
	private final FeingClient client;
	
	public ItemServiceImplement(FeingClient client) {
		this.client = client;
		
	}

	@Override
	public List<Item> findAll() {
		return client.findAll().stream().map(x -> new Item( x, 4 )).toList();
		
	}

	@Override
	public Optional<Item> findById(Long id) {
		try {
			Producto producto =client.listarProductoId(id);
			return Optional.ofNullable(new Item(producto, 3));
		}catch(FeignException e) {
			return Optional.empty();
		}
		
		
	}

}
