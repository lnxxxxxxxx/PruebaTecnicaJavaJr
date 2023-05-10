package com.example.PruebaTecnica.Mapper;

import com.example.PruebaTecnica.DTO.ProductosDTO;
import com.example.PruebaTecnica.Model.Productos;

public class ProductosMapper {
  public static ProductosDTO toDto(Productos payment) {
    ProductosDTO productosDTO = new ProductosDTO();
    productosDTO.setId(payment.getId());
    productosDTO.setNombre(payment.getNombre());
    productosDTO.setCantidad(payment.getCantidad());
    productosDTO.setPrecio(payment.getPrecio());
    productosDTO.setDescripcion(payment.getDescripcion());
    return productosDTO;
  }

  public static Productos toModel(ProductosDTO paymentDto) {
    Productos productos = new Productos();
    productos.setId(paymentDto.getId());
    productos.setNombre(paymentDto.getNombre());
    productos.setCantidad(paymentDto.getCantidad());
    productos.setPrecio(paymentDto.getPrecio());
    productos.setDescripcion(paymentDto.getDescripcion());
    return productos;
  }


}
