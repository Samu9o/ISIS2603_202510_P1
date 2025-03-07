package co.edu.uniandes.dse.parcial1.services;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {
        @Autowired
	private EstadioRepository estadioRepository;

	@Autowired
	private ConciertoRepository conciertoRepository;
	
	
	@Transactional
public ConciertoEntity addConcierto(Long conciertoId, Long estadioId)
        throws EntityNotFoundException, IllegalOperationException {

    log.info("Inicia proceso de agregar un concierto al estadio con id", estadioId);

    Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
    if (conciertoEntity.isEmpty())
        throw new EntityNotFoundException("El concierto con id no existe");

    Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
    if (estadioEntity.isEmpty())
        throw new EntityNotFoundException("El estadio con id no existe");


    if (conciertoEntity.get().getCapacidad() > estadioEntity.get().getCapacidadMaxima()) {
        throw new IllegalOperationException("La capacidad del concierto supera la capacidad del estadio");
    }

   
    if (estadioEntity.get().getPrecioAlquiler() > conciertoEntity.get().getPresupuesto()) {
        throw new IllegalOperationException("El precio de alquiler supera el presupuesto del concierto");
    }


    LocalDate nuevaFecha = conciertoEntity.get().getDate().toInstant()
             .atZone(ZoneId.systemDefault()).toLocalDate();
    for (ConciertoEntity c : estadioEntity.get().getConciertos()) {
        LocalDate fechaExistente = c.getDate().toInstant()
             .atZone(ZoneId.systemDefault()).toLocalDate();
        long diasDiferencia = ChronoUnit.DAYS.between(fechaExistente, nuevaFecha);
        if (Math.abs(diasDiferencia) < 2) {
            throw new IllegalOperationException("Deben existir al menos 2 días de diferencia entre conciertos");
        }
    }

    conciertoEntity.get().setEstadio(estadioEntity.get());
    log.info("Termina proceso de agregar un concierto al estadio con id = {0}", estadioId);
    return conciertoEntity.get();
}

    

}
