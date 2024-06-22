package gm.inventarios.servicio;

import gm.inventarios.modelo.Categoria;
import gm.inventarios.modelo.Curso;

import java.util.List;

public interface ICategoriaServicio {

    public List<Categoria> ListarCategoria();

    public Categoria buscarCategoriaPorId(Integer idCategoria);

    public Categoria guardarCategoria(Categoria categoria);

    public void eliminarCategoriaPorId(Integer idCategoria);
}
