package gm.inventarios.servicio;

import gm.inventarios.modelo.SeccionCurso;
import gm.inventarios.repository.SeccionCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SeccionCursoServicio implements ISeccionCursoServicio{

    @Autowired
    private SeccionCursoRepository seccionCursoRepository;

    @Override
    public List<SeccionCurso> obtenerSeccionesPorCurso(Integer idCurso) {
        return seccionCursoRepository.findByCursoIdCurso(idCurso);
    }

    @Override
    public List<SeccionCurso> ListarSecciones() {
        return this.seccionCursoRepository.findAll();
    }

    @Override
    public SeccionCurso buscarSeccionPorId(Integer idSeccion) {
        SeccionCurso seccionCurso = this.seccionCursoRepository.findById(idSeccion).orElse(null);
        return seccionCurso;
    }

    @Override
    public SeccionCurso guardarSeccion(SeccionCurso seccionCurso) {
        return this.seccionCursoRepository.save(seccionCurso);
    }

    @Override
    public void eliminarSeccionPorId(Integer idSeccion) {
        this.seccionCursoRepository.deleteById(idSeccion);
    }

}
