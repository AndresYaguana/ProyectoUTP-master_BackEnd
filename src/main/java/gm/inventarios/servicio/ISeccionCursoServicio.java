package gm.inventarios.servicio;

import gm.inventarios.modelo.SeccionCurso;

import java.util.List;

public interface ISeccionCursoServicio {

    public List<SeccionCurso> obtenerSeccionesPorCurso(Integer idCurso);

    public List<SeccionCurso> ListarSecciones();

    public SeccionCurso buscarSeccionPorId(Integer idSeccion);

    public SeccionCurso guardarSeccion(SeccionCurso seccionCurso);

    public void eliminarSeccionPorId(Integer idSeccion);

}
