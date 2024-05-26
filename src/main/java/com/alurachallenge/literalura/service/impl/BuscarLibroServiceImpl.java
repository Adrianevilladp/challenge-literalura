package com.alurachallenge.literalura.service.impl;

import com.alurachallenge.literalura.enums.Idioma;
import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.Libro;
import com.alurachallenge.literalura.repository.AutorRepository;
import com.alurachallenge.literalura.repository.LibroRespository;
import com.alurachallenge.literalura.service.BuscarLibroService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BuscarLibroServiceImpl implements BuscarLibroService {
    private final LibroRespository libroRespository;
    private final AutorRepository autorRepository;

//    @Override
//    public Libro buscarLibroPorTitulo(String titulo) {
//        return libroRespository.findByTituloContainingIgnoreCase(titulo)
//                .orElse(null);
//    }

    @Override
    public List<Libro> encontrarTodosLibros() {
        return libroRespository.findAll();
    }

    @Override
    public Integer cantidadLibrosPorIdioma(String idioma) {
        Idioma idiomaParaBuscar = Idioma.fromNombre(idioma.trim().toUpperCase());
        return libroRespository.findCantidadPorIdioma(idiomaParaBuscar.name());
    }

    @Override
    public List<Autor> encontrarAutoresVivosPorAnio(String year) {
        Year yearParaBuscar = Year.of(Integer.parseInt(year.trim()));
        try {
            if (yearParaBuscar.isAfter(Year.of(2024))) {
                System.out.println("Antes del 2024 ");
                yearParaBuscar = pedirAnio();
            }
            return autorRepository.findByYear(Integer.parseInt(String.valueOf(yearParaBuscar)));
        } catch (NumberFormatException exception) {
            System.out.println("Debe ingresar un anio valido. ejempolo: 1997");
            yearParaBuscar = pedirAnio();

        } catch (Exception exception) {
            System.out.printf("ha ocurrido un error inesperado: " + exception.getMessage());
            yearParaBuscar = pedirAnio();
        }

        return autorRepository.findByYear(Integer.parseInt(String.valueOf(yearParaBuscar)));

    }

    private Year pedirAnio() {
        System.out.println("Ingrese nuevamente la fecha: ");
        String year = new Scanner(System.in).nextLine();

        return Year.of(Integer.parseInt(year.trim()));
    }

    @Override
    public List<Autor> encontrarTodosAutores() {
        return autorRepository.findAll();
    }

    @Override
    public List<Libro> encontrarTop10LibrosMasDescargados() {
        return libroRespository.findTop10MasDescargados();
    }

    @Override
    public Autor encontrarAutorPorNombre(String nombre) {
        return autorRepository.findByNombre("%" + nombre + "%")
                        .orElse(null);
    }

}
