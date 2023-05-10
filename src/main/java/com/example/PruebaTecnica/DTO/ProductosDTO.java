package com.example.PruebaTecnica.DTO;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDTO {

  private Long id;
  private String nombre;
  private String descripcion;
  private float precio;
  private int cantidad;


}
