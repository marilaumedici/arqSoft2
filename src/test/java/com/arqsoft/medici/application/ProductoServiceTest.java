package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.arqsoft.medici.domain.exceptions.ProductoInexistenteException;
import com.arqsoft.medici.domain.utils.ProductoCategoria;
import com.arqsoft.medici.domain.utils.ProductoEstado;
import com.arqsoft.medici.infrastructure.persistence.ProductoRepository;
import com.arqsoft.medici.infrastructure.persistence.puertos.ProductoDAO;
import com.arqsoft.medici.domain.dto.FiltroBuscadorProducto;
import java.util.List;
import java.util.ArrayList;
import com.arqsoft.medici.domain.dto.ProductosPaginado;
import com.arqsoft.medici.domain.dto.ProductosVendedorDTO;

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
	
	@Mock
	private ProductoDAO productoDAO;
	
	
	private String nombreP1 = "Pistachos 400gm";
	private String descripcionP1 = "Bolsa de pistachos tostados y salados 400gm";
	private double precioP1 = 14000;
	private int stockP1 = 35;
	private String codigoP1 = "12334454356";
	
	private String nombreP1_nuevo = "Pistachos 350gm";
	private String descripcionP1_nuevo = "Bolsita de pistachos tostados y salados 400gm";
	private double precioP1_nuevo = 13000;
	private int stockP1_nuevo = 50;
	
	private ProductoCategoria ALIMENTOS = ProductoCategoria.ALIMENTOS;
	private ProductoCategoria CONGELADOS = ProductoCategoria.CONGELADOS;
	private ProductoCategoria TECNOLOGIA = ProductoCategoria.TECNOLOGIA;

	
	private String mailVendedor1 = "naturisteros@gmail.com";
	private String razonSoacialVendedor1 = "Tienda naturista Naturisteros";

	private String mailVendedor2 = "juarezconfor@gmail.com";
	private String razonSoacialVendedor2 = "Juarez Confort";
	
	private String nombreP2 = "Microondas BGH";
	private String descripcionP2 = "Es un microondas grande";
	private double precioP2 = 200000;
	private int stockP2 = 40;
	private String codigoP2 = "12334454357";
	
	private String nombreP3 = "Heladera Patrick gris";
	private String descripcionP3 = "Es una healdera patrick gris medianita";
	private double precioP3 = 400000;
	private int stockP3 = 20;
	private String codigoP3 = "12334454358";
	
	private String nombreP4 = "Lavasecarropas longive blanco";
	private String descripcionP4 = "Es un lavasecarropas genial";
	private double precioP4 = 350000;
	private int stockP4 = 60;
	private String codigoP4 = "12334454359";
	
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
	
	@Test
	public void testCrearProductoInexistenteConCodigo() {
		
		Optional<Producto> productoOpcional = Optional.empty(); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);

		ProductoDTO request = new ProductoDTO(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS, mailVendedor1);
		assertThrows(ProductoInexistenteException.class, () -> {  productoService.crearProducto(request); });
		
	}
	
	@Test
	public void testModificarProductoExistenteOk() {
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		Optional<Producto> productoOpcional = Optional.of(producto); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);

		when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ProductoDTO request = new ProductoDTO(codigoP1, nombreP1_nuevo, descripcionP1_nuevo, precioP1_nuevo, stockP1_nuevo, CONGELADOS, mailVendedor1);
		assertDoesNotThrow(() -> { productoService.modificarProducto(request); });
		
		verify(productoRepository, times(1)).findById(codigoP1);
		verify(productoRepository, times(1)).save(any(Producto.class));
		verify(productoRepository).save(productoCaptor.capture());
		
		Producto capturado = productoCaptor.getValue();

	    assertEquals(nombreP1_nuevo, capturado.getNombre());
	    assertEquals(descripcionP1_nuevo, capturado.getDescripcion());
	    assertEquals(CONGELADOS, capturado.getCategoria());
	    assertEquals(ProductoEstado.DISPONIBLE, capturado.getEstado());
	    assertEquals(precioP1_nuevo, capturado.getPrecio());
	    assertEquals(stockP1_nuevo, capturado.getStock());
	    assertEquals(mailVendedor1, capturado.getVendedor().getMail());
		
		
	}
	

	@Test
	public void testModificarProductoExistenteDiferenteVendedor() {
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		Optional<Producto> productoOpcional = Optional.of(producto); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);

		ProductoDTO request = new ProductoDTO(codigoP1, nombreP1_nuevo, descripcionP1_nuevo, precioP1_nuevo, stockP1_nuevo, ALIMENTOS, mailVendedor2);
		assertThrows(InternalErrorException.class, () -> {  productoService.modificarProducto(request); });

		verify(productoRepository, times(1)).findById(codigoP1);
		
	}
	
	@Test
	public void testModificarProductoInexistente() {
		
		Optional<Producto> productoOpcional = Optional.empty(); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);

		ProductoDTO request = new ProductoDTO(codigoP1, nombreP1_nuevo, descripcionP1_nuevo, precioP1_nuevo, stockP1_nuevo, ALIMENTOS, mailVendedor1);
		assertThrows(ProductoInexistenteException.class, () -> {  productoService.modificarProducto(request); });

		verify(productoRepository, times(1)).findById(codigoP1);
		
	}
	
	@Test
	public void testModificarProductoMailVendedorVacio() {
		

		ProductoDTO request = new ProductoDTO(codigoP1, nombreP1_nuevo, descripcionP1_nuevo, precioP1_nuevo, stockP1_nuevo, ALIMENTOS, "");
		assertThrows(InternalErrorException.class, () -> {  productoService.modificarProducto(request); });

		
	}
	
	@Test
	public void testModificarProductoMailCodigoProductoVacio() {
		
		ProductoDTO request = new ProductoDTO("", nombreP1_nuevo, descripcionP1_nuevo, precioP1_nuevo, stockP1_nuevo, ALIMENTOS, mailVendedor1);
		assertThrows(InternalErrorException.class, () -> {  productoService.modificarProducto(request); });

	}
	
	@Test
	public void testEliminarProductoExistente() {
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		Optional<Producto> productoOpcional = Optional.of(producto); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);
		
		when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

		assertDoesNotThrow(() -> { productoService.eliminarProducto(codigoP1, mailVendedor1); });

		verify(productoRepository, times(1)).findById(codigoP1);
		verify(productoRepository, times(1)).save(any(Producto.class));
		verify(productoRepository).save(productoCaptor.capture());
		
		Producto capturado = productoCaptor.getValue();

	    assertEquals(nombreP1, capturado.getNombre());
	    assertEquals(descripcionP1, capturado.getDescripcion());
	    assertEquals(ALIMENTOS, capturado.getCategoria());
	    assertEquals(ProductoEstado.NO_DISPONIBLE, capturado.getEstado());
	    assertEquals(precioP1, capturado.getPrecio());
	    assertEquals(stockP1, capturado.getStock());
	    assertEquals(mailVendedor1, capturado.getVendedor().getMail());
		
	}
	
	@Test
	public void testEliminarProductoInexistenteEnBase() {
		
		Optional<Producto> productoOpcional = Optional.empty(); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);
		
		assertThrows(ProductoInexistenteException.class, () -> {  productoService.eliminarProducto(codigoP1, mailVendedor1); });

		verify(productoRepository, times(1)).findById(codigoP1);
		
	}
	
	@Test
	public void testEliminarProductoMailVendedorVacio() {

		assertThrows(InternalErrorException.class, () -> {  productoService.eliminarProducto(codigoP1, ""); });

	}
	
	@Test
	public void testEliminarProductoCodigoProductoVacio() {

		assertThrows(InternalErrorException.class, () -> {  productoService.eliminarProducto("", mailVendedor1); });

	}
	
	
	@Test
	public void testEliminarProductoDeOtroVendedor() {
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		Optional<Producto> productoOpcional = Optional.of(producto); 
		when(productoRepository.findById(codigoP1)).thenReturn(productoOpcional);
		
		assertThrows(InternalErrorException.class, () -> {  productoService.eliminarProducto(codigoP1, mailVendedor2); });

		verify(productoRepository, times(1)).findById(codigoP1);
		
	}
	
	
	@Test
	public void testBuscarTodosProductosSinFiltro() {
		
		List<Producto> productos = new ArrayList<Producto>();
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		
		Producto producto2 = new Producto(codigoP2, nombreP2, descripcionP2, precioP2, stockP2, TECNOLOGIA);
		Vendedor vendedor2 = new Vendedor(mailVendedor2, razonSoacialVendedor2);
		producto2.setVendedor(vendedor2);
		
		Producto producto3 = new Producto(codigoP3, nombreP3, descripcionP3, precioP3, stockP3, TECNOLOGIA);
		producto3.setVendedor(vendedor2);
		
		Producto producto4 = new Producto(codigoP4, nombreP4, descripcionP4, precioP4, stockP4, TECNOLOGIA);
		producto4.setVendedor(vendedor2);
		
		productos.add(producto4);
		productos.add(producto3);
		productos.add(producto2);
		productos.add(producto);

		ProductosPaginado pagina =  new ProductosPaginado(productos, 0, 4, 1);
		
		FiltroBuscadorProducto dto = new FiltroBuscadorProducto();
		when(productoDAO.buscarProductos(dto)).thenReturn(pagina);

		
		assertDoesNotThrow(() -> {
			
			ProductosVendedorDTO response = productoService.obtenerProductosFiltrados(dto);
			assertNotNull(response.getProductos());
		    assertEquals(response.getProductos().size(), 4);
		    assertEquals(response.getPaginaActual(), 0);
		    assertEquals(response.getTotalResultados(), 4);

		});

	}
	
	
	@Test
	public void testBuscarTodosProductosFiltraCategoria() {
		
		List<Producto> productos = new ArrayList<Producto>();
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		
		Producto producto2 = new Producto(codigoP2, nombreP2, descripcionP2, precioP2, stockP2, TECNOLOGIA);
		Vendedor vendedor2 = new Vendedor(mailVendedor2, razonSoacialVendedor2);
		producto2.setVendedor(vendedor2);
		
		Producto producto3 = new Producto(codigoP3, nombreP3, descripcionP3, precioP3, stockP3, TECNOLOGIA);
		producto3.setVendedor(vendedor2);
		
		Producto producto4 = new Producto(codigoP4, nombreP4, descripcionP4, precioP4, stockP4, TECNOLOGIA);
		producto4.setVendedor(vendedor2);
		
		productos.add(producto4);
		productos.add(producto3);
		productos.add(producto2);
		//productos.add(producto);

		ProductosPaginado pagina =  new ProductosPaginado(productos, 0, 3, 1);
		
		FiltroBuscadorProducto dto = new FiltroBuscadorProducto();
		dto.setCategoria(TECNOLOGIA);
		when(productoDAO.buscarProductos(dto)).thenReturn(pagina);

		
		assertDoesNotThrow(() -> {
			
			ProductosVendedorDTO response = productoService.obtenerProductosFiltrados(dto);
			assertNotNull(response.getProductos());
		    assertEquals(response.getProductos().size(), 3);
		    assertEquals(response.getPaginaActual(), 0);
		    assertEquals(response.getTotalResultados(), 3);
		    assertEquals(response.getProductos().get(0).getCategoria(), TECNOLOGIA);
		    assertEquals(response.getProductos().get(1).getCategoria(), TECNOLOGIA);
		    assertEquals(response.getProductos().get(2).getCategoria(), TECNOLOGIA);


		});

	}
	
	@Test
	public void testBuscarTodosProductosFiltradoPrecio() {
		
		List<Producto> productos = new ArrayList<Producto>();
		
		Producto producto = new Producto(codigoP1, nombreP1, descripcionP1, precioP1, stockP1, ALIMENTOS);
		Vendedor vendedor = new Vendedor(mailVendedor1, razonSoacialVendedor1);
		producto.setVendedor(vendedor);
		
		Producto producto2 = new Producto(codigoP2, nombreP2, descripcionP2, precioP2, stockP2, TECNOLOGIA);
		Vendedor vendedor2 = new Vendedor(mailVendedor2, razonSoacialVendedor2);
		producto2.setVendedor(vendedor2);
		
		Producto producto3 = new Producto(codigoP3, nombreP3, descripcionP3, precioP3, stockP3, TECNOLOGIA);
		producto3.setVendedor(vendedor2);
		
		Producto producto4 = new Producto(codigoP4, nombreP4, descripcionP4, precioP4, stockP4, TECNOLOGIA);
		producto4.setVendedor(vendedor2);
		
		//productos.add(producto4);
		//productos.add(producto3);
		productos.add(producto2);
		productos.add(producto);

		ProductosPaginado pagina =  new ProductosPaginado(productos, 0, 2, 1);
		
		FiltroBuscadorProducto dto = new FiltroBuscadorProducto();
		dto.setPrecioMinimo(10000);
		dto.setPrecioMaximo(300000);
		when(productoDAO.buscarProductos(dto)).thenReturn(pagina);

		
		assertDoesNotThrow(() -> {
			
			ProductosVendedorDTO response = productoService.obtenerProductosFiltrados(dto);
			assertNotNull(response.getProductos());
		    assertEquals(response.getProductos().size(), 2);
		    assertEquals(response.getPaginaActual(), 0);
		    assertEquals(response.getTotalResultados(), 2);

		});

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
