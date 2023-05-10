package com.example.PruebaTecnica.Service.impl;

import com.example.PruebaTecnica.DTO.ProductosDTO;
import com.example.PruebaTecnica.Mapper.ProductosMapper;
import com.example.PruebaTecnica.Model.Productos;
import com.example.PruebaTecnica.Repository.ProductosRepository;
import com.example.PruebaTecnica.Service.iProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosService implements iProductosService {

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

    public List<Productos> findAllOrderByPrecio() {
        return productosRepository.findAll(Sort.by(Sort.Direction.ASC, "precio"));
    }

    public Optional<Productos> findByIdOrNombre(Long id, String nombre) {
        if (id != null) {
            return productosRepository.findById(id);
        } else if (nombre != null) {
            return productosRepository.findByNombre(nombre);
        } else {
            return Optional.empty();
        }
    }

    /**
     * no esta en uso, es para traer por id al proudcto
     * @param id
     * @return
     */
    public ProductosDTO getProductoById(Long id) {
        Optional<Productos> optionalProductos = productosRepository.findById(id);
        if (optionalProductos.isPresent()) {
            Productos productos = optionalProductos.get();
            return ProductosMapper.toDto(productos);
        }
        return null;
    }

    public ProductosDTO createProducto(ProductosDTO productosDTO) {

        Optional<Productos> productoExistente = productosRepository.findByIdOrNombre(productosDTO.getId(), productosDTO.getNombre());
        if (productoExistente.isPresent()) {
            throw new RuntimeException("Ya existe un producto con el mismo id o nombre");
        }


        Productos productos = ProductosMapper.toModel(productosDTO);
        productos = productosRepository.save(productos);
        return ProductosMapper.toDto(productos);
    }

    public ProductosDTO updateProductoByIdOrNombre(String idOuNombre, ProductosDTO productosDTO) {
        Optional<Productos> optionalProductos;
        try {
            Long id = Long.parseLong(idOuNombre);
            optionalProductos = productosRepository.findById(id);
        } catch (NumberFormatException e) {
            optionalProductos = productosRepository.findByNombre(idOuNombre);
        }

        if (optionalProductos.isPresent()) {
            Productos productos = optionalProductos.get();
            productos.setNombre(productosDTO.getNombre());
            productos.setCantidad(productosDTO.getCantidad());
            productos.setPrecio(productosDTO.getPrecio());
            productos.setDescripcion(productosDTO.getDescripcion());
            productos = productosRepository.save(productos);
            return ProductosMapper.toDto(productos);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
    }


    public boolean deleteProducto(String idOuNombre) {
        Optional<Productos> optionalProductos;
        try {
            Long id = Long.parseLong(idOuNombre);
            optionalProductos = productosRepository.findById(id);
        } catch (NumberFormatException e) {
            optionalProductos = productosRepository.findByNombre(idOuNombre);
        }
        if (optionalProductos.isPresent()) {
            productosRepository.deleteById(optionalProductos.get().getId());
            return true;
        }
        return false;
    }
}
