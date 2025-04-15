package com.arqsoft.medici.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.arqsoft.medici.domain.Venta;

@Repository
public interface VentaRepository  extends MongoRepository<Venta, String> {

}
