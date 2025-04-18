package com.arqsoft.medici.infrastructure.rest.puertos;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arqsoft.medici.domain.dto.UsuarioDTO;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/usuario")
public interface UsuarioController {
	
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "crear_usuario", value = "Crea un usuario")
	public void crearUsuario(@RequestBody UsuarioDTO request);
    
    @PutMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "modificar_usuario", value = "Modifica un usuario")
	public void modificarUsuario(@RequestBody UsuarioDTO request);
    
    @DeleteMapping(path = "/{email}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "borrar_usuario", value = "Borra un usuario logicamente")
	public void eliminarUsuario(@PathVariable(value = "email") String mail);

}
