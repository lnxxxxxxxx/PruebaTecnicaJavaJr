package com.example.PruebaTecnica.Service;

import com.example.PruebaTecnica.DTO.ProductosDTO;
import com.example.PruebaTecnica.Mapper.ProductosMapper;
import com.example.PruebaTecnica.Model.Productos;
import com.example.PruebaTecnica.Repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    public List<ProductosDTO> getProductos() {
        List<Productos> productosList = productosRepository.findAll();
        List<ProductosDTO> productosDtoList = new ArrayList<>();

        for (Productos producto : productosList) {
            ProductosDTO productoDto = ProductosMapper.toDto(producto);
            productosDtoList.add(productoDto);
        }

        return productosDtoList;
    }


    public ProductosDTO getProductoById(Long id) {
        Optional<Productos> optionalProductos = productosRepository.findById(id);
        if (optionalProductos.isPresent()) {
            Productos productos = optionalProductos.get();
            return ProductosMapper.toDto(productos);
        }
        return null;
    }

    public ProductosDTO createProducto(ProductosDTO productosDTO) {
        Productos productos = ProductosMapper.toModel(productosDTO);
        productos = productosRepository.save(productos);
        return ProductosMapper.toDto(productos);
    }

    public ProductosDTO updateProducto(Long id, ProductosDTO productosDTO) {
        Optional<Productos> optionalProductos = productosRepository.findById(id);
        if (optionalProductos.isPresent()) {
            Productos productos = optionalProductos.get();
            productos.setId(id);
            productos.setNombre(productosDTO.getNombre());
            productos.setCantidad(productosDTO.getCantidad());
            productos.setPrecio(productosDTO.getPrecio());
            productos.setDescripcion(productosDTO.getDescripcion());
            productos = productosRepository.save(productos);
            return ProductosMapper.toDto(productos);
        }
        return null;
    }

    public boolean deleteProducto(Long id) {
        Optional<Productos> optionalProductos = productosRepository.findById(id);
        if (optionalProductos.isPresent()) {
            productosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
