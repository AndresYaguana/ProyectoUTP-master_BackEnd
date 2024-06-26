package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.ContenidoCurso;
import gm.inventarios.modelo.SeccionCurso;
import gm.inventarios.repository.CursoRepository;
import gm.inventarios.servicio.ContenidoCursoServicio;
import gm.inventarios.servicio.SeccionCursoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class ContenidoCursoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ContenidoCursoControlador.class);

    private final ContenidoCursoServicio contenidoCursoServicio;

    public ContenidoCursoControlador(ContenidoCursoServicio contenidoCursoServicio) {
        this.contenidoCursoServicio = contenidoCursoServicio;
    }

    @GetMapping("/cursos/contenido/{id}")
    public List<ContenidoCurso> obtenerContenidoCurso(@PathVariable Integer id) {
        return contenidoCursoServicio.obtenerContenidoCurso(id);
    }

    @GetMapping("/cursos/contenido/seccion/{id}")
    public List<ContenidoCurso> obtenerContenidoSeccion(@PathVariable Integer id) {
        return contenidoCursoServicio.obtenerContenidosSeccion(id);
    }

    @GetMapping("/contenido")
    public List<ContenidoCurso> obtenerContenido() {
        List<ContenidoCurso> contenido = this.contenidoCursoServicio.ListarContenido();
        logger.info("Contenidos obtenidos:");
        contenido.forEach(contenidoCurso -> logger.info(contenidoCurso.toString()));
        return contenido;
    }

    @GetMapping("/contenido/{id}")
    public ResponseEntity<ContenidoCurso> obtenerContenidoPorId(@PathVariable int id) {
        ContenidoCurso contenido = this.contenidoCursoServicio.buscarContenidoPorId(id);
        if (contenido != null) {
            return ResponseEntity.ok(contenido);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    @PutMapping("/contenido/{id}")
    public ResponseEntity<ContenidoCurso> actualizarContenido(
            @PathVariable int id,
            @RequestBody ContenidoCurso contenidoRecibido) {
        ContenidoCurso contenido = this.contenidoCursoServicio.buscarContenidoPorId(id);
        if (contenido == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        contenido.setTitulo(contenidoRecibido.getTitulo());
        contenido.setDescripcion(contenidoRecibido.getDescripcion());
        contenido.setUrlVideo(contenidoRecibido.getUrlVideo());
        contenido.setContenido(contenidoRecibido.getContenido());
        contenido.setHabilitado(contenidoRecibido.getHabilitado());
        contenido.setModificadoPor(contenidoRecibido.getModificadoPor());
        contenido.setFechaModificacion(contenidoRecibido.getFechaModificacion());
        this.contenidoCursoServicio.guardarContenido(contenido);
        return ResponseEntity.ok(contenido);
    }

    @DeleteMapping("/contenido/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarSeccion(@PathVariable int id) {
        ContenidoCurso contenido = contenidoCursoServicio.buscarContenidoPorId(id);
        if (contenido == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        this.contenidoCursoServicio.eliminarContenidoPorId(contenido.getIdContenido());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Contenido Eliminado Exitosamente", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
