package com.arqsoft.medici.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
import com.arqsoft.medici.domain.utils.ProductoEstado;
import com.arqsoft.medici.infrastructure.persistence.ProductoRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private VendedorService vendedorService;
	
	@Override
	public void crearProducto(ProductoDTO request) throws InternalErrorException, VendedorNoEncontradoException, ValidacionException {
		
		if(StringUtils.isBlank(request.getNombre())) {
			throw new ValidacionException("Debe ingresar un nombre para el producto.");
			
		}
		if(StringUtils.isBlank(request.getDescripcion())) {
			throw new ValidacionException("Debe ingresar una breve descripcion para el producto.");
			
		}
		if(StringUtils.isBlank(String.valueOf(request.getPrecio()))) {
			throw new ValidacionException("Debe asignarle un precio al producto.");
			
		}
		if(StringUtils.isBlank(String.valueOf(request.getStock()))) {
			throw new ValidacionException("Debe ingresar un stock inicial para el producto.");
			
		}
		if(StringUtils.isBlank(String.valueOf(request.getCategoria()))) {
			throw new ValidacionException("Debe ingresar una categoria para el producto.");
			
		}
		
		Producto producto = new Producto(request.getNombre(), request.getDescripcion(), request.getPrecio(), request.getStock(), request.getCategoria());
		
		Vendedor vendedor = vendedorService.obtenerVendedorEntidad(request.getMailVendedor());
		producto.setVendedor(vendedor);
		
		productoRepository.insert(producto);
		
		vendedor.getProductosListados().add(producto);
		vendedorService.actualizarVendedor(vendedor);
		
	}
	
	@Override
	public void modificarProducto(String id, ProductoDTO request) throws InternalErrorException, ProductoInexistenteException {
		
		if(StringUtils.isBlank(id)) {
			throw new InternalErrorException("El id del producto no puede estar vacio.");
			
		}
		
		Optional<Producto> opcionalProducto = productoRepository.findById(id);
		
		if(existeProducto(opcionalProducto)) {
			
			Producto producto = opcionalProducto.get();
			boolean cambio = false;
			
			if(StringUtils.isNotBlank(request.getNombre())) {
				producto.setNombre(request.getNombre());
				cambio = true;
			}
			if(StringUtils.isNotBlank(request.getDescripcion())) {
				producto.setDescripcion(request.getDescripcion());
				cambio = true;
			}
			if(StringUtils.isNotBlank(String.valueOf(request.getPrecio()))) {
				producto.setPrecio(request.getPrecio());
				cambio = true;
			}
			if(StringUtils.isNotBlank(String.valueOf(request.getStock()))) {
				producto.setStock(request.getStock());
				cambio = true;
			}
			if(StringUtils.isNotBlank(String.valueOf(request.getStock()))) {
				producto.setStock(request.getStock());
				cambio = true;
			}
			if(StringUtils.isNotBlank(String.valueOf(request.getCategoria()))) {
				producto.setCategoria(request.getCategoria());
				cambio = true;
			}
			
			if(cambio) {
				productoRepository.save(producto);
				
			}
		}else {
			throw new ProductoInexistenteException();
			
		}
	}
	
	private boolean existeProducto(Optional<Producto> opcionalProducto) {
		
		return opcionalProducto.isPresent() && opcionalProducto.get().getEstado().equals(ProductoEstado.DISPONIBLE);
		
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
