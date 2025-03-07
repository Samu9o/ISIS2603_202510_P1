package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.uniandes.dse.parcial1.podam.DateStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import lombok.Data;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {
    @PodamExclude
    @ManyToOne
    private EstadioEntity estadio;

    @Temporal(TemporalType.DATE)
	@PodamStrategyValue(DateStrategy.class)
	private Date date;

    private String nombre;
    private int capacidad;
    private String nombreArtista;

    private Long presupuesto;
}
