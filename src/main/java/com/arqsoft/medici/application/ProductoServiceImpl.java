package com.arqsoft.medici.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.infrastructure.persistence.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private VendedorService vendedorService;
	
	@Override
	public void crearProducto(ProductoDTO request) {
		
		Producto producto = new Producto(request.getNombre(), request.getDescripcion(), request.getPrecio(), request.getStock(), request.getCategoria());
		Vendedor vendedor = vendedorService.obtenerVendedorByMail(request.getMailVendedor());
		producto.setVendedor(vendedor);
		
		productoRepository.insert(producto);
		
		vendedor.getProductosListados().add(producto);
		vendedorService.actualizarVendedor(vendedor);
		
	}

	public ProductoRepository getProductoRepository() {
		return productoRepository;
	}

	public void setProductoRepository(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	public VendedorService getVendedorService() {
		return vendedorService;
	}

	public void setVendedorService(VendedorService vendedorService) {
		this.vendedorService = vendedorService;
	}

}
