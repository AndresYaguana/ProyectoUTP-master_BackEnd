package gm.inventarios.servicio;

import gm.inventarios.modelo.TipoUsuario;
import java.util.List;

public interface ITipoUsuarioServicio {

    public List<TipoUsuario> ListarTipousuario();

    public TipoUsuario buscarTipousuarioPorId(Integer idTipousuario);

    public TipoUsuario guardarTipousuario(TipoUsuario tipoUsuario);

    public void eliminarTipousuarioPorId(Integer idTipousuario);
}
