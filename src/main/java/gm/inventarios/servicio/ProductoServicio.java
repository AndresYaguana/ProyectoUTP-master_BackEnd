package gm.inventarios.servicio;

import gm.inventarios.modelo.Producto;
import gm.inventarios.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio implements IProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> ListarProductos() {
        return this.productoRepository.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer idProducto) {
        Producto producto = this.productoRepository.findById(idProducto).orElse(null);
        return producto;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return this.productoRepository.save(producto);
    }

    @Override
    public void eliminarProductoPorId(Integer idProducto) {
        this.productoRepository.deleteById(idProducto);
    }
}
