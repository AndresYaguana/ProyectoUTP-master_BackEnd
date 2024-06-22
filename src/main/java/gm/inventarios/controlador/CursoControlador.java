package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Categoria;
import gm.inventarios.modelo.Curso;
import gm.inventarios.modelo.Usuario;
import gm.inventarios.servicio.CategoriaServicio;
import gm.inventarios.servicio.CursoServicio;
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
public class CursoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(CursoControlador.class);

    private CursoServicio cursoServicio;
    private CategoriaServicio categoriaServicio;

    @Autowired
    public CursoControlador(CursoServicio cursoServicio, CategoriaServicio categoriaServicio) {
        this.cursoServicio = cursoServicio;
        this.categoriaServicio = categoriaServicio;
    }

    //http://localhost:8080/ProyectoUTP-master/cursos
    @GetMapping("/cursos")
    public List<Curso> obtenerCursos() {
        List<Curso> cursos = this.cursoServicio.ListarCursos();
        logger.info("Cursos obtenidos:");
        cursos.forEach((usuario -> logger.info(usuario.toString())));
        return cursos;
    }
    @PostMapping("/cursos")
    public Curso agregarCurso (@RequestBody Curso curso){
        logger.info("Curso a agregar: " + curso);
        if (curso.getCategoria() != null && curso.getCategoria().getIdCategoria() != null) {
            Categoria categoria = categoriaServicio.buscarCategoriaPorId(curso.getCategoria().getIdCategoria());
            curso.setCategoria(categoria);
        }
        return this.cursoServicio.guardarCurso(curso);
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable int id) {
        Curso curso = this.cursoServicio.buscarCursoPorId(id);
        if (curso != null) {
            return ResponseEntity.ok(curso);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> actualizarCurso(
            @PathVariable int id,
            @RequestBody Curso cursoRecibido){
        Curso curso = this.cursoServicio.buscarCursoPorId(id);
        if (curso == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        curso.setNombre(cursoRecibido.getNombre());
        curso.setRuta(cursoRecibido.getRuta());
        curso.setUrlImage(cursoRecibido.getUrlImage());
        curso.setDescripcion(cursoRecibido.getDescripcion());
        curso.setHabilitado(cursoRecibido.getHabilitado());
        curso.setModificadoPor(cursoRecibido.getModificadoPor());
        curso.setFechaModificacion(cursoRecibido.getFechaModificacion());
        this.cursoServicio.guardarCurso(curso);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCurso(@PathVariable int id){
        Curso curso = cursoServicio.buscarCursoPorId(id);
        if (curso == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        this.cursoServicio.eliminarCursoPorId(curso.getIdCurso());
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("Curso Eliminado Exitosamente",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);

    }

}



