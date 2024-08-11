package gm.inventarios.servicio;

import gm.inventarios.modelo.Mensaje;
import gm.inventarios.repository.MensajeRepository;
import gm.inventarios.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeServicio implements IMensajeServicio{

    private MensajeRepository mensajeRepositorio;
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    public MensajeServicio(MensajeRepository mensajeRepositorio, UsuarioRepository usuarioRepositorio) {
        this.mensajeRepositorio = mensajeRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public List<Mensaje> obtenerMensajesPorUsuario(Integer idUsuario) {
        return mensajeRepositorio.findByEmisor_IdUsuarioOrReceptor_IdUsuarioOrderByFechaEnvioAsc(idUsuario, idUsuario);
    }

    @Override
    public Mensaje enviarMensaje(Mensaje mensaje) {
        usuarioRepositorio.findById(mensaje.getEmisor().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Emisor no encontrado"));
        usuarioRepositorio.findById(mensaje.getReceptor().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Receptor no encontrado"));

        return mensajeRepositorio.save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeRepositorio.findAllByOrderByFechaEnvioAsc();
    }

}
