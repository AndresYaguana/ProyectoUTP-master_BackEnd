package gm.inventarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idMensaje;

    @ManyToOne
    @JoinColumn(name = "id_emisor", referencedColumnName = "idUsuario")
    Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "id_receptor", referencedColumnName = "idUsuario")
    Usuario receptor;

    String contenido;
    LocalDateTime fechaEnvio;
    Boolean habilitado;
}
