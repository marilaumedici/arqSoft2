package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.arqsoft.medici.application.UsuarioService;
import com.arqsoft.medici.domain.dto.UsuarioDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.infrastructure.rest.puertos.UsuarioController;


@RestController
public class UsuarioControllerImpl implements UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Override
	public void crearUsuario(UsuarioDTO request) {
    	
    	try {
			usuarioService.crearUsuario(request);
			
		} catch (UsuarioExistenteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario "+request.getMail()+" ya existe.", e);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

		} catch (FormatoEmailInvalidoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email debe poseer un formato valido.", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}	
    }
    
	@Override
	public void modificarUsuario(UsuarioDTO request) {
    	
			try {
				usuarioService.modificarUsuario(request);
				
			} catch (UsuarioNoEncontradoException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+request.getMail()+".", e);

			} catch (InternalErrorException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
				
			}
    }
	
	@Override
	public void eliminarUsuario(String mail) {
    	
			try {
				usuarioService.eliminarUsuario(mail);
				
			} catch (InternalErrorException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

			} catch (UsuarioNoEncontradoException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+mail+".", e);

			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
				
			}
    }
	

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

}
