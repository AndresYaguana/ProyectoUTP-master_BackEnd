package gm.inventarios.servicio;

import gm.inventarios.modelo.Producto;
import gm.inventarios.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {

    public List<Usuario> ListarUsuario();

    public Usuario buscarUsuarioPorId(Integer idUsuario);

    public Usuario guardarUsuario(Usuario usuario);

    public void eliminarUsuarioPorId(Integer idUsuario);

    public Usuario autenticarUsuario(String email, String password);

    public List<String>obtenerPermisosUsuario(Integer idUsuario);

}
