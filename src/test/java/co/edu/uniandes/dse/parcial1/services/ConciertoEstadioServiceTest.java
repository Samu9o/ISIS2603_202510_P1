package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;

@SpringBootTest
@Transactional
public class ConciertoEstadioServiceTest {

    @Autowired
    private EstadioRepository estadioRepository;

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private ConciertoEstadioService conciertoEstadioService;

    private EstadioEntity estadio;
    private ConciertoEntity concierto;
    private ConciertoEntity conciertoExistente;

    @BeforeEach
    void setUp() {
        // Crear y persistir un estadio
        estadio = new EstadioEntity();
        estadio.setNombre("Estadio Principal");
        estadio.setCapacidadMaxima(200);
        estadio.setPrecioAlquiler(1000L);
        estadioRepository.save(estadio);

        // Crear y persistir un concierto existente (para probar la regla de fechas)
        conciertoExistente = new ConciertoEntity();
        conciertoExistente.setNombre("Concierto Existente");
        conciertoExistente.setCapacidad(50);
        conciertoExistente.setPresupuesto(1500L);
        conciertoExistente.setDate(new Date());
        conciertoExistente.setEstadio(estadio);
        conciertoRepository.save(conciertoExistente);

        
        concierto = new ConciertoEntity();
        concierto.setNombre("Concierto Nuevo");
        concierto.setCapacidad(100);
        concierto.setPresupuesto(2000L);
    
        LocalDate fechaMañana = LocalDate.now().plusDays(1);
        concierto.setDate(Date.from(fechaMañana.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        conciertoRepository.save(concierto);
    }

    @Test
    void testAsociacionExitosa() throws EntityNotFoundException, IllegalOperationException {
      
        ConciertoEntity resultado = conciertoEstadioService.addConcierto(concierto.getId(), estadio.getId());
        assertNotNull(resultado, "La asociación debe retornar un concierto válido");
    }

    @Test
    void testEstadioNoExiste() {
     
        assertThrows(EntityNotFoundException.class, () -> {
            conciertoEstadioService.addConcierto(concierto.getId(), 9999L);
        });
    }

    @Test
    void testConciertoNoExiste() {
        
        assertThrows(EntityNotFoundException.class, () -> {
            conciertoEstadioService.addConcierto(9999L, estadio.getId());
        });
    }

    @Test
    void testViolacionReglaFechasEntreConciertos() {
      
        concierto.setDate(conciertoExistente.getDate());
        conciertoRepository.save(concierto);

        assertThrows(IllegalOperationException.class, () -> {
            conciertoEstadioService.addConcierto(concierto.getId(), estadio.getId());
        });
    }
}