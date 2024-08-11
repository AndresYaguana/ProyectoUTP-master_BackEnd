package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.TipoUsuario;
import gm.inventarios.modelo.Usuario;
import gm.inventarios.servicio.TipoUsuarioServicio;
import gm.inventarios.servicio.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class UsuariosControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(UsuariosControlador.class);

    private UsuarioServicio usuarioServicio;
    private TipoUsuarioServicio tipoUsuarioServicio;

    @Autowired
    public UsuariosControlador(UsuarioServicio usuarioServicio, TipoUsuarioServicio tipoUsuarioServicio) {
        this.usuarioServicio = usuarioServicio;
        this.tipoUsuarioServicio = tipoUsuarioServicio;
    }

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = this.usuarioServicio.ListarUsuario();
        logger.info("Usuarios obtenidos:");
        usuarios.forEach((usuario -> logger.info(usuario.toString())));
        return usuarios;
    }

    /*@PostMapping("/usuarios")
    public Usuario agregarUsuario(@RequestBody Usuario usuario){
        logger.info("Usuarios a agregar: " + usuario);
        if (usuario.getTipousuario() != null && usuario.getTipousuario().getIdTipousuario() != null) {
            TipoUsuario tipoUsuario = tipoUsuarioServicio.buscarTipousuarioPorId(usuario.getTipousuario().getIdTipousuario());
            usuario.setTipousuario(tipoUsuario);
        }
        return this.usuarioServicio.guardarUsuario(usuario);
    }*/

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) {
        logger.info("Usuario a agregar: " + usuario);
        if (usuario.getTipousuario() != null && usuario.getTipousuario().getIdTipousuario() != null) {
            TipoUsuario tipoUsuario = tipoUsuarioServicio.buscarTipousuarioPorId(usuario.getTipousuario().getIdTipousuario());
            if (tipoUsuario != null) {
                usuario.setTipousuario(tipoUsuario);
            } else {
                return ResponseEntity.badRequest().body(null); // TipoUsuario no encontrado
            }
        }
        Usuario usuarioGuardado = this.usuarioServicio.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = this.usuarioServicio.buscarUsuarioPorId(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        System.out.println("Received login request for: " + usuario.getEmail());
        Usuario usuarioAutenticado = usuarioServicio.autenticarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioAutenticado != null) {
            return ResponseEntity.ok(usuarioAutenticado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas. Por favor, verifica tu correo electrónico y contraseña.");
        }
    }*/

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = usuarioServicio.autenticarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioAutenticado != null) {
            return ResponseEntity.ok(usuarioAutenticado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable int id,
            @RequestBody Usuario usuarioRecibido){
        Usuario usuario = this.usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        usuario.setEmail(usuarioRecibido.getEmail());
        usuario.setPassword(usuarioRecibido.getPassword());
        usuario.setNombres(usuarioRecibido.getNombres());
        usuario.setApellidos(usuarioRecibido.getApellidos());
        usuario.setTipousuario(usuarioRecibido.getTipousuario());
        usuario.setUrlFoto(usuarioRecibido.getUrlFoto());
        usuario.setUniversidad(usuarioRecibido.getUniversidad());
        usuario.setHabilitado(usuarioRecibido.getHabilitado());
        usuario.setModificadoPor(usuarioRecibido.getModificadoPor());
        usuario.setFechaModificacion(usuarioRecibido.getFechaModificacion());
        this.usuarioServicio.guardarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarUsuario(@PathVariable int id){
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        this.usuarioServicio.eliminarUsuarioPorId(usuario.getIdUsuario());
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("Usuario Eliminado Exitosamente",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/usuarios/{id}/permisos")
    public ResponseEntity<List<String>> obtenerPermisosUsuario(@PathVariable("id") Integer idUsuario) {
        List<String> permisos = usuarioServicio.obtenerPermisosUsuario(idUsuario);
        return ResponseEntity.ok(permisos);
    }

}


