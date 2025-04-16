package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.arqsoft.medici.application.VentaService;
import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;

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
		 
		 try {
			 
			ventaService.procesarVenta(request);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

		} catch (ValidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);

		} catch (ProductoInexistenteException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);

		} catch (UsuarioNoEncontradoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+request.getMailComprador()+".", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
		 
	 }

	public VentaService getVentaService() {
		return ventaService;
	}

	public void setVentaService(VentaService ventaService) {
		this.ventaService = ventaService;
	}

}
