package gm.inventarios.servicio;

import gm.inventarios.modelo.Curso;

import java.util.List;

public interface ICursoServicio {

    public List<Curso> ListarCursos();

    public Curso buscarCursoPorId(Integer idCurso);

    public Curso guardarCurso(Curso curso);

    public void eliminarCursoPorId(Integer idCurso);

    public List<Curso> obtenerCursosPorCategoria(Integer idCategoria);
}
