package com.items.app.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
	
	private Producto producto;
	
	private int cantidad;
	
	private Double total;
	
	
	public Double calculaTotal() {
		return producto.getPrecio() * cantidad;
	}


	public Item(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.total = calculaTotal();
	}


	public Item() {
		
	}
	

}
