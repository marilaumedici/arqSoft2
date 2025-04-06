package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
