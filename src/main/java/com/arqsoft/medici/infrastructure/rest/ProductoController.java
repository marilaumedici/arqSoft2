package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.arqsoft.medici.application.ProductoService;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "crear_producto", value = "Crea un producto")
	public void crearProducto(@RequestBody ProductoDTO request) {
    	
    	productoService.crearProducto(request);
    	
    }

	public ProductoService getProductoService() {
		return productoService;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

}
