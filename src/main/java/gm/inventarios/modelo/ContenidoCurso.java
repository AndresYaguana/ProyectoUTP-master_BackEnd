package gm.inventarios.modelo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContenidoCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idContenido;
    String titulo;
    String descripcion;
    String urlArchivo;
    Boolean habilitado;
    String creadoPor;
    Date fechaCreacion;
    String modificadoPor;
    Date fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;

    // Relación con Seccion
    @ManyToOne
    @JoinColumn(name = "idSeccion")
    //@JsonBackReference
    private SeccionCurso seccion;
}
