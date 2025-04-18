package com.arqsoft.medici.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductosVendedorDTO {
	
	private Integer paginaActual;
	private Integer totalPaginas;
	private long totalResultados;
	private List<ProductoResponseDTO> productos = new ArrayList<ProductoResponseDTO>();
	
	public ProductosVendedorDTO(Integer paginaActual, Integer totalPaginas, long totalResultados, List<ProductoResponseDTO> productos) {
		super();
		this.paginaActual = paginaActual;
		this.totalPaginas = totalPaginas;
		this.totalResultados = totalResultados;
		this.productos = productos;
	}
	
	public ProductosVendedorDTO() {

	}
	
	public ProductosVendedorDTO(Integer paginaActual, Integer totalPaginas, long totalResultados) {
		super();
		this.paginaActual = paginaActual;
		this.totalPaginas = totalPaginas;
		this.totalResultados = totalResultados;
	}

	public List<ProductoResponseDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoResponseDTO> productos) {
		this.productos = productos;
	}

	public Integer getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(Integer paginaActual) {
		this.paginaActual = paginaActual;
	}

	public Integer getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getTotalResultados() {
		return totalResultados;
	}

	public void setTotalResultados(long totalResultados) {
		this.totalResultados = totalResultados;
	}

}
