package com.example.PruebaTecnica.Controller;


import com.example.PruebaTecnica.DTO.ProductosDTO;
import com.example.PruebaTecnica.Mapper.ProductosMapper;
import com.example.PruebaTecnica.Model.Productos;
import com.example.PruebaTecnica.Service.iProductosService;
import com.example.PruebaTecnica.Service.impl.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductosController {

  @Autowired
  private iProductosService productosService;

  @PostMapping
  public ResponseEntity<ProductosDTO> createProducto(@RequestBody ProductosDTO productosDTO) {
    try {
      ProductosDTO productoCreado = productosService.createProducto(productosDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    } catch (RuntimeException ex) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<ProductosDTO>> getProductos() {
    List<ProductosDTO> productosDtoList = productosService.getProductos();
    return new ResponseEntity<>(productosDtoList, HttpStatus.OK);
  }

  @GetMapping("/ordenados")
  public ResponseEntity<List<Productos>> getAllProductosOrderByPrecio() {
    List<Productos> productos = productosService.findAllOrderByPrecio();
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }


  @GetMapping("/{idOuNombre}")
  public ResponseEntity<ProductosDTO> buscarProductoPorIdOuNombre(@PathVariable String idOuNombre) {
    Optional<Productos> producto = null;

    try {
      Long id = Long.parseLong(idOuNombre);
      producto = productosService.findByIdOrNombre(id, null);
    } catch (NumberFormatException e) {
      producto = productosService.findByIdOrNombre(null, idOuNombre);
    }

    if (producto.isPresent()) {
      ProductosDTO productoDTO = ProductosMapper.toDto(producto.get());
      return ResponseEntity.ok(productoDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{idOuNombre}")
  public ProductosDTO updateProductoByIdOrNombre(@PathVariable String idOuNombre, @RequestBody ProductosDTO productosDTO) {
    return productosService.updateProductoByIdOrNombre(idOuNombre, productosDTO);
  }

  @DeleteMapping("/{idOuNombre}")
  public ResponseEntity<String> deleteProducto(@PathVariable String idOuNombre) {
    boolean eliminado = productosService.deleteProducto(idOuNombre);
    if (eliminado) {
      return ResponseEntity.ok().body("Producto eliminado exitosamente");
    } else {
      return ResponseEntity.notFound().build();
    }
  }


}
