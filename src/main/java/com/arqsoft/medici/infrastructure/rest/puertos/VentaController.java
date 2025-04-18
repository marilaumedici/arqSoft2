package com.arqsoft.medici.infrastructure.rest.puertos;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/venta")
public interface VentaController {
	
	 @PostMapping(path = "/", 
	 consumes = MediaType.APPLICATION_JSON_VALUE, 
	 produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(nickname = "crear_venta", value = "Registra una venta")
	 public void registrarVenta(@RequestBody RegistrarVentaDTO request);

}
