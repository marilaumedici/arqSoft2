package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.ProductoCategoria;
import com.arqsoft.medici.domain.utils.ProductoEstado;

public class ProductoResponseDTO {
	
	private String codigoProducto;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private ProductoCategoria categoria;
	private String mailVendedor;
	private ProductoEstado estado;
	
	
	public ProductoResponseDTO(String id, String nombre, String descripcion, double precio, int stock, ProductoCategoria categoria, ProductoEstado estado, String mailVendedor) {
		super();
		this.codigoProducto = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.mailVendedor = mailVendedor;
		this.estado = estado;
	}
	
	public ProductoResponseDTO() {}
	
	
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
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

	public ProductoEstado getEstado() {
		return estado;
	}

	public void setEstado(ProductoEstado estado) {
		this.estado = estado;
	}

}
