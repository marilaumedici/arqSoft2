package com.arqsoft.medici.application;

import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.dto.VendedorDatosDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorExistenteException;

public interface VendedorService {

	void crearVendedor(VendedorDTO request) throws InternalErrorException, FormatoEmailInvalidoException, VendedorExistenteException;

	Vendedor obtenerVendedorByMail(String mailVendedor);

	void actualizarVendedor(Vendedor vendedor);

	VendedorDatosDTO obtenerVendedor(String mail);

}
