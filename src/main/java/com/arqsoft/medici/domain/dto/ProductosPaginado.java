package com.arqsoft.medici.domain.dto;

import java.util.List;



import java.util.ArrayList;

import com.arqsoft.medici.domain.Producto;

public class ProductosPaginado {
	
	public ProductosPaginado(List<Producto> productos, int pagina, long totalElementos, int totalPaginas) {
		super();
		this.productos = productos;
		this.pagina = pagina;
		this.totalElementos = totalElementos;
		this.totalPaginas = totalPaginas;
	}

	private List<Producto> productos = new ArrayList<Producto>();
	private int pagina = 0;
	private long totalElementos = 0;
	private int totalPaginas = 0;

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public long getTotalElementos() {
		return totalElementos;
	}

	public void setTotalElementos(long totalElementos) {
		this.totalElementos = totalElementos;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

}
