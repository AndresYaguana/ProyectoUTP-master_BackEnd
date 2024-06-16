package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Curso;
import gm.inventarios.modelo.Usuario;
import gm.inventarios.servicio.CursoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//localhost:8080/inventario_app
@RequestMapping("ProyectoUTP-master")
@CrossOrigin(value = "http://localhost:4200")
public class CursoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(CursoControlador.class);

    @Autowired
    private CursoServicio cursoServicio;

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



}



