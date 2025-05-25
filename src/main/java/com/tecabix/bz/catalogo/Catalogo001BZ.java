package com.tecabix.bz.catalogo;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.tecabix.db.entity.Catalogo;
import com.tecabix.db.repository.CatalogoRepository;
import com.tecabix.res.b.RSB010;
import com.tecabix.sv.rq.RQSV012;

/**
 *
 * @author Ramirez Urrutia Angel Abinadi
 */
public class Catalogo001BZ {

	private CatalogoRepository catalogoRepository;
	
	private String NO_SE_ENCONTRO_EL_CATALOGO = "No se encontro el catalogo.";
	
    public Catalogo001BZ(CatalogoRepository repository) {
		this.catalogoRepository = repository;
	}

	public ResponseEntity<RSB010> clave(final RQSV012 rqsv012) {

        RSB010 rsb010 = rqsv012.getRsb010();
        String tipo = rqsv012.getTipo();
        String nombre = rqsv012.getNombre();

        Optional<Catalogo> optional;
        optional = catalogoRepository.findByTipoAndNombre(tipo, nombre);

        if (optional.isEmpty()) {
            return rsb010.notFound(NO_SE_ENCONTRO_EL_CATALOGO);
        }

        return rsb010.ok(optional.get());
    }
}
