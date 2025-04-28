package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.ProductoCategoria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Datos para la creacion/modificación de un producto.")
public class ProductoDTO {
	
	@ApiModelProperty(value = "Codigo del producto. Para la creación s viaja vacio, se creara un producto nuevo. Si viaja lleno, modificará al producto", example="680985584a846b45ce0494d7")
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
