package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.ProductoCategoria;

public class FiltroBuscadorProducto {
	
	private Integer pagina = 0;
	private Integer size = 999;
	private String descripcion;
	private ProductoCategoria categoria;
	private Integer precioMinimo;
	private Integer precioMaximo;
	
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ProductoCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(ProductoCategoria categoria) {
		this.categoria = categoria;
	}
	public Integer getPrecioMinimo() {
		return precioMinimo;
	}
	public void setPrecioMinimo(Integer precioMinimo) {
		this.precioMinimo = precioMinimo;
	}
	public Integer getPrecioMaximo() {
		return precioMaximo;
	}
	public void setPrecioMaximo(Integer precioMaximo) {
		this.precioMaximo = precioMaximo;
	}
	

}
