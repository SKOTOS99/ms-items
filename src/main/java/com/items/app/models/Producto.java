package com.items.app.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Producto {

	
	private Long id;
	private String nombre;
	
	private Double precio;
	private LocalDate createAt;
	private int port;
	
	
	public Producto(Long id, String nombre, Double precio, int port) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.port = port;
	}
	
	
}
