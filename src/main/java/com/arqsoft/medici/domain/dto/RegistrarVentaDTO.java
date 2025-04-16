package com.arqsoft.medici.domain.dto;


public class RegistrarVentaDTO {
	
	private String productoId;
	private String mailComprador;
	private Integer cantidad;
	
	public String getProductoId() {
		return productoId;
	}
	public void setProductoId(String productoId) {
		this.productoId = productoId;
	}
	public String getMailComprador() {
		return mailComprador;
	}
	public void setMailComprador(String mailComprador) {
		this.mailComprador = mailComprador;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
