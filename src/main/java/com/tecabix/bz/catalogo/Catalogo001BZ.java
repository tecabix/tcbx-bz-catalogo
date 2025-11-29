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

    /**
     * Repositorio para acceder a la entidad Catalogo.
     */
    private final CatalogoRepository catalogoRepository;

    /**
     * Mensaje cuando no se encuentra el catálogo.
     */
    private static final String NO_SE_ENCONTRO_EL_CATALOGO;

    static {

        NO_SE_ENCONTRO_EL_CATALOGO = "No se encontro el catalogo.";
    }

    /**
     * Constructor de la clase {@code Catalogo001BZ}.
     *
     * @param repository el repositorio de catálogo que se utilizará para
     *                   acceder a los datos.
     */
    public Catalogo001BZ(final CatalogoRepository repository) {
        this.catalogoRepository = repository;
    }

    /**
     * Método para consultar los elementos del catálogo especificado por
     * tipo y nombre.
     * @param rqsv012 datos de la consulta.
     * @return Obtiene la clave del elemento del catálogo buscado.
     */
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
