package co.edu.uniandes.dse.parcial1.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

     @Autowired
	EstadioRepository estadioRepository;


    @Transactional
	public EstadioEntity createEstadio(EstadioEntity estadio) throws IllegalOperationException {
		log.info("Inicia proceso de creación de un estadio");
		
        if(estadio.getNombreCiudad().length() < 3) {
			throw new IllegalOperationException("El nombre de la ciudad debe ser de minimo 3 caracteres");
	    }
        if(estadio.getCapacidadMaxima() < 1000) {
			throw new IllegalOperationException("El estadio debe tener capacidad de minimo 1000 personas");
	    }
        if(estadio.getPrecioAlquiler() < 100000) {
			throw new IllegalOperationException("El precio de alquiler debe ser superior a 100000 dólares");
	    }
		
		return estadioRepository.save(estadio);
	}

}
