package com.example.PruebaTecnica.Controller;


import com.example.PruebaTecnica.DTO.ProductosDTO;
import com.example.PruebaTecnica.Model.Productos;
import com.example.PruebaTecnica.Repository.ProductosRepository;
import com.example.PruebaTecnica.Service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @PostMapping
    public ResponseEntity<ProductosDTO> crearProducto(@RequestBody ProductosDTO productosDTO) {
        ProductosDTO createdProducto = productosService.crearProducto(productosDTO);
        return new ResponseEntity<>(createdProducto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> obtenerProducto(@PathVariable Long id) {
        ProductosDTO productoDTO = productosService.obtenerProducto(id);
        if (productoDTO != null) {
            return ResponseEntity.ok(productoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductosDTO productosDTO) {
        ProductosDTO updatedProducto = productosService.actualizarProducto(id, productosDTO);
        if (updatedProducto != null) {
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {
        productosService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }




}
