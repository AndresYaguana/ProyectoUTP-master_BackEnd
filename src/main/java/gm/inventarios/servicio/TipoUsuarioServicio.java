package gm.inventarios.servicio;

import gm.inventarios.modelo.TipoUsuario;
import gm.inventarios.repository.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoUsuarioServicio implements ITipoUsuarioServicio{

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public List<TipoUsuario> ListarTipousuario() {
        return this.tipoUsuarioRepository.findAll();
    }

    @Override
    public TipoUsuario buscarTipousuarioPorId(Integer idTipousuario) {
        TipoUsuario tipoUsuario = this.tipoUsuarioRepository.findById(idTipousuario).orElse(null);
        return tipoUsuario;
    }

    @Override
    public TipoUsuario guardarTipousuario(TipoUsuario tipoUsuario) {
        return this.tipoUsuarioRepository.save(tipoUsuario);
    }

    @Override
    public void eliminarTipousuarioPorId(Integer idTipousuario) {
        this.tipoUsuarioRepository.deleteById(idTipousuario);
    }
}
