package com.items.app.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.items.app.models.Item;
import com.items.app.models.Producto;
import com.items.app.service.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

	
	private final ItemService service;
	
	private final CircuitBreakerFactory cb;
	
	public ItemController(ItemService service, CircuitBreakerFactory cb) {
		this.service = service;
		this.cb = cb;
	}
	
	@GetMapping("/item/listar")
	public List<Item> listarItems(@RequestParam(name="name", required=false) String name, 
			@RequestHeader(name="token-request", required=false) String token){
		System.out.println("token item: "+ token);
		System.out.println("param item: "+ name);

		return service.findAll();
	}
	
	@GetMapping("/item/listar/{id}")
	public ResponseEntity<Item> listarPorId(@PathVariable Long id) {
		Optional<Item> item = cb.create("items").run(()->service.findById(id), e->{
			Item i = new Item(new Producto(1L,"producto alternativo",12.456, 888), 3);
					return Optional.of(i);
		});
		
		return item.isPresent() 
				? ResponseEntity.ok(item.get()) 
						: ResponseEntity.notFound().build();
		
	}
	
}
