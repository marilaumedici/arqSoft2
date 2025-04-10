package com.arqsoft.medici.application;

import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.dto.VendedorDatosDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorExistenteException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;

public interface VendedorService {

	void crearVendedor(VendedorDTO request) throws InternalErrorException, FormatoEmailInvalidoException, VendedorExistenteException;

	void modificarVendedor(VendedorDTO request) throws InternalErrorException, VendedorNoEncontradoException;
	
	void eliminarVendedor(String mail) throws InternalErrorException, VendedorNoEncontradoException;
	
	Vendedor obtenerVendedorEntidad(String mailVendedor) throws InternalErrorException, VendedorNoEncontradoException;

	void actualizarVendedor(Vendedor vendedor);

	VendedorDatosDTO obtenerVendedor(String mail);



}
