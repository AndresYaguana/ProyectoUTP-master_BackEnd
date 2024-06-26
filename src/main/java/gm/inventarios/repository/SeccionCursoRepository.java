package gm.inventarios.repository;

import gm.inventarios.modelo.SeccionCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeccionCursoRepository extends JpaRepository<SeccionCurso, Integer>{
    List<SeccionCurso> findByCursoIdCurso(Integer idCurso);
}
