package gm.inventarios.servicio;

import gm.inventarios.modelo.ContenidoCurso;
import gm.inventarios.modelo.SeccionCurso;
import gm.inventarios.repository.ContenidoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenidoCursoServicio implements IContenidoCursoServicio {

    @Autowired
    private ContenidoCursoRepository contenidoCursoRepository;

    @Override
    public List<ContenidoCurso> obtenerContenidoCurso(Integer idCurso) {
        return contenidoCursoRepository.findByCursoIdCurso(idCurso);
    }

    @Override
    public List<ContenidoCurso> obtenerContenidosSeccion(Integer idSeccion) {
        return contenidoCursoRepository.findBySeccionIdSeccion(idSeccion);
    }

    @Override
    public List<ContenidoCurso> ListarContenido() {
        return this.contenidoCursoRepository.findAll();
    }

    @Override
    public ContenidoCurso buscarContenidoPorId(Integer idContenido) {
        ContenidoCurso contenidoCurso = this.contenidoCursoRepository.findById(idContenido).orElse(null);
        return contenidoCurso;
    }

    @Override
    public ContenidoCurso guardarContenido(ContenidoCurso contenidoCurso) {
        return this.contenidoCursoRepository.save(contenidoCurso);
    }

    @Override
    public void eliminarContenidoPorId(Integer idContenido) {
        this.contenidoCursoRepository.deleteById(idContenido);
    }
}
