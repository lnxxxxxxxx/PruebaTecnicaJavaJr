package com.example.PruebaTecnica.Service;


import com.example.PruebaTecnica.DTO.ProductosDTO;
import com.example.PruebaTecnica.Model.Productos;

import java.util.List;
import java.util.Optional;

public interface iProductosService {

  List<ProductosDTO> getProductos();

  List<Productos> findAllOrderByPrecio();

  Optional<Productos> findByIdOrNombre(Long id, String nombre);

  ProductosDTO getProductoById(Long id);

  ProductosDTO createProducto(ProductosDTO productosDTO);

  ProductosDTO updateProductoByIdOrNombre(String idOuNombre, ProductosDTO productosDTO);

  boolean deleteProducto(String idOuNombre);
}


