package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Categoria;
import gm.inventarios.modelo.Curso;
import gm.inventarios.modelo.TipoUsuario;
import gm.inventarios.repository.CategoriaRepository;
import gm.inventarios.servicio.CategoriaServicio;
import gm.inventarios.servicio.CursoServicio;
import gm.inventarios.servicio.TipoUsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//localhost:8080/inventario_app
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class TipoUsuarioControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(TipoUsuarioControlador.class);

    @Autowired
    private TipoUsuarioServicio tipoUsuarioServicio;

    //http://localhost:8080/ProyectoUTP-master/tipousuarios
    @GetMapping("/tipousuarios")
    public List<TipoUsuario> obtenerTipousuarios() {
        List<TipoUsuario> tipoUsuarios = this.tipoUsuarioServicio.ListarTipousuario();
        logger.info("Tipo Usuario obtenidas:");
        tipoUsuarios.forEach((tipoUsuario -> logger.info(tipoUsuario.toString())));
        return tipoUsuarios;
    }

    @PostMapping("/tipousuarios")
    public TipoUsuario agregarTipousuarios (@RequestBody TipoUsuario tipoUsuario){
        logger.info("Tipo Usuario a agregar: " + tipoUsuario);
        return this.tipoUsuarioServicio.guardarTipousuario(tipoUsuario);
    }

    @GetMapping("/tipousuarios/{id}")
    public ResponseEntity<TipoUsuario> obtenerTipousuarioPorId(@PathVariable int id) {
        TipoUsuario tipoUsuario = this.tipoUsuarioServicio.buscarTipousuarioPorId(id);
        if (tipoUsuario != null) {
            return ResponseEntity.ok(tipoUsuario);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    @PutMapping("/tipousuarios/{id}")
    public ResponseEntity<TipoUsuario> actualizarTipousuario(
            @PathVariable int id,
            @RequestBody TipoUsuario tipoUsuarioRecibido){
        TipoUsuario tipoUsuario = this.tipoUsuarioServicio.buscarTipousuarioPorId(id);
        if (tipoUsuario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        tipoUsuario.setNombre(tipoUsuarioRecibido.getNombre());
        tipoUsuario.setHabilitado(tipoUsuarioRecibido.getHabilitado());
        tipoUsuario.setModificadoPor(tipoUsuarioRecibido.getModificadoPor());
        tipoUsuario.setFechaModificacion(tipoUsuarioRecibido.getFechaModificacion());
        this.tipoUsuarioServicio.guardarTipousuario(tipoUsuario);
        return ResponseEntity.ok(tipoUsuario);
    }

    @DeleteMapping("/tipousuarios/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarTipousuario(@PathVariable int id){
        TipoUsuario tipoUsuario = tipoUsuarioServicio.buscarTipousuarioPorId(id);
        if (tipoUsuario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        this.tipoUsuarioServicio.eliminarTipousuarioPorId(tipoUsuario.getIdTipousuario());
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("Tipo Usuario Eliminada Exitosamente",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);

    }
}
