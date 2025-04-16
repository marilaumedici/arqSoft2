package com.arqsoft.medici.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Venta {
	

	@Id
	private String ventaId;
	private String productoId;
	private String mailVendedor;
	private String mailComprador;
	private Date fechaVenta;
	private double precioIndividual;
	private double precioFinal;
	private Integer cantidadComprada;
	
	
	public Venta(String productoId, String mailVendedor, String mailComprador, Date fechaVenta,
			double precioIndividual, double precioFinal, Integer cantidadComprada) {
		super();

		this.productoId = productoId;
		this.mailVendedor = mailVendedor;
		this.mailComprador = mailComprador;
		this.fechaVenta = fechaVenta;
		this.precioIndividual = precioIndividual;
		this.precioFinal = precioFinal;
		this.cantidadComprada = cantidadComprada;
	}
	
	public Venta() {

	}
	
	public String getVentaId() {
		return ventaId;
	}
	public void setVentaId(String ventaId) {
		this.ventaId = ventaId;
	}

	public String getProductoId() {
		return productoId;
	}
	public void setProductoId(String productoId) {
		this.productoId = productoId;
	}
	public String getMailVendedor() {
		return mailVendedor;
	}
	public void setMailVendedor(String mailVendedor) {
		this.mailVendedor = mailVendedor;
	}
	public String getMailComprador() {
		return mailComprador;
	}
	public void setMailComprador(String mailComprador) {
		this.mailComprador = mailComprador;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public double getPrecioIndividual() {
		return precioIndividual;
	}
	public void setPrecioIndividual(double precioIndividual) {
		this.precioIndividual = precioIndividual;
	}
	public double getPrecioFinal() {
		return precioFinal;
	}
	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}
	public Integer getCantidadComprada() {
		return cantidadComprada;
	}
	public void setCantidadComprada(Integer cantidadComprada) {
		this.cantidadComprada = cantidadComprada;
	}

}
