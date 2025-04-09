package com.arqsoft.medici.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.arqsoft.medici.domain.utils.VendedorEstado;

public class Vendedor {
	
	@Id
	private String mail;
	private String razonSocial;
	private VendedorEstado estado;
	@DBRef//(lazy = true) 
	private List<Producto> productosListados = new ArrayList<Producto>();
	
	public Vendedor(String mail, String razonSocial) {
		super();
		this.mail = mail;
		this.razonSocial = razonSocial;
		this.estado = VendedorEstado.ACTIVO;
	}
	
	public Vendedor() {

	}
	
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
	public VendedorEstado getEstado() {
		return estado;
	}
	public void setEstado(VendedorEstado estado) {
		this.estado = estado;
	}

}
