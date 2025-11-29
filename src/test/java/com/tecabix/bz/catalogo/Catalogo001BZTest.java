package com.tecabix.bz.catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.tecabix.db.entity.Catalogo;
import com.tecabix.db.repository.CatalogoRepository;
import com.tecabix.res.b.RSB010;
import com.tecabix.sv.rq.RQSV012;

/**
 *
 * @author Ramirez Urrutia Angel Abinadi
 */
@ExtendWith(MockitoExtension.class)
class Catalogo001BZTest {

    /**
     * Mock del repositorio de catálogo utilizado para pruebas unitarias.
     */
    @Mock
    private CatalogoRepository repository;

    /**
     * Mock del componente RQSV012 utilizado en las pruebas.
     */
    @Mock
    private RQSV012 rqsv012;

    /**
     * Mock del componente RSB010 utilizado en las pruebas.
     */
    @Mock
    private RSB010 rsb010;

    /**
     * Instancia de la clase bajo prueba (System Under Test), con las
     * dependencias inyectadas.
     */
    @InjectMocks
    private Catalogo001BZ sut;

    /**
     * Constante que representa el tipo de prueba.
     */
    private static final String TIPO;

    /**
     * Constante que representa el nombre de la prueba.
     */
    private static final String NOMBRE;

    /**
     * Mensaje utilizado cuando no se encuentra el catálogo.
     */
    private static final String MENSAJE_NO_ENCONTRADO;

    static {
        TIPO = "tipoPrueba";
        NOMBRE = "nombrePrueba";
        MENSAJE_NO_ENCONTRADO = "No se encontro el catalogo.";
    }

    @Test
    void cuandoNoExisteCatalogoRetornaNotFound() {

        when(rqsv012.getTipo()).thenReturn(TIPO);
        when(rqsv012.getNombre()).thenReturn(NOMBRE);
        when(rqsv012.getRsb010()).thenReturn(rsb010);
        when(repository.findByTipoAndNombre(TIPO, NOMBRE))
            .thenReturn(Optional.empty());

        ResponseEntity<RSB010> notFoundResponse;
        notFoundResponse = ResponseEntity.notFound().build();
        when(rsb010.notFound(MENSAJE_NO_ENCONTRADO))
            .thenReturn(notFoundResponse);

        ResponseEntity<RSB010> response = sut.clave(rqsv012);
        verify(rsb010).notFound(MENSAJE_NO_ENCONTRADO);
        assertEquals(notFoundResponse, response);
    }

    @Test
    void cuandoExisteCatalogoRetornaOk() {

        when(rqsv012.getTipo()).thenReturn(TIPO);
        when(rqsv012.getNombre()).thenReturn(NOMBRE);
        when(rqsv012.getRsb010()).thenReturn(rsb010);
        Catalogo catalogo = new Catalogo();
        when(repository.findByTipoAndNombre(TIPO, NOMBRE))
            .thenReturn(Optional.of(catalogo));

        ResponseEntity<RSB010> okResponse = ResponseEntity.ok(rsb010);
        when(rsb010.ok(catalogo)).thenReturn(okResponse);
        ResponseEntity<RSB010> response = sut.clave(rqsv012);
        verify(rsb010).ok(catalogo);
        assertEquals(okResponse, response);
    }
}
