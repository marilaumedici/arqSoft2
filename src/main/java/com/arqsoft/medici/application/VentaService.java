package com.arqsoft.medici.application;


import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;


public interface VentaService {

	void procesarVenta(RegistrarVentaDTO request) throws InternalErrorException, ValidacionException, ProductoInexistenteException, UsuarioNoEncontradoException;

}
