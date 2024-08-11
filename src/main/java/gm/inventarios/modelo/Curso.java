package gm.inventarios.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCurso;
    String nombre;
    String ruta;
    String urlImage;
    String descripcion;
    Boolean habilitado;
    String creadoPor;
    Date fechaCreacion;
    String modificadoPor;
    Date fechaModificacion;

    // Relación con Categoria
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    @JsonIgnoreProperties("cursos")
    private Categoria categoria;

    // Relación con Seccion
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    //@JsonManagedReference
    @JsonIgnoreProperties("curso")
    private List<SeccionCurso> secciones;

    /*@Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                ", habilitado=" + habilitado +
                ", creadoPor='" + creadoPor + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }*/
}
