package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Categoria;
import gm.inventarios.modelo.Curso;
import gm.inventarios.repository.CategoriaRepository;
import gm.inventarios.servicio.CategoriaServicio;
import gm.inventarios.servicio.CursoServicio;
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
public class CategoriaControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(CategoriaControlador.class);

    @Autowired
    private CategoriaServicio categoriaServicio;

    //http://localhost:8080/ProyectoUTP-master/categorias
    @GetMapping("/categorias")
    public List<Categoria> obtenerCategoria() {
        List<Categoria> categorias = this.categoriaServicio.ListarCategoria();
        logger.info("Categorias obtenidas:");
        categorias.forEach((categoria -> logger.info(categoria.toString())));
        return categorias;
    }

    @PostMapping("/categorias")
    public Categoria agregarCategoria (@RequestBody Categoria categoria){
        logger.info("Categoria a agregar: " + categoria);
        return this.categoriaServicio.guardarCategoria(categoria);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        Categoria categoria = this.categoriaServicio.buscarCategoriaPorId(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable int id,
            @RequestBody Categoria categoriaRecibido){
        Categoria categoria = this.categoriaServicio.buscarCategoriaPorId(id);
        if (categoria == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        categoria.setNombre(categoriaRecibido.getNombre());
        categoria.setRuta(categoriaRecibido.getRuta());
        categoria.setHabilitado(categoriaRecibido.getHabilitado());
        categoria.setModificadoPor(categoriaRecibido.getModificadoPor());
        categoria.setFechaModificacion(categoriaRecibido.getFechaModificacion());
        this.categoriaServicio.guardarCategoria(categoria);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCategoria(@PathVariable int id){
        Categoria categoria = categoriaServicio.buscarCategoriaPorId(id);
        if (categoria == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: "+ id);
        this.categoriaServicio.eliminarCategoriaPorId(categoria.getIdCategoria());
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("Categoria Eliminada Exitosamente",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);

    }


}
