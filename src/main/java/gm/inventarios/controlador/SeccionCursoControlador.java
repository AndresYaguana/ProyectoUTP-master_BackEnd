package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Curso;
import gm.inventarios.modelo.SeccionCurso;
import gm.inventarios.servicio.CursoServicio;
import gm.inventarios.servicio.SeccionCursoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(origins = "http://localhost:4200")
public class SeccionCursoControlador {

    private static final Logger logger = LoggerFactory.getLogger(SeccionCursoControlador.class);

    private final SeccionCursoServicio seccionCursoServicio;
    private final CursoServicio cursoServicio;

    @Autowired
    public SeccionCursoControlador(SeccionCursoServicio seccionCursoServicio, CursoServicio cursoServicio) {
        this.seccionCursoServicio = seccionCursoServicio;
        this.cursoServicio = cursoServicio;
    }

    @GetMapping("/cursos/{idCurso}/seccion")
    public List<SeccionCurso> obtenerSeccionCurso(@PathVariable("idCurso") Integer idCurso) {
        return seccionCursoServicio.obtenerSeccionesPorCurso(idCurso);
    }

    @GetMapping("/seccion")
    public List<SeccionCurso> obtenerSeccion() {
        List<SeccionCurso> seccion = seccionCursoServicio.ListarSecciones();
        logger.info("Secciones obtenidas:");
        seccion.forEach(seccionCurso -> logger.info(seccionCurso.toString()));
        return seccion;
    }

    @PostMapping("/seccion")
    public SeccionCurso agregarCurso(@RequestBody SeccionCurso seccionCurso) {
        logger.info("Seccion a agregar: " + seccionCurso);
        if (seccionCurso.getCurso() != null && seccionCurso.getCurso().getIdCurso() != null) {
            Curso curso = cursoServicio.buscarCursoPorId(seccionCurso.getCurso().getIdCurso());
            seccionCurso.setCurso(curso);
        }
        return seccionCursoServicio.guardarSeccion(seccionCurso);
    }

    @GetMapping("/seccion/{id}")
    public ResponseEntity<SeccionCurso> obtenerSeccionPorId(@PathVariable int id) {
        SeccionCurso seccion = seccionCursoServicio.buscarSeccionPorId(id);
        if (seccion != null) {
            Curso curso = cursoServicio.buscarCursoPorId(seccion.getCurso().getIdCurso());
            seccion.setCurso(curso);
            return ResponseEntity.ok(seccion);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    @PutMapping("/seccion/{id}")
    public ResponseEntity<SeccionCurso> actualizarSeccion(
            @PathVariable int id,
            @RequestBody SeccionCurso seccionRecibida) {
        SeccionCurso seccion = seccionCursoServicio.buscarSeccionPorId(id);
        if (seccion == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }

        seccion.setNombre(seccionRecibida.getNombre());
        seccion.setHabilitado(seccionRecibida.getHabilitado());
        seccion.setModificadoPor(seccionRecibida.getModificadoPor());
        seccion.setFechaModificacion(seccionRecibida.getFechaModificacion());

        if (seccionRecibida.getCurso() != null && seccionRecibida.getCurso().getIdCurso() != null) {
            Curso curso = cursoServicio.buscarCursoPorId(seccionRecibida.getCurso().getIdCurso());
            seccion.setCurso(curso);
        }

        seccionCursoServicio.guardarSeccion(seccion);
        return ResponseEntity.ok(seccion);
    }


    @DeleteMapping("/seccion/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarSeccion(@PathVariable int id) {
        SeccionCurso seccion = seccionCursoServicio.buscarSeccionPorId(id);
        if (seccion == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
        seccionCursoServicio.eliminarSeccionPorId(seccion.getIdSeccion());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Seccion Eliminada Exitosamente", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
