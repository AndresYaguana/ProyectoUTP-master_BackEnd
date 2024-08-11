package gm.inventarios.repository;

import gm.inventarios.modelo.Curso;
import gm.inventarios.modelo.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{
    List<Mensaje> findByReceptorIdUsuario(Integer idReceptor);
    List<Mensaje> findByEmisor_IdUsuarioOrReceptor_IdUsuarioOrderByFechaEnvioAsc(Integer idEmisor, Integer idReceptor);
    List<Mensaje> findAllByOrderByFechaEnvioAsc();
}
