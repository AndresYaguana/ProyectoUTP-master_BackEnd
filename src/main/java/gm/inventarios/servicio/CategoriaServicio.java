package gm.inventarios.servicio;

import gm.inventarios.modelo.Categoria;
import gm.inventarios.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaServicio implements ICategoriaServicio{

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> ListarCategoria() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer idCategoria) {
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElse(null);
        return categoria;
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarCategoriaPorId(Integer idCategoria) {
        this.categoriaRepository.deleteById(idCategoria);
    }



}
