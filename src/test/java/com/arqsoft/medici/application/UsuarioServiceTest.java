package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.dto.UsuarioDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.utils.FormatUtils;
import com.arqsoft.medici.infrastructure.persistence.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@InjectMocks
	private UsuarioServiceImpl usuarioService;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	private String email    = "agussusu@gmal.com";
	private String nombre   = "Agustin";
	private String apellido = "Delpane";
	
	@Test
	public void testCrearUsuarioOK() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {
		/*
		// Se prepara la respuesta del metodo usuarioRepository.findById
		Usuario usuarioBuscado = new Usuario(nombre, apellido, email);
		usuarioBuscado.setEstado(UsuarioEstado.BORRADO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBuscado); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		
		mockStatic(FormatUtils.class);
	    doNothing().when(FormatUtils.class);
	    FormatUtils.isValidEmail(email);
	    */
	    MockedStatic<FormatUtils> mockStatic = mockStatic(FormatUtils.class);
	    mockStatic.when(() -> FormatUtils.isValidEmail(email)).thenAnswer(invocation -> {
//.thenThrow(new FormatoEmailInvalidoException());
            return null; 
        });
		
		Optional<Usuario> usuarioOpcional = Optional.empty(); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		when(usuarioRepository.insert(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		UsuarioDTO request = new UsuarioDTO(nombre, apellido, email);
		assertDoesNotThrow(() -> { usuarioService.crearUsuario(request); });
		
		verify(usuarioRepository, times(1)).findById(email);
		verify(usuarioRepository, times(1)).insert(any(Usuario.class));
		mockStatic.verify(() -> FormatUtils.isValidEmail(email), times(1));
		//PowerMockito.verifyStatic(FormatUtils.class, Mockito.times(1));

	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public UsuarioServiceImpl getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioServiceImpl usuarioService) {
		this.usuarioService = usuarioService;
	}



}
