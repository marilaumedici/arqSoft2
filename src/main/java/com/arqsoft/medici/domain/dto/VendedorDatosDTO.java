package com.arqsoft.medici.domain.dto;

import java.util.ArrayList;
import java.util.List;
import com.arqsoft.medici.domain.utils.VendedorEstado;

public class VendedorDatosDTO {
	
	private String mail;
	private String razonSocial;
	private VendedorEstado estado;
	private List<ProductoDTO> productosListados = new ArrayList<ProductoDTO>();
	
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
	public List<ProductoDTO> getProductosListados() {
		return productosListados;
	}
	public void setProductosListados(List<ProductoDTO> productosListados) {
		this.productosListados = productosListados;
	}

}
