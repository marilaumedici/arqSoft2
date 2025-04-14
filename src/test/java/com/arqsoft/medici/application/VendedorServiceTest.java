package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorExistenteException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
import com.arqsoft.medici.domain.utils.VendedorEstado;
import com.arqsoft.medici.infrastructure.persistence.VendedorRepository;

@ExtendWith(MockitoExtension.class)
public class VendedorServiceTest {
	
	@Mock
	private VendedorRepository vendedorRepository;
	
	@InjectMocks
	private VendedorServiceImpl vendedorService;
	
	private String email_invalido = "agustus";
	private String email = "agustus@gmail.com";
	private String razonSoacial = "Agustus Delpino";
	private String razonSoacial_Nueva = "Agustus Delpino Empresas";
	
	@Captor
	private ArgumentCaptor<Vendedor> vendedorCaptor;
	
	@Test
	public void testCrearVendedorInexistenteOk() {
		
		Optional<Vendedor> vendedorOpcional = Optional.empty(); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		
		when(vendedorRepository.insert(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		VendedorDTO request = new VendedorDTO(email, razonSoacial);
		assertDoesNotThrow(() -> { vendedorService.crearVendedor(request); });
		
		verify(vendedorRepository, times(1)).findById(email);
		verify(vendedorRepository).insert(vendedorCaptor.capture());
		
		Vendedor capturado = vendedorCaptor.getValue();
		
	    assertEquals(email, capturado.getMail());
	    assertEquals(razonSoacial, capturado.getRazonSocial());
	    assertEquals(VendedorEstado.ACTIVO, capturado.getEstado());
		
	}
	
	@Test
	public void testCrearVendedorExistenteBorradoOK() {
		
		Vendedor vendedorBD = new Vendedor(email, razonSoacial);
		vendedorBD.setEstado(VendedorEstado.BORRADO);
		Optional<Vendedor> vendedorOpcional = Optional.of(vendedorBD); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		
		when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		VendedorDTO request = new VendedorDTO(email, razonSoacial);
		assertDoesNotThrow(() -> { vendedorService.crearVendedor(request); });
		
		verify(vendedorRepository, times(1)).findById(email);
		verify(vendedorRepository, times(0)).insert(vendedorBD);
		verify(vendedorRepository, times(1)).save(vendedorBD);
		verify(vendedorRepository).save(vendedorCaptor.capture());
		
		Vendedor capturado = vendedorCaptor.getValue();
		
		assertEquals(email, capturado.getMail());
		assertEquals(razonSoacial, capturado.getRazonSocial());
		assertEquals(VendedorEstado.ACTIVO, capturado.getEstado());

	}
	
	@Test
	public void testCrearVendedorExistenteActivo() {
		
		Vendedor vendedorBD = new Vendedor(email, razonSoacial);
		Optional<Vendedor> vendedorOpcional = Optional.of(vendedorBD); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		
		VendedorDTO request = new VendedorDTO(email, razonSoacial);
		assertThrows(VendedorExistenteException.class, () -> {  vendedorService.crearVendedor(request); });
		
		verify(vendedorRepository, times(1)).findById(email);

	}
	
	@Test
	public void testCrearVendedorMailVacio() {
		
		VendedorDTO request = new VendedorDTO("", razonSoacial);
		assertThrows(InternalErrorException.class, () -> {  vendedorService.crearVendedor(request); });
		
	}
	
	@Test
	public void testCrearVendedorMailVacioNull() {
		
		VendedorDTO request = new VendedorDTO(null, razonSoacial);
		assertThrows(InternalErrorException.class, () -> {  vendedorService.crearVendedor(request); });

	}
	
	@Test
	public void testCrearVendedorMailFormatoInvalido() {
		
		VendedorDTO request = new VendedorDTO(email_invalido, razonSoacial);
		assertThrows(FormatoEmailInvalidoException.class, () -> {  vendedorService.crearVendedor(request); });

	}
	
	@Test
	public void modificarVendedorExistenteActivoOK() {
		
		Vendedor vendedorBD = new Vendedor(email, razonSoacial);
		Optional<Vendedor> vendedorOpcional = Optional.of(vendedorBD); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		VendedorDTO request = new VendedorDTO(email, razonSoacial_Nueva);
		assertDoesNotThrow(() -> { vendedorService.modificarVendedor(request); });
		
		verify(vendedorRepository, times(1)).findById(email);
		verify(vendedorRepository, times(1)).save(vendedorBD);
		verify(vendedorRepository).save(vendedorCaptor.capture());

		Vendedor capturado = vendedorCaptor.getValue();
		
		assertEquals(email, capturado.getMail());
		assertEquals(razonSoacial_Nueva, capturado.getRazonSocial());
		assertEquals(VendedorEstado.ACTIVO, capturado.getEstado());
		
	}
	
	@Test
	public void modificarVendedorExistenteBorrado() {
		
		Vendedor vendedorBD = new Vendedor(email, razonSoacial);
		vendedorBD.setEstado(VendedorEstado.BORRADO);
		Optional<Vendedor> vendedorOpcional = Optional.of(vendedorBD); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		
		VendedorDTO request = new VendedorDTO(email, razonSoacial_Nueva);
		assertThrows(VendedorNoEncontradoException.class, () -> {  vendedorService.modificarVendedor(request); });
		
		verify(vendedorRepository, times(1)).findById(email);

	}
	
	@Test
	public void modificarVendedorInexistente() {
		
		Optional<Vendedor> vendedorOpcional = Optional.empty(); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		
		VendedorDTO request = new VendedorDTO(email, razonSoacial_Nueva);
		assertThrows(VendedorNoEncontradoException.class, () -> {  vendedorService.modificarVendedor(request); });
		
		verify(vendedorRepository, times(1)).findById(email);

	}
	
	@Test
	public void modificarVendedorMailVacio() {
		
		VendedorDTO request = new VendedorDTO("", razonSoacial_Nueva);
		assertThrows(InternalErrorException.class, () -> {  vendedorService.modificarVendedor(request); });
		

	}
	
	@Test
	public void modificarVendedorMailNull() {
		
		VendedorDTO request = new VendedorDTO(null, razonSoacial_Nueva);
		assertThrows(InternalErrorException.class, () -> {  vendedorService.modificarVendedor(request); });
		

	}
	
	@Test
	public void eliminarVendedorOK() {
		
		Vendedor vendedorBD = new Vendedor(email, razonSoacial);
		Optional<Vendedor> vendedorOpcional = Optional.of(vendedorBD); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		assertDoesNotThrow(() -> { vendedorService.eliminarVendedor(email); });
		
		verify(vendedorRepository, times(1)).findById(email);
		verify(vendedorRepository, times(1)).save(vendedorBD);
		verify(vendedorRepository).save(vendedorCaptor.capture());

		Vendedor capturado = vendedorCaptor.getValue();
		
		assertEquals(email, capturado.getMail());
		assertEquals(razonSoacial, capturado.getRazonSocial());
		assertEquals(VendedorEstado.BORRADO, capturado.getEstado());
		
	}
	
	@Test
	public void eliminarVendedorBorradoOK() {
		
		Vendedor vendedorBD = new Vendedor(email, razonSoacial);
		vendedorBD.setEstado(VendedorEstado.BORRADO);
		Optional<Vendedor> vendedorOpcional = Optional.of(vendedorBD); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		assertDoesNotThrow(() -> { vendedorService.eliminarVendedor(email); });
		
		verify(vendedorRepository, times(1)).findById(email);
		verify(vendedorRepository, times(1)).save(vendedorBD);
		verify(vendedorRepository).save(vendedorCaptor.capture());

		Vendedor capturado = vendedorCaptor.getValue();
		
		assertEquals(email, capturado.getMail());
		assertEquals(razonSoacial, capturado.getRazonSocial());
		assertEquals(VendedorEstado.BORRADO, capturado.getEstado());
		
	}
	
	@Test
	public void eliminarVendedorInexistente() {
		
		Optional<Vendedor> vendedorOpcional = Optional.empty(); 
		when(vendedorRepository.findById(email)).thenReturn(vendedorOpcional);
		
		assertThrows(VendedorNoEncontradoException.class, () -> {  vendedorService.eliminarVendedor(email); });

		verify(vendedorRepository, times(1)).findById(email);

	}

	public VendedorRepository getVendedorRepository() {
		return vendedorRepository;
	}

	public void setVendedorRepository(VendedorRepository vendedorRepository) {
		this.vendedorRepository = vendedorRepository;
	}

	public VendedorServiceImpl getVendedorService() {
		return vendedorService;
	}

	public void setVendedorService(VendedorServiceImpl vendedorService) {
		this.vendedorService = vendedorService;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public ArgumentCaptor<Vendedor> getVendedorCaptor() {
		return vendedorCaptor;
	}


	public void setVendedorCaptor(ArgumentCaptor<Vendedor> vendedorCaptor) {
		this.vendedorCaptor = vendedorCaptor;
	}

}
