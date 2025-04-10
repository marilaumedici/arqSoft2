package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.arqsoft.medici.application.VendedorService;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.dto.VendedorDatosDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorExistenteException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {
	
	@Autowired
	private VendedorService vendedorService;
	
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "crear_vendedor", value = "Crea un vendedor")
	public void crearUsuario(@RequestBody VendedorDTO request) {

    	try {
    		
			vendedorService.crearVendedor(request);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
			
		} catch (FormatoEmailInvalidoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email debe poseer un formato valido.", e);
			
		} catch (VendedorExistenteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El vendedor "+request.getMail()+" ya existe.", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
    }
    
    @PutMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "modificar_vendedor", value = "Modifica los datos de un vendedor")
	public void modificarVendedor(@RequestBody VendedorDTO request) {
    	
    	try {
    		
			vendedorService.modificarVendedor(request);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
			
		} catch (VendedorNoEncontradoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el vendedor "+request.getMail()+".", e);
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
    }
    
    @DeleteMapping(path = "/{email}", 
    //consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "borrar_vendedor", value = "Borra un vendedor logicamente")
	public void eliminarUsuario(@PathVariable(value = "email") String mail) {

			try {
				
				vendedorService.eliminarVendedor(mail);
				
			} catch (InternalErrorException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
				
			} catch (VendedorNoEncontradoException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el vendedor "+mail+".", e);

			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
				
			}
    }
	
    
    @GetMapping(path = "/{vendedorMail}", 
    //consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "obtener_vendedor", value = "Obtiene un vendedor")
	public VendedorDatosDTO obtenerVendedor(@PathVariable(value = "vendedorMail") String mail) {
		
    	return vendedorService.obtenerVendedor(mail);
    	
    }

	public VendedorService getVendedorService() {
		return vendedorService;
	}

	public void setVendedorService(VendedorService vendedorService) {
		this.vendedorService = vendedorService;
	}

}
