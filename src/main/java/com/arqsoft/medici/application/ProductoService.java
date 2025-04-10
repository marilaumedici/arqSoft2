package com.arqsoft.medici.application;

import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;

public interface ProductoService {
	
	public void crearProducto(ProductoDTO request) throws InternalErrorException, VendedorNoEncontradoException, ValidacionException;

}
