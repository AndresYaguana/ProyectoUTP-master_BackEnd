package gm.inventarios.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
