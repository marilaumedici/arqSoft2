package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.ProductoCategoria;

public class ProductoDTO {
	
	private String codigoProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private ProductoCategoria categoria;
	private String mailVendedor;
	
	
	public ProductoDTO(String nombre, String descripcion, double precio, int stock, ProductoCategoria categoria, String mailVendedor) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.mailVendedor = mailVendedor;
	}
	
	public ProductoDTO(String codigo, String nombre, String descripcion, double precio, int stock, ProductoCategoria categoria, String mailVendedor) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.mailVendedor = mailVendedor;
		this.codigoProducto =  codigo;
	}
	
	public ProductoDTO() {}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public ProductoCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(ProductoCategoria categoria) {
		this.categoria = categoria;
	}
	public String getMailVendedor() {
		return mailVendedor;
	}
	public void setMailVendedor(String mailVendedor) {
		this.mailVendedor = mailVendedor;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

}
