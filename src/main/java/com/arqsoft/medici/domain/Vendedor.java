package com.arqsoft.medici.domain;

import java.util.List;

public class Vendedor {
	
	private String mail;
	private String razonSocial;
	private List<Producto> productosListados;
	
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
	public List<Producto> getProductosListados() {
		return productosListados;
	}
	public void setProductosListados(List<Producto> productosListados) {
		this.productosListados = productosListados;
	}

}
