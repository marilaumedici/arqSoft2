package com.arqsoft.medici.application;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.dto.UsuarioDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.domain.utils.FormatUtils;
import com.arqsoft.medici.domain.utils.UsuarioEstado;
import com.arqsoft.medici.infrastructure.persistence.UsuarioRepository;
import io.micrometer.common.util.StringUtils;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void crearUsuario(UsuarioDTO request) throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {

		if(StringUtils.isBlank(request.getMail())) {
			throw new InternalErrorException("El campo mail no debe viajar vacio");
		}
		
		FormatUtils.isValidEmail(request.getMail());
		
		Optional<Usuario> usuarioOpcional = usuarioRepository.findById(request.getMail());
		
		if(usuarioOpcional.isPresent()) {
			if(usuarioOpcional.get().getEstado().equals(UsuarioEstado.ACTIVO)) {
				throw new UsuarioExistenteException();
				
			}else {
				Usuario usuario = usuarioOpcional.get();
				usuario.setEstado(UsuarioEstado.ACTIVO);
				actualizarDatosUsuario(request, usuario);
			}
		}else {
			usuarioRepository.insert(new Usuario(request.getNombre(), request.getApellido(), request.getMail()));

		}		
	}
	
	@Override
	public void modificarUsuario(UsuarioDTO request) throws UsuarioNoEncontradoException, InternalErrorException {
		
		if(StringUtils.isBlank(request.getMail())) {
			throw new InternalErrorException("El campo mail no debe viajar vacio");
		}
		
		Optional<Usuario> usuarioOpcional = usuarioRepository.findById(request.getMail());
		
		if (usuarioOpcional.isPresent()) {
			if(usuarioOpcional.get().getEstado().equals(UsuarioEstado.BORRADO)) {
				throw new UsuarioNoEncontradoException();
				
			}
		    Usuario usuario = usuarioOpcional.get();
		    actualizarDatosUsuario(request, usuario);
		    
		} else {
		    throw new UsuarioNoEncontradoException();
		}
	}
	
	@Override
	public void eliminarUsuario(String mail) throws InternalErrorException, UsuarioNoEncontradoException {
		
		if(StringUtils.isBlank(mail)) {
			throw new InternalErrorException("El mail no debe viajar vacio");
		}
		
		Optional<Usuario> usuarioOpcional = usuarioRepository.findById(mail);
		
		if (usuarioOpcional.isPresent()) {
			Usuario usuario = usuarioOpcional.get();
			usuario.setEstado(UsuarioEstado.BORRADO);
			usuarioRepository.save(usuario);
			
		} else {
		    throw new UsuarioNoEncontradoException();
		}
	}
	
	@Override
	public Usuario obtenerUsuarioByID(String mail) throws UsuarioNoEncontradoException {
		
	Optional<Usuario> usuarioOpcional = usuarioRepository.findById(mail);
		
		if(usuarioOpcional.isPresent() && usuarioOpcional.get().getEstado().equals(UsuarioEstado.ACTIVO)) {
			return usuarioOpcional.get();
				
		}else {
			throw new UsuarioNoEncontradoException();
		}
	}

	private void actualizarDatosUsuario(UsuarioDTO request, Usuario usuario) {
		
		if(StringUtils.isNotBlank(request.getApellido())) {
			usuario.setApellido(request.getApellido());
		}
		if(StringUtils.isNotBlank(request.getNombre())) {
			usuario.setNombre(request.getNombre());
		}
		usuarioRepository.save(usuario);
	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

}
