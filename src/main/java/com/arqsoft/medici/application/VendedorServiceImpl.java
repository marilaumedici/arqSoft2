package com.arqsoft.medici.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorExistenteException;
import com.arqsoft.medici.domain.utils.FormatUtils;
import com.arqsoft.medici.domain.utils.VendedorEstado;
import com.arqsoft.medici.infrastructure.persistence.VendedorRepository;
import io.micrometer.common.util.StringUtils;

@Service
public class VendedorServiceImpl implements VendedorService {
	
	@Autowired
	private VendedorRepository vendedorRepository;

	@Override
	public void crearVendedor(VendedorDTO request) throws InternalErrorException, FormatoEmailInvalidoException, VendedorExistenteException {
		
		if(StringUtils.isBlank(request.getMail())) {
			throw new InternalErrorException("El campo mail no debe viajar vacio");
		}
		
		FormatUtils.isValidEmail(request.getMail());
		
		Optional<Vendedor> vendedorOpcional = vendedorRepository.findById(request.getMail());
		
		if(vendedorOpcional.isPresent()) {
			if(vendedorOpcional.get().getEstado().equals(VendedorEstado.ACTIVO)) {
				throw new VendedorExistenteException();
				
			}else {
				Vendedor vendedor = vendedorOpcional.get();
				vendedor.setEstado(VendedorEstado.ACTIVO);
				actualizarDatosVendedor(request, vendedor);
			}
		}else {
			vendedorRepository.insert(new Vendedor(request.getMail(), request.getRazonSocial()));

		}	
	}


	private void actualizarDatosVendedor(VendedorDTO request, Vendedor vendedor) {
		if(StringUtils.isNotBlank(request.getRazonSocial())) {
			vendedor.setRazonSocial(request.getRazonSocial());
		}

		vendedorRepository.save(vendedor);
	}


	public VendedorRepository getVendedorRepository() {
		return vendedorRepository;
	}

	public void setVendedorRepository(VendedorRepository vendedorRepository) {
		this.vendedorRepository = vendedorRepository;
	}

}
