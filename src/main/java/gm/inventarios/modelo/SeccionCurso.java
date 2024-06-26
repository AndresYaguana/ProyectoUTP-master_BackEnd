package gm.inventarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeccionCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idSeccion;
    String nombre;
    Boolean habilitado;
    String creadoPor;
    Date fechaCreacion;
    String modificadoPor;
    Date fechaModificacion;

    // Relación con Curso
    @ManyToOne
    @JoinColumn(name = "idCurso")
    Curso curso;

    // Relación con Contenido
    @OneToMany(mappedBy = "seccion")
    List<ContenidoCurso> contenidos;


}
