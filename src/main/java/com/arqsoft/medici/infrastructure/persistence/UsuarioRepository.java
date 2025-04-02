package com.arqsoft.medici.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.arqsoft.medici.domain.Usuario;

@Repository
public interface UsuarioRepository  extends MongoRepository<Usuario, String> {

}
