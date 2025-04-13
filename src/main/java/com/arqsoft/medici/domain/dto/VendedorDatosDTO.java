package com.arqsoft.medici.domain.dto;

import java.util.ArrayList;
import java.util.List;
import com.arqsoft.medici.domain.utils.VendedorEstado;

public class VendedorDatosDTO {
	
	private String mail;
	private String razonSocial;
	private VendedorEstado estado;
	private List<ProductoResponseDTO> productosListados = new ArrayList<ProductoResponseDTO>();
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public VendedorEstado getEstado() {
		return estado;
	}
	public void setEstado(VendedorEstado estado) {
		this.estado = estado;
	}
	public List<ProductoResponseDTO> getProductosListados() {
		return productosListados;
	}
	public void setProductosListados(List<ProductoResponseDTO> productosListados) {
		this.productosListados = productosListados;
	}

}
