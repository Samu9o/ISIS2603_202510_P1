package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.ConciertoService;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest {

    @Autowired
	private ConciertoService conciertoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<ConciertoEntity> conciertoList = new ArrayList<>();
	private EstadioEntity estadioEntity;

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from  ConciertoEntity");
		entityManager.getEntityManager().createQuery("delete from EstadioEntity");

	}

    private void insertData() {
		
		estadioEntity = factory.manufacturePojo(EstadioEntity.class);
		entityManager.persist(estadioEntity);

		for (int i = 0; i < 3; i++) {
			ConciertoEntity conciertoEntity = factory.manufacturePojo(ConciertoEntity.class);
			conciertoEntity.setEstadio(estadioEntity);
        
			entityManager.persist(conciertoEntity);
			conciertoList.add(conciertoEntity);
		}

	
	}

    @Test
void testCreateConcierto() throws EntityNotFoundException, IllegalOperationException {
    ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
    newEntity.setEstadio(estadioEntity);
    newEntity.setDate(new Date());

    ConciertoEntity result = conciertoService.createConcierto(newEntity);

    assertNotNull(result);
    ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());
    assertEquals(newEntity.getId(), entity.getId());
    assertEquals(newEntity.getNombre(), entity.getNombre());
    assertEquals(newEntity.getNombreArtista(), entity.getNombreArtista());
    assertEquals(newEntity.getPresupuesto(), entity.getPresupuesto());
    assertEquals(newEntity.getDate(), entity.getDate());
}

    @Test
    void testCreateConciertoCapacidadInvalida() {
     ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
        newEntity.setEstadio(estadioEntity);
        newEntity.setCapacidad(5); 
        assertThrows(IllegalOperationException.class, () -> {
        conciertoService.createConcierto(newEntity);
    });
}
    
    
}
