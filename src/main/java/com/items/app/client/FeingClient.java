package com.items.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.items.app.models.Producto;

@FeignClient(name = "ms-productos")
public interface FeingClient {
	
	@GetMapping("/productos/listar")
	List<Producto> findAll();
		
	@GetMapping("/productos/listar/{id}")
	 Producto listarProductoId(@PathVariable Long id);

}
