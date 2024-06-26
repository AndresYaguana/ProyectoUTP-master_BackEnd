package gm.inventarios.repository;
import gm.inventarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmailIgnoreCase(String email);
    List<Usuario> findByTipousuarioIdTipousuario(Integer idTipousuario);
}
