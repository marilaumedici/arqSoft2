package com.arqsoft.medici.infrastructure.persistence;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.arqsoft.medici.domain.Producto;
import com.arqsoft.medici.domain.Vendedor;
import com.arqsoft.medici.domain.dto.FiltroBuscadorProducto;
import com.arqsoft.medici.domain.dto.ProductosPaginado;
import com.arqsoft.medici.domain.utils.ProductoEstado;
import com.arqsoft.medici.domain.utils.VendedorEstado;
import com.arqsoft.medici.infrastructure.persistence.puertos.ProductoDAO;

import io.micrometer.common.util.StringUtils;


@Repository
public class ProductoDAOImpl implements ProductoDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public ProductosPaginado buscarProductos(FiltroBuscadorProducto request) {
		
	PageRequest pageRequest = PageRequest.of(request.getPagina(), request.getSize());
		
		List<Criteria> criteriosAnd = new ArrayList<>();
		
		//Solo hay que traer productos disponibles para su compra
	    criteriosAnd.add(Criteria.where("estado").is(ProductoEstado.DISPONIBLE));
	    
	    List<String> vendedoresActivosIds = mongoTemplate.find(
	            new Query(Criteria.where("estado").is(VendedorEstado.ACTIVO)),
	            Vendedor.class
	        ).stream().map(Vendedor::getMail).collect(Collectors.toList());

	     criteriosAnd.add(Criteria.where("vendedor.$id").in(vendedoresActivosIds));

		
	    //Filtra por precio
		if (request.getPrecioMinimo() != null && request.getPrecioMaximo() != null) {
		    criteriosAnd.add(Criteria.where("precio").gte(request.getPrecioMinimo()).lte(request.getPrecioMaximo()));
		} else if (request.getPrecioMinimo() != null) {
		    criteriosAnd.add(Criteria.where("precio").gte(request.getPrecioMinimo()));
		} else if (request.getPrecioMaximo() != null) {
		    criteriosAnd.add(Criteria.where("precio").lte(request.getPrecioMaximo()));
		}

		//Filtra por categoria
		if (request.getCategoria() != null) {
		    criteriosAnd.add(Criteria.where("categoria").is(request.getCategoria()));
		}

		//Filtra por una descripcion del producto
		if (StringUtils.isNotBlank(request.getDescripcion())) {
		    String regex = ".*" + Pattern.quote(request.getDescripcion().trim()) + ".*";
		    Criteria nombreCriteria      = Criteria.where("nombre").regex(regex, "i");
		    Criteria descripcionCriteria = Criteria.where("descripcion").regex(regex, "i");

		    Criteria orCriterio = new Criteria().orOperator(nombreCriteria, descripcionCriteria);
		    criteriosAnd.add(orCriterio);
		}

		Criteria finalCriteria = new Criteria().andOperator(criteriosAnd.toArray(new Criteria[0]));
		Query query = new Query(finalCriteria).with(pageRequest);

		List<Producto> productos = mongoTemplate.find(query, Producto.class);
		
		long totalElementos = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Producto.class);
		int totalPaginas = (int) Math.ceil((double) totalElementos / request.getSize());

		return new ProductosPaginado(productos, request.getPagina(), totalElementos, totalPaginas);
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}



}
