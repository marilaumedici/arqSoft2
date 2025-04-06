package com.arqsoft.medici.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.arqsoft.medici.domain.utils.ProductoCategoria;
import com.arqsoft.medici.domain.utils.ProductoEstado;

public class Producto {
	
	@Id
	private String productoId;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private ProductoCategoria categoria;
	private ProductoEstado estado;
	@DBRef
	private Vendedor vendedor;
	
    public Producto() {
		
	}
	
	public Producto(String nombre, String descripcion, double precio, int stock, ProductoCategoria categoria) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.estado = ProductoEstado.ACTIVO;
	}
	
	public String getProductoId() {
		return productoId;
	}
	public void setProductoId(String productoId) {
		this.productoId = productoId;
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
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	public ProductoCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(ProductoCategoria categoria) {
		this.categoria = categoria;
	}
	public ProductoEstado getEstado() {
		return estado;
	}
	public void setEstado(ProductoEstado estado) {
		this.estado = estado;
	}
}
