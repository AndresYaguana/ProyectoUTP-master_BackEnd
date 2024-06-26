package gm.inventarios.modelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContenidoCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idContenido;
    String titulo;
    String descripcion;
    String urlVideo;
    String contenido;
    Boolean habilitado;
    String creadoPor;
    Date fechaCreacion;
    String modificadoPor;
    Date fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "idCurso")
    Curso curso;

    // Relación con Seccion
    @ManyToOne
    @JoinColumn(name = "idSeccion")
    SeccionCurso seccion;
}
