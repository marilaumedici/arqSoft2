package com.arqsoft.medici.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.arqsoft.medici.domain.Producto;


@Repository
public interface ProductoRepository  extends MongoRepository<Producto, String>  {
	
	public Page<Producto> findByVendedor_Mail(String mail, Pageable pageable);

}
