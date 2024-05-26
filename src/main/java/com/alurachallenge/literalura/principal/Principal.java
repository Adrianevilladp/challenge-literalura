package com.alurachallenge.literalura.principal;

import com.alurachallenge.literalura.model.*;
import com.alurachallenge.literalura.procesarJson.DatosAutor;
import com.alurachallenge.literalura.procesarJson.DatosLibro;
import com.alurachallenge.literalura.procesarJson.JsonResultado;
import com.alurachallenge.literalura.procesarJson.Result;
import com.alurachallenge.literalura.repository.AutorRepository;
import com.alurachallenge.literalura.service.BuscarLibroService;
import com.alurachallenge.literalura.service.impl.ConsumoAPIImpl;
import com.alurachallenge.literalura.service.impl.ConvertirDatosImpl;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPIImpl consumoAPI = new ConsumoAPIImpl();
    private ConvertirDatosImpl convertirDatos = new ConvertirDatosImpl();
    private AutorRepository autorRepository;
    private BuscarLibroService buscarLibroService;

    public Principal(AutorRepository autorRepository, BuscarLibroService buscarLibroService) {
        this.autorRepository = autorRepository;
        this.buscarLibroService = buscarLibroService;
    }

    public void muestraMenu() {
        var opcion = -1;
        var menu = """
                1. - Buscar libro
                2. - Mostrar todos los libros
                3. - Cantidad de libros por idioma
                4. - Mostrar autores vivos en un anio especifico
                5. - Top 10 libros mas descargados
                6. - Buscar autor
                
                0. - Salir
                """;

        while (opcion != 0) {
            System.out.println(menu);
            opcion = pedirYValidarNumero();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarTodosLibros();
                    break;
                case 3:
                    cantidadDeLibrosPorIdioma();
                    break;
                case 4:
                    mostrarAutoresEnUnAnio();
                    break;
                case 5:
                    top10LibrosMasDescargados();
                    break;
                case 6:
                    encontrarAutor();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
    }

    private Integer pedirYValidarNumero() {
        var opcion = -1;
        try {
            opcion = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException | NumberFormatException | NullPointerException exception) {
            System.out.println("Ha ocurrido un error \n ingrese nuevamente un numero valido (ej: 4): ");
            opcion = teclado.nextInt();
            teclado.nextLine();
        }

        return opcion;
    }

    private DatosLibro obtenerYGuardarLibro() {
        System.out.println("Escribe el nombre del libro a buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(obtenerUrlValida(nombreLibro));
        JsonResultado jsonResultado = convertirDatos.obtenerDatos(json, JsonResultado.class);

        if (jsonResultado.cantidad() < 1) {
            System.out.println("Titulo no encontrado");
            return obtenerYGuardarLibro();
        }

        Result result = obtenerResultado(jsonResultado);
        DatosAutor autorEncontrado = obtenerAutor(result);
        DatosLibro libroEncontrado = obtenerLibroDeResult(result);
        System.out.println(libroEncontrado + " - " + autorEncontrado);
        guardarLibro(libroEncontrado, autorEncontrado);

        return libroEncontrado;
    }

    private String obtenerUrlValida(String nombreLibro) {
        return URL_BASE + nombreLibro.replace(" ", "%20");
    }

    private Result obtenerResultado(JsonResultado jsonResultado) {
        return jsonResultado.result().get(0);
    }

    private DatosAutor obtenerAutor(Result result) {
        return result.autors().get(0);
    }

    private DatosLibro obtenerLibroDeResult(Result result) {
        return new DatosLibro(
                result.titulo(),
                result.cantidadDescargas(),
                result.idioma().get(0)
        );
    }

    private void guardarLibro(DatosLibro libroEncontrado, DatosAutor autorEncontrado) {
        if (!autorRepository.existsByNombre(autorEncontrado.nombre())) {
            Libro libroAGuardar = new Libro(libroEncontrado);
            Autor autorAGuardar = new Autor(autorEncontrado);
            autorAGuardar.aniadirLibro(libroAGuardar);
            autorRepository.save(autorAGuardar);
        }
    }

    private void buscarLibro() {
        obtenerYGuardarLibro();
    }

    private void mostrarTodosLibros() {
        System.out.println(buscarLibroService.encontrarTodosLibros());
    }

    private void cantidadDeLibrosPorIdioma() {
        System.out.println("Escribe el idioma (ej: Ruso) : ");
        var idioma = teclado.nextLine();
        System.out.println("Cantidad de libros en este idioma " + buscarLibroService.cantidadLibrosPorIdioma(idioma));
    }

    private void mostrarAutoresEnUnAnio() {
        System.out.println("Escribe el anio : ");
        var anio = teclado.nextLine();

        System.out.println("Autores vivos en el anio: "+anio+ "\n");
        System.out.println(buscarLibroService.encontrarAutoresVivosPorAnio(anio));
    }

    private void top10LibrosMasDescargados () {
        System.out.println(buscarLibroService.encontrarTop10LibrosMasDescargados());
    }

    private void encontrarAutor() {
        System.out.println("Ingrese el nombre del autor: ");
        var nombreAutor = teclado.nextLine();
        var autorEncontrado = buscarLibroService.encontrarAutorPorNombre(nombreAutor);
        if (Objects.isNull(autorEncontrado)) {
            System.out.println("Autor no encontrado");
        }
        System.out.println(autorEncontrado);
    }
}
