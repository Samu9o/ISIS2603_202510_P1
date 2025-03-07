package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.EstadioService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Editorials
 *
 * @author ISIS2603
 */
@DataJpaTest
@Transactional
@Import(EstadioService.class)
class EstadioServiceTest {

	@Autowired
	private EstadioService estadioService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EstadioEntity> estadioList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EstadioEntity");
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity");
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {

		for (int i = 0; i < 3; i++) {
			EstadioEntity estadioEntity = factory.manufacturePojo(EstadioEntity.class);
			entityManager.persist(estadioEntity);
			estadioList.add(estadioEntity);
		}
	}

    @Test
	void testCreateEstadio() throws IllegalOperationException {
		EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);
		EstadioEntity result = estadioService.createEstadio(newEntity);
		assertNotNull(result);

      

		EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getNombreCiudad(), entity.getNombreCiudad());
        assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
        assertEquals(newEntity.getPrecioAlquiler(), entity.getPrecioAlquiler());


        
	}

    @Test
    void testCreateEstadioCapacidadMaximoInvalida() {
     EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);
        newEntity.setCapacidadMaxima(5); 
        assertThrows(IllegalOperationException.class, () -> {
        estadioService.createEstadio(newEntity);
    });
}
    

}