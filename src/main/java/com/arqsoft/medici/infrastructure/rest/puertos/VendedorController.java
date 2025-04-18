package com.arqsoft.medici.infrastructure.rest.puertos;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.dto.VendedorDatosDTO;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/vendedor")
public interface VendedorController {
	
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "crear_vendedor", value = "Crea un vendedor")
	public void crearVendedor(@RequestBody VendedorDTO request);
    
    @PutMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "modificar_vendedor", value = "Modifica los datos de un vendedor")
	public void modificarVendedor(@RequestBody VendedorDTO request);
    
    @DeleteMapping(path = "/{email}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "borrar_vendedor", value = "Borra un vendedor logicamente")
	public void eliminarVendedor(@PathVariable(value = "email") String mail);
    
    @GetMapping(path = "/{vendedorMail}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "obtener_vendedor", value = "Obtiene un vendedor")
	public VendedorDatosDTO obtenerVendedor(@PathVariable(value = "vendedorMail") String mail);

}
