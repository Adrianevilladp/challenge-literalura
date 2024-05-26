package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.enums.Idioma;
import com.alurachallenge.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRespository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainingIgnoreCase(String titulo);

    //use derived queries
    @Query(value = "SELECT count(*) FROM libros where libros.idioma = ?1", nativeQuery = true)
    Integer findCantidadPorIdioma(String idiomaParaBuscar);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC LIMIT 10")
    List<Libro> findTop10MasDescargados();
}
