package com.example.PruebaTecnica.Repository;

import com.example.PruebaTecnica.Model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.DoubleStream;

/**
 * Se busca el proudcto por nombre, se usa Optional porque el valor podria ser nulo
 * y devuelve un objeto Optional nulo.
 */
@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
    Optional<Productos> findByNombre(String nombre);
}
