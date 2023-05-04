package com.example.PruebaTecnica.Controller;


import com.example.PruebaTecnica.Model.Productos;
import com.example.PruebaTecnica.Repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductosController {

    @Autowired
    ProductosRepository productosRepository;

    /**
     * Busca todos los productos
     * localhost/api/productos
     */
    @GetMapping("/productos")
    public List<Productos> findAll() {
        return productosRepository.findAll();
    }

    /**
     * Busca todos los productos ordenados por precio
     * localhost/api/productos-ordenados
     */
    @GetMapping("/productos-ordenados")
    public List<Productos> findAllOrderByPrecio() {
        return productosRepository.findAll(Sort.by(Sort.Direction.ASC, "precio"));
    }

    /**
     * Crear un nuevo producto en base de datos
     * Método POST
     * @param productos
     * @return
     */

    @PostMapping("/crear")
    public ResponseEntity<Productos> create(@RequestBody Productos productos){
        if(productos.getId() != null) {
            System.out.println("El producto que intenta ingresar ya fue ingresado");
            return ResponseEntity.badRequest().build();
        }
        Productos res = productosRepository.save(productos);
        return ResponseEntity.ok(res);
    }

    /**
     * actualizar un producto existente en base de datos
     * verifica si es ingresado el id y si existe en la base de datos
     */

    @PutMapping("/actualizar")
    public ResponseEntity<Productos> update(@RequestBody Productos productos){
        if(productos.getId() == null ){
            return ResponseEntity.badRequest().build();
        }
        if(!productosRepository.existsById(productos.getId())){
            return ResponseEntity.notFound().build();
        }


        Productos res = productosRepository.save(productos);
        return ResponseEntity.ok(res);
    }

    /**
     * Busca el producto por ID o por nombre
     * se utiliza los metodos .map() y .orElse() de la clase Optional
     * .map() para transformar el objeto en un nuevo objeto Optional
     * .orElse() si el objeto esta vacio le da valor predeterminado
     * En este caso .map() devuelve un status 200, y orElse() status 404.
     * localhost/api/buscarproducto?nombre=NombreProducto
     * localhost/api/buscarproducto?id=idProducto
     */

    @GetMapping("/buscarproducto")
    public ResponseEntity<Productos> findByIdOrNombre(@RequestParam(required = false) Long id, @RequestParam(required = false) String nombre) {
        if (id != null) {
            return productosRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if (nombre != null) {
            return productosRepository.findByNombre(nombre)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina el producto ingresado por nombre o por ID
     * Se utiliza el metodo isPresent() de la clase Optional para
     * ver si es true los datos del proudcto ingresados.
     * @param id
     * @param nombre
     * @return
     *
     * localhost/api/eliminarproducto?nombre=NombreProducto
     * localhost/api/eliminarproducto?id=idProducto
     */

    @DeleteMapping("/eliminarproducto")
    public ResponseEntity<?> deleteByIdOrNombre(@RequestParam(required = false) Long id, @RequestParam(required = false) String nombre) {
        if (id != null) {
            if(productosRepository.existsById(id)){
                productosRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        } else if (nombre != null) {
            Optional<Productos> producto = productosRepository.findByNombre(nombre);
            if(producto.isPresent()){
                productosRepository.delete(producto.get());
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }




}
