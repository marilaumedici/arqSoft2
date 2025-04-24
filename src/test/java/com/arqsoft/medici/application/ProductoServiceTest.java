package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
import com.arqsoft.medici.domain.utils.ProductoCategoria;
import com.arqsoft.medici.domain.utils.ProductoEstado;
import com.arqsoft.medici.infrastructure.persistence.ProductoRepository;;


@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
	
	@InjectMocks
	private ProductoServiceImpl productoService;

	@Mock
	private VendedorServiceImpl vendedorService;
	
	@Mock
	private ProductoRepository productoRepository;
	
	@Captor
	private ArgumentCaptor<Producto> productoCaptor;
	
	private String nombreP1 = "Pistachos 400gm";
	private String descripcionP1 = "Bolsa de pistachos tostados y salados 400gm";
	private double precioP1 = 14000;
	private int stockP1 = 35;
	private String codigoP1 = "12334454356";
	
	private ProductoCategoria ALIMENTOS = ProductoCategoria.ALIMENTOS;
	
	private String mailVendedor1 = "naturisteros@gmail.com";
	private String razonSoacialVendedor1 = "Tienda naturista Naturisteros";


	
	@Test
	public void testCrearProductoInexistenteOK() throws InternalErrorException, VendedorNoEncontradoException {
		
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		when(vendedorService.obtenerVendedorEntidad(mailVendedor1)).thenReturn(vendedor);
		
		when(productoRepository.insert(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ProductoDTO request = new ProductoDTO(nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS, mailVendedor1);
		assertDoesNotThrow(() -> { productoService.crearProducto(request); });

		verify(vendedorService, times(1)).obtenerVendedorEntidad(mailVendedor1);
		verify(productoRepository).insert(productoCaptor.capture());
		
		Producto capturado = productoCaptor.getValue();

	    assertEquals(nombreP1, capturado.getNombre());
	    assertEquals(descripcionP1, capturado.getDescripcion());
	    assertEquals(ALIMENTOS, capturado.getCategoria());
	    assertEquals(ProductoEstado.DISPONIBLE, capturado.getEstado());
	    assertEquals(precioP1, capturado.getPrecio());
	    assertEquals(stockP1, capturado.getStock());
	    assertEquals(mailVendedor1, capturado.getVendedor().getMail());
		
	}
	
	@Test
	public void testCrearProductoExistenteNoDisponible() throws InternalErrorException, VendedorNoEncontradoException {
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		producto.setEstado(ProductoEstado.NO_DISPONIBLE);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		Optional<Producto> productoOpcional = Optional.of(producto); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);

		when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ProductoDTO request = new ProductoDTO(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS, mailVendedor1);
		assertDoesNotThrow(() -> { productoService.crearProducto(request); });

		verify(productoRepository, times(1)).findById(codigoP1);
		verify(productoRepository, times(1)).save(any(Producto.class));
		verify(productoRepository).save(productoCaptor.capture());
		
		Producto capturado = productoCaptor.getValue();

	    assertEquals(nombreP1, capturado.getNombre());
	    assertEquals(descripcionP1, capturado.getDescripcion());
	    assertEquals(ALIMENTOS, capturado.getCategoria());
	    assertEquals(ProductoEstado.DISPONIBLE, capturado.getEstado());
	    assertEquals(precioP1, capturado.getPrecio());
	    assertEquals(stockP1, capturado.getStock());
	    assertEquals(mailVendedor1, capturado.getVendedor().getMail());
		
	}
	
	public ArgumentCaptor<Producto> getProductoCaptor() {
		return productoCaptor;
	}

	public void setProductoCaptor(ArgumentCaptor<Producto> productoCaptor) {
		this.productoCaptor = productoCaptor;
	}

	public ProductoServiceImpl getProductoService() {
		return productoService;
	}

	public void setProductoService(ProductoServiceImpl productoService) {
		this.productoService = productoService;
	}

	public VendedorServiceImpl getVendedorService() {
		return vendedorService;
	}

	public void setVendedorService(VendedorServiceImpl vendedorService) {
		this.vendedorService = vendedorService;
	}

}
