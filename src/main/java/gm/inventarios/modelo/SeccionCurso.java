package gm.inventarios.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("seccion")
    //@JsonBackReference
    private Curso curso;

    // Relación con Contenido
    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ContenidoCurso> contenidos;

    @Override
    public String toString() {
        return "SeccionCurso{" +
                "idSeccion=" + idSeccion +
                ", nombre='" + nombre + '\'' +
                ", habilitado=" + habilitado +
                ", creadoPor='" + creadoPor + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}
