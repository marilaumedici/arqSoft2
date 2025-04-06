package com.arqsoft.medici.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.arqsoft.medici.domain.Producto;


@Repository
public interface ProductoRepository  extends MongoRepository<Producto, String>  {

}
