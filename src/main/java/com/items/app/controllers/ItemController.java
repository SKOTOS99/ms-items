package com.items.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.items.app.models.Item;
import com.items.app.service.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

	
	private final ItemService service;
	
	public ItemController(ItemService service) {
		this.service = service;
	}
	
	@GetMapping("/item/listar")
	public List<Item> listarItems(){
		return service.findAll();
	}
	
	@GetMapping("/item/listar/{id}")
	public ResponseEntity<Item> listarPorId(@PathVariable Long id) {
		
		return service.findById(id).isPresent() 
				? ResponseEntity.ok(service.findById(id).get()) 
						: ResponseEntity.notFound().build();
	}
	
}
