package gm.inventarios.servicio;

import gm.inventarios.modelo.Mensaje;
import java.util.List;

public interface IMensajeServicio {

    List<Mensaje> obtenerMensajesPorUsuario(Integer idUsuario);
    Mensaje enviarMensaje(Mensaje mensaje);
    List<Mensaje> obtenerTodosLosMensajes();
}
