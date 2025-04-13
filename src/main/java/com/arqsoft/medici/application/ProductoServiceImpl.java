package com.arqsoft.medici.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.dto.ProductoResponseDTO;
import com.arqsoft.medici.domain.dto.ProductosVendedorDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
import com.arqsoft.medici.domain.utils.ProductoEstado;
import com.arqsoft.medici.infrastructure.persistence.ProductoRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private VendedorService vendedorService;
	
	@Override
	public ProductoResponseDTO crearProducto(ProductoDTO request) throws InternalErrorException, VendedorNoEncontradoException, ValidacionException, ProductoInexistenteException {
		
		if(StringUtils.isBlank(request.getMailVendedor())) {
			throw new InternalErrorException("El mail del vendedor no puede estar vacio.");
			
		}
		
		ProductoResponseDTO response = null;
		
		if(StringUtils.isNotBlank(request.getCodigoProducto())) {
			
			Optional<Producto> opcionalProducto = productoRepository.findById(request.getCodigoProducto());
			
			if(opcionalProducto.isEmpty()) {
				throw new ProductoInexistenteException();
				
			}
			
			Producto producto = opcionalProducto.get();
			
			if(producto.getEstado().equals(ProductoEstado.NO_DISPONIBLE)) {
				producto.setEstado(ProductoEstado.DISPONIBLE);
				
			}
						
			if(actualizarDatosProducto(request, producto)) {
				Producto p = productoRepository.save(producto);
				response = new ProductoResponseDTO(p.getProductoId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), p.getCategoria(), p.getEstado(), p.getVendedor().getMail());
				
			}
		}else {
			
			validarDatosCreacionProducto(request);
			
			Producto producto = new Producto(request.getNombre(), request.getDescripcion(), request.getPrecio(), request.getStock(), request.getCategoria());
			
			Vendedor vendedor = vendedorService.obtenerVendedorEntidad(request.getMailVendedor());
			producto.setVendedor(vendedor);
			
			Producto p = productoRepository.insert(producto);
			
			vendedor.getProductosListados().add(producto);
			vendedorService.actualizarVendedor(vendedor);
			
			response = new ProductoResponseDTO(p.getProductoId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), p.getCategoria(),p.getEstado(), p.getVendedor().getMail());

		}
		
		return response;
	}
	
	@Override
	public void modificarProducto(String id, ProductoDTO request) throws InternalErrorException, ProductoInexistenteException {
		
		if(StringUtils.isBlank(id)) {
			throw new InternalErrorException("El id del producto no puede estar vacio.");
			
		}
		if(StringUtils.isBlank(request.getMailVendedor())) {
			throw new InternalErrorException("El mail del vendedor no puede estar vacio.");
			
		}
		
		Optional<Producto> opcionalProducto = productoRepository.findById(id);
		
		if(existeProducto(opcionalProducto)) {
			
			Producto producto = opcionalProducto.get();

			if(actualizarDatosProducto(request, producto)) {
				productoRepository.save(producto);
				
			}
		}else {
			throw new ProductoInexistenteException();
			
		}
	}

	
	@Override
	public void eliminarProducto(String id, String mail) throws InternalErrorException, ProductoInexistenteException {
		
		if(StringUtils.isBlank(mail)) {
			throw new InternalErrorException("El mail del vendedor no puede estar vacio.");
			
		}
		if(StringUtils.isBlank(id)) {
			throw new InternalErrorException("El id del producto no puede estar vacio.");
			
		}
		
		Optional<Producto> opcionalProducto = productoRepository.findById(id);
		
		if(opcionalProducto.isPresent()) {
			
			Producto producto = opcionalProducto.get();
			
			validarProductoMismoVendedor(producto.getVendedor().getMail(), mail, "Un vendedor no puede eliminar el producto de otro vendedor.");
			
			producto.setEstado(ProductoEstado.NO_DISPONIBLE);
			
			productoRepository.save(producto);
			
		}else {
			throw new ProductoInexistenteException();
		}
	}
	
	@Override
	public ProductosVendedorDTO obtenerProductosVendedor(String mailVendedor, Integer pagina, Integer size) throws InternalErrorException {
		
		if(StringUtils.isBlank(mailVendedor)) {
			throw new InternalErrorException("El mail del vendedor no puede estar vacio.");
			
		}

		List<ProductoResponseDTO> response = new ArrayList<ProductoResponseDTO>();
		Pageable pageable = PageRequest.of(pagina, size);
	    //Page<Producto> productosPage = productoRepository.findAll(pageable);
	    Page<Producto> productosPage = productoRepository.findByVendedor_Mail(mailVendedor, pageable);
	    List<Producto> productos = productosPage.getContent();
	    
	    for(Producto p : productos) {
	    	ProductoResponseDTO pDTO = new ProductoResponseDTO(p.getProductoId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), p.getCategoria(),p.getEstado(), p.getVendedor().getMail());
	    	response.add(pDTO);
	    }
	    return new ProductosVendedorDTO(productosPage.getNumber(), productosPage.getTotalPages(), productosPage.getTotalElements(), response);
	}
	
	private void validarProductoMismoVendedor(String mailVendedorProducto, String mailVendedorIngresado, String mensaje) throws InternalErrorException {
		
		if(! mailVendedorProducto.equals(mailVendedorIngresado)) {
			throw new InternalErrorException(mensaje);
			
		}
	}
	
	private boolean existeProducto(Optional<Producto> opcionalProducto) {
		
		return opcionalProducto.isPresent() && opcionalProducto.get().getEstado().equals(ProductoEstado.DISPONIBLE);
		
	}
	
	/**
	 * Metodo que actualiza los datos del ente producto con los que hay en el dto y devuelve un booleano para indicar si se actualizo algun atrubuto.
	 * De tener mail vendedores distintos el ente y el dto, dara error.
	 * @param request
	 * @param producto
	 * @return Si actualizo algun campo para actualizar del ente
	 * @throws InternalErrorException
	 */
	private boolean actualizarDatosProducto(ProductoDTO request, Producto producto) throws InternalErrorException {
		
		validarProductoMismoVendedor(producto.getVendedor().getMail(), request.getMailVendedor(), "Un vendedor no puede modificar el producto de otro vendedor.");

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
		return cambio;
	}
	
	private void validarDatosCreacionProducto(ProductoDTO request) throws ValidacionException, InternalErrorException {
		
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
