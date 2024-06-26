package gm.inventarios.servicio;


import gm.inventarios.modelo.ContenidoCurso;

import java.util.List;

public interface IContenidoCursoServicio {

    public List<ContenidoCurso> obtenerContenidoCurso(Integer idCurso);

    public List<ContenidoCurso> obtenerContenidosSeccion(Integer idSeccion);

    public List<ContenidoCurso> ListarContenido();

    public ContenidoCurso buscarContenidoPorId(Integer idContenido);

    public ContenidoCurso guardarContenido(ContenidoCurso contenidoCurso);

    public void eliminarContenidoPorId(Integer idContenido);
}
