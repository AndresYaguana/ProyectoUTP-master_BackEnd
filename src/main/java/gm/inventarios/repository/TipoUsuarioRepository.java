package gm.inventarios.repository;

import gm.inventarios.modelo.Curso;
import gm.inventarios.modelo.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario,Integer>{

}
