package com.arqsoft.medici.application;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arqsoft.medici.domain.Venta;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.infrastructure.persistence.VentaRepository;
import io.micrometer.common.util.StringUtils;

@Service
public class VentaServiceImpl  implements VentaService {
	
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private VendedorService vendedorService;
	
	@Autowired
	private VentaRepository ventaRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void procesarVenta(RegistrarVentaDTO request) throws InternalErrorException, ValidacionException, ProductoInexistenteException, UsuarioNoEncontradoException {
		
		if(StringUtils.isBlank(request.getProductoId())) {
			throw new InternalErrorException("El id del producto no puede estar vacio.");
			
		}
		if(StringUtils.isBlank(request.getMailComprador())) {
			throw new InternalErrorException("El mail del comprador no puede estar vacio.");
			
		}
		
		usuarioService.obtenerUsuarioByID(request.getMailComprador());
		
		Producto producto = productoService.obtenerProductoByID(request.getProductoId());
		
		Vendedor vendedor = producto.getVendedor();
		
		Venta venta = new Venta(request.getProductoId(), vendedor.getMail(), request.getMailComprador(), new Date(),
				producto.getPrecio(), producto.getPrecio() * request.getCantidad(), request.getCantidad());
		
		ventaRepository.insert(venta);
		
		productoService.descontarStock(producto, request.getCantidad());
		
	}

	public ProductoService getProductoService() {
		return productoService;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

	public VendedorService getVendedorService() {
		return vendedorService;
	}

	public void setVendedorService(VendedorService vendedorService) {
		this.vendedorService = vendedorService;
	}

	public VentaRepository getVentaRepository() {
		return ventaRepository;
	}

	public void setVentaRepository(VentaRepository ventaRepository) {
		this.ventaRepository = ventaRepository;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}


}
