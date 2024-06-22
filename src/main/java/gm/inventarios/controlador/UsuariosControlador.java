package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Usuario;
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
//localhost:8080/inventario_app
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class UsuariosControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(UsuariosControlador.class);

    @Autowired
    private UsuarioServicio usuarioServicio;

    //http://localhost:8080/ProyectoUTP-master/usuarios
    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = this.usuarioServicio.ListarUsuario();
        logger.info("Usuarios obtenidos:");
        usuarios.forEach((usuario -> logger.info(usuario.toString())));
        return usuarios;

    }

    @PostMapping("/usuarios")
    public Usuario agregarProducto(@RequestBody Usuario usuario){
        logger.info("Usuarios a agregar: " + usuario);
        return this.usuarioServicio.guardarUsuario(usuario);
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        System.out.println("Received login request for: " + usuario.getEmail());
        Usuario usuarioAutenticado = usuarioServicio.autenticarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioAutenticado != null) {
            return ResponseEntity.ok(usuarioAutenticado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas. Por favor, verifica tu correo electrónico y contraseña.");
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
        usuario.setTipoUsuario(usuarioRecibido.getTipoUsuario());
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

}
