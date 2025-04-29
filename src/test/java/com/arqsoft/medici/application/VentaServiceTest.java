package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.RegistrarVentaDTO;
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.domain.exceptions.ValidacionException;
import com.arqsoft.medici.domain.utils.ProductoCategoria;
import com.arqsoft.medici.domain.utils.UsuarioEstado;
import com.arqsoft.medici.infrastructure.persistence.VentaRepository;
import com.arqsoft.medici.domain.Venta;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {
	
	@InjectMocks
	private VentaServiceImpl ventaService;
	
	@Mock
	private ProductoServiceImpl productoService;

	@Mock
	private UsuarioServiceImpl usuarioService;
	
	@Mock
	private VentaRepository ventaRepository;
	
	@Captor
	private ArgumentCaptor<Venta> ventaCaptor;
	
	
	private String mailVendedor1 = "naturisteros@gmail.com";
	private String razonSoacialVendedor1 = "Tienda naturista Naturisteros";
	
	private String nombreP1 = "Pistachos 400gm";
	private String descripcionP1 = "Bolsa de pistachos tostados y salados 400gm";
	private double precioP1 = 14000;
	private int stockP1 = 35;
	private String codigoP1    = "680fff46b369023700f29045";
	private ProductoCategoria ALIMENTOS = ProductoCategoria.ALIMENTOS;

	private String email_comprador    		= "agussusu@gmal.com";
	private String nombre_comprador			= "Agustin";
	private String apellido_comprador 		= "Delpane";
	
	@Test
	public void procesarVentaOK() throws UsuarioNoEncontradoException, ProductoInexistenteException, ValidacionException {
		
		int cantidad = 10;
		
		Usuario usuarioBD = new Usuario(nombre_comprador, apellido_comprador, email_comprador, UsuarioEstado.BORRADO);
		when(usuarioService.obtenerUsuarioByID(email_comprador)).thenReturn(usuarioBD);
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		when(productoService.obtenerProductoByID(codigoP1)).thenReturn(producto);

		productoService.descontarStock(producto, cantidad );

		when(ventaRepository.insert(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		RegistrarVentaDTO request =  new RegistrarVentaDTO();
		request.setCantidad(cantidad);
		request.setMailComprador(email_comprador);
		request.setProductoId(codigoP1);
		assertDoesNotThrow(() -> { ventaService.procesarVenta(request); });

		verify(ventaRepository).insert(ventaCaptor.capture());
		
		Venta capturado = ventaCaptor.getValue();
		
	    assertEquals(cantidad, capturado.getCantidadComprada());
	    assertEquals(email_comprador, capturado.getMailComprador());
	    assertEquals(mailVendedor1, capturado.getMailVendedor());
	    assertEquals(cantidad * precioP1, capturado.getPrecioFinal());
	    assertEquals(precioP1, capturado.getPrecioIndividual());
	    assertEquals(codigoP1, capturado.getProductoId());

	}
	
	@Test
	public void procesarVentaInsuficienteStock() throws UsuarioNoEncontradoException, ProductoInexistenteException, ValidacionException {
		
		int cantidad = 10;
		int stockInsuficiente = 1;
		
		Usuario usuarioBD = new Usuario(nombre_comprador, apellido_comprador, email_comprador, UsuarioEstado.BORRADO);
		when(usuarioService.obtenerUsuarioByID(email_comprador)).thenReturn(usuarioBD);
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockInsuficiente, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		when(productoService.obtenerProductoByID(codigoP1)).thenReturn(producto);

		doThrow(new ValidacionException("La cantidad ingresada debe ser menor o igual al stock disponible.")).when(productoService).descontarStock(producto, cantidad );
		
		RegistrarVentaDTO request =  new RegistrarVentaDTO();
		request.setCantidad(cantidad);
		request.setMailComprador(email_comprador);
		request.setProductoId(codigoP1);
		assertThrows(ValidacionException.class, () -> { ventaService.procesarVenta(request); });


	}
	
	
	@Test
	public void procesarVentaCantidadSolicitadaCero() throws UsuarioNoEncontradoException, ProductoInexistenteException, ValidacionException {
		
		int cantidad = 0;
		int stockInsuficiente = 1;
		
		Usuario usuarioBD = new Usuario(nombre_comprador, apellido_comprador, email_comprador, UsuarioEstado.BORRADO);
		when(usuarioService.obtenerUsuarioByID(email_comprador)).thenReturn(usuarioBD);
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockInsuficiente, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		when(productoService.obtenerProductoByID(codigoP1)).thenReturn(producto);

		doThrow(new ValidacionException("La cantidad ingresada debe ser mayor o igual a 1.")).when(productoService).descontarStock(producto, cantidad );
		
		RegistrarVentaDTO request =  new RegistrarVentaDTO();
		request.setCantidad(cantidad);
		request.setMailComprador(email_comprador);
		request.setProductoId(codigoP1);
		assertThrows(ValidacionException.class, () -> { ventaService.procesarVenta(request); });


	}
	
	@Test
	public void procesarVentaUsuarioNoEncontrado() throws UsuarioNoEncontradoException, ProductoInexistenteException, ValidacionException {
		
		int cantidad = 10;

		doThrow(new UsuarioNoEncontradoException()).when(usuarioService).obtenerUsuarioByID(email_comprador);

		RegistrarVentaDTO request =  new RegistrarVentaDTO();
		request.setCantidad(cantidad);
		request.setMailComprador(email_comprador);
		request.setProductoId(codigoP1);
		assertThrows(UsuarioNoEncontradoException.class, () -> { ventaService.procesarVenta(request); });


	}
	
	@Test
	public void procesarVentaProductoNoEncontrado() throws UsuarioNoEncontradoException, ProductoInexistenteException, ValidacionException {
		
		int cantidad = 10;

		Usuario usuarioBD = new Usuario(nombre_comprador, apellido_comprador, email_comprador, UsuarioEstado.BORRADO);
		when(usuarioService.obtenerUsuarioByID(email_comprador)).thenReturn(usuarioBD);
		
		doThrow(new ProductoInexistenteException()).when(productoService).obtenerProductoByID(codigoP1);

		
		RegistrarVentaDTO request =  new RegistrarVentaDTO();
		request.setCantidad(cantidad);
		request.setMailComprador(email_comprador);
		request.setProductoId(codigoP1);
		assertThrows(ProductoInexistenteException.class, () -> { ventaService.procesarVenta(request); });


	}
	

}
