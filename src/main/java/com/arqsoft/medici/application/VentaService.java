package com.arqsoft.medici.application;

import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;

//Los puertos permiten acceder a la lógica de negocio.
public interface VentaService {

	void procesarVenta(RegistrarVentaDTO request);

}
