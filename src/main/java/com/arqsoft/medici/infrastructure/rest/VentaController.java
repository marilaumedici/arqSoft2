package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.arqsoft.medici.application.VentaService;
import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;
import com.arqsoft.medici.domain.dto.VendedorDTO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/venta")
public class VentaController {
	
	@Autowired
	private VentaService ventaService;
	
	 @PostMapping(path = "/", 
	 consumes = MediaType.APPLICATION_JSON_VALUE, 
	 produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(nickname = "crear_venta", value = "Registra una venta")
	 public void registrarVenta(@RequestBody RegistrarVentaDTO request) {
		 
		 ventaService.procesarVenta(request);
		 
	 }

	public VentaService getVentaService() {
		return ventaService;
	}

	public void setVentaService(VentaService ventaService) {
		this.ventaService = ventaService;
	}

}
