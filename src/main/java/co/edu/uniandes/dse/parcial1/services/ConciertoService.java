package co.edu.uniandes.dse.parcial1.services;


import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;

import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    @Autowired
	ConciertoRepository conciertoRepository;


    @Transactional
public ConciertoEntity createConcierto(ConciertoEntity conciertoEntity) throws IllegalOperationException {
    if (conciertoEntity.getDate() == null) {
        throw new IllegalOperationException("La fecha del concierto no puede ser nula");
    }

    if (conciertoEntity.getDate().before(new Date())) {
        throw new IllegalOperationException("La fecha no puede ser en el pasado");
    }

    if (conciertoEntity.getCapacidad() <= 10) {
        throw new IllegalOperationException("La capacidad del concierto debe ser mayor a 10");
    }

    if (conciertoEntity.getPresupuesto() <= 1000) {
        throw new IllegalOperationException("El presupuesto debe ser mayor a 1000 dólares");
    }

    return conciertoRepository.save(conciertoEntity);
}

}
