package gm.inventarios.repository;

import gm.inventarios.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso,Integer> {
    List<Curso> findByCategoriaIdCategoria(Integer idCategoria);
}

