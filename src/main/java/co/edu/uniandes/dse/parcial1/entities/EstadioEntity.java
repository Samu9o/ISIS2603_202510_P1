package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    private String nombreCiudad;
    private int capacidadMaxima;
    @PodamExclude
    @OneToMany(mappedBy = "estadio")
    private List<ConciertoEntity> conciertos= new ArrayList<>();;
  
}
