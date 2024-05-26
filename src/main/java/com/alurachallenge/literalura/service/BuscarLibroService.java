package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.Libro;

import java.util.List;

public interface BuscarLibroService {

//    Libro buscarLibroPorTitulo(String titulo);
    List<Libro> encontrarTodosLibros();
    Integer cantidadLibrosPorIdioma(String idioma);
    List<Autor> encontrarAutoresVivosPorAnio(String year);
    List<Autor> encontrarTodosAutores();
    List<Libro> encontrarTop10LibrosMasDescargados();
    Autor encontrarAutorPorNombre(String nombre);
}
