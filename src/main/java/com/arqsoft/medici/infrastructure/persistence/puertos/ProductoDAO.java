package com.arqsoft.medici.infrastructure.persistence.puertos;

import com.arqsoft.medici.domain.dto.FiltroBuscadorProducto;
import com.arqsoft.medici.domain.dto.ProductosPaginado;

public interface ProductoDAO {
	
	public ProductosPaginado buscarProductos(FiltroBuscadorProducto filtro);

}
