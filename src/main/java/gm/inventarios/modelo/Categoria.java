package gm.inventarios.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCategoria;
    String nombre;
    String ruta;
    Boolean habilitado;
    String creadoPor;
    Date fechaCreacion;
    String modificadoPor;
    Date fechaModificacion;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Curso> cursos;

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                ", habilitado=" + habilitado +
                ", creadoPor='" + creadoPor + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}
