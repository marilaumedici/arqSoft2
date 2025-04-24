package com.arqsoft.medici.application;

import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.dto.FiltroBuscadorProducto;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.dto.ProductoResponseDTO;
import com.arqsoft.medici.domain.dto.ProductosVendedorDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;

public interface ProductoService {
	
	public ProductoResponseDTO crearProducto(ProductoDTO request) throws InternalErrorException, VendedorNoEncontradoException, ValidacionException, ProductoInexistenteException;

	public void modificarProducto(ProductoDTO request) throws InternalErrorException, ProductoInexistenteException;

	public void eliminarProducto(String id, String mail) throws InternalErrorException, ProductoInexistenteException;
	
	public ProductosVendedorDTO obtenerProductosVendedor(String mail, Integer pagina, Integer size) throws InternalErrorException;

	/**
	 * Recibe un productoId y devuelve un producto DISPONIBLE
	 * @param productoId
	 * @return Producto
	 * @throws ProductoInexistenteException
	 */
	public Producto obtenerProductoByID(String productoId) throws ProductoInexistenteException;

	public void descontarStock(Producto producto, Integer cantidad) throws ValidacionException;

	public ProductosVendedorDTO obtenerProductosFiltrados(FiltroBuscadorProducto request);

}
