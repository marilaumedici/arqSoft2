package com.arqsoft.medici.infrastructure.rest.puertos;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arqsoft.medici.domain.dto.FiltroBuscadorProducto;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.dto.ProductoResponseDTO;
import com.arqsoft.medici.domain.dto.ProductosVendedorDTO;
import io.swagger.annotations.ApiOperation;


@RequestMapping("/producto")
public interface ProductoController {
	
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "crear_producto", value = "Crea un producto")
	public ProductoResponseDTO crearProducto(@RequestBody ProductoDTO request);
    
    @PutMapping(path = "/{productoId}", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "modificar_producto", value = "Modifica los datos de un producto")
	public void modificarProducto(@PathVariable(value = "productoId") String id, @RequestBody ProductoDTO request);
    
    @DeleteMapping(path = "/{productoId}/{mailVendedor}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "borrar_producto", value = "Borra un producto logicamente")
	public void eliminarProducto(@PathVariable(value = "productoId") String id, @PathVariable(value = "mailVendedor") String mail);

    @GetMapping(path = "/{mailVendedor}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "get_productos_vendedor", value = "Obtiene los productos de un vendedor")
	public ProductosVendedorDTO obtenerProductosVendedor(@PathVariable(value = "mailVendedor") String mail, @RequestParam(name = "pagina", defaultValue = "0")  Integer page, @RequestParam(name = "size", defaultValue = "999")  Integer elementos);
    	
    @PostMapping(path = "/disponibles", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "get_productos_disponibles", value = "Obtiene productos por varios filtros")
	public ProductosVendedorDTO obtenerProductosFiltro(@RequestBody FiltroBuscadorProducto request);
    	

}
