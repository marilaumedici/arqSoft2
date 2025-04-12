package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.arqsoft.medici.application.ProductoService;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
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
    	
    	try {
    		
			productoService.crearProducto(request);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
			
		} catch (VendedorNoEncontradoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el vendedor "+request.getMailVendedor()+".", e);
			
		} catch (ValidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
    }
    
    @PutMapping(path = "/{productoId}", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "modificar_producto", value = "Modifica los datos de un producto")
	public void modificarProducto(@PathVariable(value = "productoId") String id, @RequestBody ProductoDTO request) {
    	
    	try {
    		
			productoService.modificarProducto(id,request);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

		} catch (ProductoInexistenteException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
    }
    
    @DeleteMapping(path = "/{productoId}/{mailVendedor}", 
    //consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "borrar_producto", value = "Borra un producto logicamente")
	public void eliminarProducto(@PathVariable(value = "productoId") String id, @PathVariable(value = "mailVendedor") String mail) {

		try {
			
			productoService.eliminarProducto(id, mail);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

		} catch (ProductoInexistenteException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
    }
    

	public ProductoService getProductoService() {
		return productoService;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

}
