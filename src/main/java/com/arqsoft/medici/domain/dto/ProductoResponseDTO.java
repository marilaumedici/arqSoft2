package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.ProductoCategoria;
import com.arqsoft.medici.domain.utils.ProductoEstado;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Datos del producto creado o modificado.")
public class ProductoResponseDTO {
	
	@ApiModelProperty(value = "Codigo del producto.", example="680985584a846b45ce0494d7")
	private String codigoProducto;
	@ApiModelProperty(value = "Nombre del producto")
	private String nombre;
	@ApiModelProperty(value = "Descripcion del producto")
	private String descripcion;
	@ApiModelProperty(value = "Precio del producto con 2 decimales", example = "23000.00")
	private double precio;
	@ApiModelProperty(value = "Cantidad disponible para compra del producto", example = "230")
	private int stock;
	@ApiModelProperty(value = "Categoria del producto")
	private ProductoCategoria categoria;
	@ApiModelProperty(value = "Email del vendedor propietario del producto")
	private String mailVendedor;
	@ApiModelProperty(value = "Estado del producto")
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
