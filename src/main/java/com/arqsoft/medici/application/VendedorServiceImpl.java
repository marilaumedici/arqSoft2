package com.arqsoft.medici.application;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.ProductoDTO;
import com.arqsoft.medici.domain.dto.ProductoResponseDTO;
import com.arqsoft.medici.domain.dto.VendedorDTO;
import com.arqsoft.medici.domain.dto.VendedorDatosDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.VendedorExistenteException;
import com.arqsoft.medici.domain.exceptions.VendedorNoEncontradoException;
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
	
	@Override
	public void modificarVendedor(VendedorDTO request) throws InternalErrorException, VendedorNoEncontradoException {
		
		if(StringUtils.isBlank(request.getMail())) {
			throw new InternalErrorException("El campo mail no debe viajar vacio");
		}
		
		Optional<Vendedor> vendedorOpcional = vendedorRepository.findById(request.getMail());
		
		if(vendedorExiste(vendedorOpcional)) {
			Vendedor vendedor = vendedorOpcional.get();
		    actualizarDatosVendedor(request, vendedor);
		    
		}else {
			 throw new VendedorNoEncontradoException();
			 
		}
	}
	
	
	@Override
	public void eliminarVendedor(String mail) throws InternalErrorException, VendedorNoEncontradoException {
		
		if(StringUtils.isBlank(mail)) {
			throw new InternalErrorException("El mail no debe viajar vacio");
		}
		
		Optional<Vendedor> vendedorOpcional = vendedorRepository.findById(mail);
		
		if (vendedorOpcional.isPresent()) {
			Vendedor vendedor = vendedorOpcional.get();
			vendedor.setEstado(VendedorEstado.BORRADO);
			vendedorRepository.save(vendedor);
			
		} else {
		    throw new VendedorNoEncontradoException();
		}
		
	}
	
	@Override
	public Vendedor obtenerVendedorEntidad(String mailVendedor) throws InternalErrorException, VendedorNoEncontradoException {
		
		if(StringUtils.isBlank(mailVendedor)) {
			throw new InternalErrorException("El mail no debe viajar vacio");
		}
		
		Optional<Vendedor> vendedorOpcional = vendedorRepository.findById(mailVendedor);
		
		if(vendedorExiste(vendedorOpcional)) {
			return vendedorOpcional.get();
			
		}else {
			throw new VendedorNoEncontradoException();
			
		}
	}
	
	@Override
	public void actualizarVendedor(Vendedor vendedor) {
		
		vendedorRepository.save(vendedor);
		
	}
	
	@Override
	public VendedorDatosDTO obtenerVendedor(String mail) {
	
		Optional<Vendedor> opcionalVendedor = vendedorRepository.findById(mail);
		
		Vendedor vendedor = opcionalVendedor.get();
		
		VendedorDatosDTO dto = new VendedorDatosDTO();
		dto.setEstado(vendedor.getEstado());
		dto.setMail(vendedor.getMail());
		dto.setRazonSocial(vendedor.getRazonSocial());
		for(Producto p : vendedor.getProductosListados()) {
	    	ProductoResponseDTO pDTO = new ProductoResponseDTO(p.getProductoId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), p.getCategoria(),p.getEstado(), p.getVendedor().getMail());
			dto.getProductosListados().add(pDTO);
		}
		
		return dto;
	}

	private void actualizarDatosVendedor(VendedorDTO request, Vendedor vendedor) {

		if(StringUtils.isNotBlank(request.getRazonSocial())) {
			vendedor.setRazonSocial(request.getRazonSocial());
		}

		vendedorRepository.save(vendedor);
	}
	
	private boolean vendedorExiste(Optional<Vendedor> vendedorOpcional) {
		
		return vendedorOpcional.isPresent() && vendedorOpcional.get().getEstado().equals(VendedorEstado.ACTIVO);
		
	}


	public VendedorRepository getVendedorRepository() {
		return vendedorRepository;
	}

	public void setVendedorRepository(VendedorRepository vendedorRepository) {
		this.vendedorRepository = vendedorRepository;
	}

}
