package gm.inventarios.servicio;

import gm.inventarios.modelo.Curso;
import gm.inventarios.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CursoServicio implements ICursoServicio{

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<Curso> ListarCursos() {
        return this.cursoRepository.findAll();
    }

    @Override
    public Curso buscarCursoPorId(Integer idCurso) {
        Curso curso = this.cursoRepository.findById(idCurso).orElse(null);
        return curso;
    }

    @Override
    public Curso guardarCurso(Curso curso) {
        return this.cursoRepository.save(curso);
    }

    @Override
    public void eliminarCursoPorId(Integer idCurso) {
        this.cursoRepository.deleteById(idCurso);
    }

    @Override
    public List<Curso> obtenerCursosPorCategoria(Integer idCategoria) {
        return cursoRepository.findByCategoriaIdCategoria(idCategoria);
    }

}
