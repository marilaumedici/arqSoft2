package com.arqsoft.medici.application;

import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.dto.UsuarioDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;

public interface UsuarioService {

	void crearUsuario(UsuarioDTO request) throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException;

	void modificarUsuario(UsuarioDTO request) throws UsuarioNoEncontradoException, InternalErrorException;

	void eliminarUsuario(String mail) throws InternalErrorException, UsuarioNoEncontradoException;

	Usuario obtenerUsuarioByID(String mail) throws UsuarioNoEncontradoException;

}
