package gm.inventarios.repository;

import gm.inventarios.modelo.ContenidoCurso;
import gm.inventarios.modelo.SeccionCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContenidoCursoRepository extends JpaRepository<ContenidoCurso, Integer> {
    List<ContenidoCurso> findByCursoIdCurso(Integer idCurso);
    List<ContenidoCurso> findBySeccionIdSeccion(Integer idSeccion);
}
